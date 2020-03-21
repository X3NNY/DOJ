package pers.dreamer.controller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FilenameUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.dreamer.bean.*;
import pers.dreamer.dto.UserEditDto;
import pers.dreamer.dto.UserLoginDto;
import pers.dreamer.dto.UserRegisterDto;
import pers.dreamer.service.*;
import pers.dreamer.util.UserUtil;
import pers.dreamer.util.captcha.CodeUtil;
import pers.dreamer.util.captcha.VerifyImageUtil;
import pers.dreamer.util.idworker.Sid;
import pers.dreamer.util.tools.EncodeUtil;
import pers.dreamer.util.tools.FileUtil;
import pers.dreamer.util.tools.MyUtil;
import pers.dreamer.util.tools.ValidCheck;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用户模块
 */
@CrossOrigin
@RestController
public class UserController {

    static String hostname = "116.63.155.152";

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @Autowired
    DateFormat dateFormat;

    @Autowired
    SubmitlistService submitlistService;

    @Autowired
    InvitationService invitationService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Autowired
    ContestService contestService;

    @Autowired
    MessageService messageService;

    @Autowired
    AchievementService achievementService;

    @Autowired
    Sid sid;

    @Autowired
    Gson gson;

    @Autowired
    DefaultStringRedisConnection stringRedisConnection;

    @PostMapping("/api/user/search")
    public Map<String,Integer> searchUserByName(String name) throws UnsupportedEncodingException {
        Map<String,Integer> ret = new HashMap<>();
        name = new String(name.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
        User user = userService.findUserByName(name);
        if (user == null) {
            ret.put("state",0);
        } else {
            ret.put("state",1);
            ret.put("uid",user.getUid());
        }
        return ret;
    }

    @PostMapping("/api/user/login")
    public Map<String,Integer> login(@RequestBody UserLoginDto userDto, HttpSession session) throws Exception {
        Map<String,Integer> ret = new HashMap<String, Integer>();//userDto.getCheckcode().toLowerCase().equals(redisTemplate.opsForHash().get(session.getId(),"checkcode")
        User user;
        if (userDto.getPassword() == null || userDto.getUsername() == null
                || (user = userService.findUserByPwdAndName(userDto.getUsername(),userDto.getPassword())) == null) {
            ret.put("state",1);
        } else if (!"1".equals(stringRedisConnection.hGet(session.getId(), "checkcode_verify"))) {//redisTemplate.opsForHash().get(session.getId(), "checkcode_verify")
            ret.put("state",2);
        } else if (!ValidCheck.isValidString(userDto.getUsername())) {
            ret.put("state",3);
        } else if (user.getStatus()!=0) {
            ret.put("state",4);
        } else {
            stringRedisConnection.hDel(session.getId(),"checkcode_verify");
            //redisTemplate.opsForHash().delete(session.getId(),"checkcode_verify");
            stringRedisConnection.hDel(session.getId(),"checkcode");
            //redisTemplate.opsForHash().delete(session.getId(),"checkcode");
            Date now = new Date();
            if (user.getLastvisit().getTime() - user.getLastvisit().getTime()%(86400000) != now.getTime() - now.getTime()%86400000) {
                user.setLiveness(user.getLiveness() + 5);
                user.setLevel(user.getLevel() + 5);
                user.setCoins(user.getCoins() + 1);
                pers.dreamer.bean.Message message = new pers.dreamer.bean.Message();
                message.setStatus(0);
                message.setDate(new Date());
                message.setTitle("每日登陆:+1 DC 任务达成!");
                message.setContent("当前您的DC数量为:" + user.getCoins() + " DC");
                message.setSenduid(-1);
                message.setTargetuid(user.getUid());
                messageService.insertMessage(message);
            }
            user.setLastvisit(now);
            userService.updateUserInfoNew(user);
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(3600*7);//7 hour 失效
            stringRedisConnection.hSet(session.getId(),session.getId(),String.valueOf(user.getUid()));
            //redisTemplate.opsForHash().put(session.getId(),session.getId(),user.getUid());
            stringRedisConnection.expire(session.getId(), 7*60*60);
            //redisTemplate.opsForHash().getOperations().expire(session.getId(), 7, TimeUnit.HOURS);//7 hour 失效
            ret.put("state",0);
        }
        return ret;
    }

    @GetMapping("/api/user/logout")
    public Map<String,Integer> logout(HttpSession session){
        Map<String,Integer> ret = new HashMap<String, Integer>();
        User user = (User) session.getAttribute("user");
        if(user == null) ret.put("state",1);
        else{
            //redisTemplate.delete(session.getId());
            stringRedisConnection.del(session.getId());
            session.invalidate();
            ret.put("state",0);
        }
        return  ret;
    }

    @PostMapping("/api/user/register")
    public Map<String,Integer> register(@RequestBody UserRegisterDto userDto) throws Exception {
        Map<String,Integer> ret = new HashMap<String, Integer>();
        if(userDto.getStep() != null && userDto.getStep() == 0){
            if(userDto.getInvitecode() == null || !invitationService.findInvitCode(userDto.getInvitecode())) ret.put("state",1);
            else {
                ret.put("state",0);
                invitationService.updateInvitCode(userDto.getInvitecode());
                String safeKey = EncodeUtil.sha1Encode(sid.nextShort()+sid.nextShort());
                stringRedisConnection.setEx(safeKey,7*24*60*60,userDto.getInvitecode());
                //redisTemplate.opsForValue().set(safeKey,userDto.getInvitecode(),7,TimeUnit.DAYS);
                //redisTemplate.opsForValue().set(userDto.getInvitecode(), userDto.getEmail(),7,TimeUnit.DAYS);
                stringRedisConnection.setEx(userDto.getInvitecode(),7*24*60*60, userDto.getEmail());
                String content = (userDto.getEmail()+"|激活邮件|欢迎您的注册，请访问网址 http://"+hostname+"/register.jsp?email="+userDto.getEmail()+"%26invite="+userDto.getInvitecode()+"%26key="+safeKey+" "+"完善资料并激活账户。\n本链接7日内有效");
                sendMsgForEmail(content);
            }
        }else{
            String inviteCode = stringRedisConnection.get(userDto.getKey());//redisTemplate.opsForValue().get(userDto.getKey());
            if (inviteCode == null) {
                ret.put("state",1);
                return ret;
            }
            String rEmail = stringRedisConnection.get(inviteCode);//redisTemplate.opsForValue().get(inviteCode);
            if (rEmail == null || !rEmail.equals(userDto.getEmail())) ret.put("state",1);
            else if (userDto.getUsername().length() == 0 || !ValidCheck.isValidString(userDto.getUsername())) ret.put("state",2);
            else {
                stringRedisConnection.del(userDto.getInvitecode());
                //redisTemplate.delete(userDto.getInvitecode());
                boolean success = userService.insertUser(userDto.getEmail(),userDto.getUsername(), EncodeUtil.sha1Encode(userDto.getPassword()),userDto.getSchool(),userDto.getInvitecode());
                if (success) {
                    ret.put("state",0);
                } else {
                    ret.put("state",3);
                }
            }
        }
        return ret;
    }

    @GetMapping("/api/user/edit")
    public Map<String,Object> editUserInfo(HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) return ret;
        ret.put("school",user.getSchool());
        ret.put("note",user.getNote());
        return ret;
    }

    @PostMapping("/api/user/edit")
    public Map<String,Integer> editUser(@RequestBody UserEditDto userEditDto,HttpSession session) throws Exception {
        Map<String,Integer> ret = new HashMap<String, Integer>();
        User user = (User) session.getAttribute("user");
        if(user == null || !user.getPassword().equals(EncodeUtil.sha1Encode(userEditDto.getPassword()))) ret.put("state",1);
        else{
            if (userEditDto.getNewpassword() != null && !"".equals(userEditDto.getNewpassword())) {
                user.setPassword(EncodeUtil.sha1Encode(userEditDto.getNewpassword()));
            }
            if(userEditDto.getNote() != null && !"".equals(userEditDto.getNote())) user.setNote(userEditDto.getNote());
            if(userEditDto.getSchool() != null && !"".equals(userEditDto.getSchool())) user.setSchool(userEditDto.getSchool());
            userService.updateUserInfo(user);
            ret.put("state",0);
        }
        return ret;
    }

    @GetMapping("/api/user/info")
    public  Map<String,Object> userInfo(Integer uid,String username,HttpSession session) throws UnsupportedEncodingException {
        User user = null;
        Map<String,Object> ret = new HashMap<String, Object>();
        username = username == null ? null :new String(username.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
        if(uid != null && uid >= 0) user = userService.findUserByUid(uid);
        else if (username != null && username.length() != 0) user = userService.findUserByName(username);
        else user = (User) session.getAttribute("user");
        if(user == null) return ret;
        ret.put("uid",user.getUid());
        ret.put("username",user.getUsername());
        ret.put("note",user.getNote());
        ret.put("image",user.getImage());
        ret.put("coins",user.getCoins());
        ret.put("school",user.getSchool());
        ret.put("email",user.getEmail());
        ret.put("level",user.getLevel());
        ret.put("maxscore",user.getMaxscore());
        ret.put("rating",user.getRating());
        ret.put("registerdate",dateFormat.format(user.getRegisterdate()));
        ret.put("lastvisit",user.getLastvisit());
        ret.put("rank",userService.findRankByRating(user.getRating()));
        ret.put("ac_cnt",userService.findAc_cntByUid(user.getUid()));
        ret.put("all_cnt",userService.findAll_cntByUid(user.getUid()));
        ret.put("ac_num",userService.findAc_numByUid(user.getUid()));
        ret.put("contest",userService.findContestByUid(user.getUid()));
        return ret;
    }

    @GetMapping("/api/user/curuser")
    public Map<String,Object> getCurrentUsername(HttpSession session) {
        Map<String,Object> res = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getUsername() == null) {
            res.put("state",1);
            res.put("username","");
        } else {
            res.put("state",0);
            res.put("uid",user.getUid());
            res.put("username",user.getUsername());
        }
        return res;
    }

    @GetMapping("/api/user/cur/unread")
    public Map<String,Object> getUnreadMsg(HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            ret.put("state",1);
        } else {
            long num = messageService.findMessagesByUidAndStatus(user.getUid(), 0);
            ret.put("state",0);
            ret.put("num",num);
        }
        return ret;
    }

    @PostMapping("/api/user/upload/image")
    public void uploadImage(@RequestParam("datafile")MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ;
        String path = session.getServletContext().getRealPath("/media");
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        File dirs = new File(path+"/"+user.getUid());
        if(!dirs.exists()) dirs.mkdirs();
        File newFile = new File(path+"/"+user.getUid()+"/"+user.getUsername()+"."+ext);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setImage("/media/"+user.getUid()+"/"+user.getUsername()+"."+ext);
        userService.updateUserInfo(user);
    }

    @PostMapping("/api/user/upload/bgimage")
    public void uploadBgImage(@RequestParam("datafile")MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ;
        String path = session.getServletContext().getRealPath("/media");
        File dirs = new File(path+"/"+user.getUid());
        if(!dirs.exists()) dirs.mkdirs();
        File newFile = new File(path+"/"+user.getUid()+"/bg-pic.png");
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/api/user/upload/staticfile")
    public void uploadStaticFile(@RequestParam("datafile")MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getLevel() < 4000) return ;

        if (file.getSize()/1024 > 100) {
            return ;
        }

        String path = session.getServletContext().getRealPath("/media");
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        File dirs = new File(path+"/"+user.getUid()+"/"+ext);
        if(!dirs.exists()) dirs.mkdirs();
        File newFile = new File(path+"/"+user.getUid()+"/"+ext+"/"+file.getOriginalFilename());
        try {
            file.transferTo(newFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @GetMapping("/api/user/getcode")
    public void checkCodeGenerator(HttpServletResponse resp,HttpSession session) {
        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();
        stringRedisConnection.hSet(session.getId(), "checkcode",gson.toJson(codeMap.get("code")));
        //redisTemplate.opsForHash().put(session.getId(), "checkcode", codeMap.get("code"));
        //redisTemplate.opsForHash().getOperations().expire(session.getId(), 30, TimeUnit.MINUTES);//30 mins 失效
//        System.out.println("expire:"+redisTemplate.opsForHash().getOperations().getExpire(session.getId(),TimeUnit.MINUTES));
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", -1);
        resp.setContentType("image/jpeg");
        ServletOutputStream sos;
        try {
            sos = resp.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/api/util/img/get_img_verify")
    @ResponseBody
    public Map<String,Object> getCheckCode(HttpSession session) throws Exception {
        Map<String, byte[]> pictureMap;
        File templateFile;  //模板图片
        File targetFile;  //
        Random random = new Random();
        int templateNo = 5;
        int targetNo = 1;

        String  srcfile = session.getServletContext().getRealPath("/static/img/templates/" + templateNo + ".png");
        templateFile = new File(session.getServletContext().getRealPath("/static/img/checkcode")+"/"+templateNo + ".png");
        FileUtil.copyFile(new File(srcfile), templateFile);
        String tarfile = session.getServletContext().getRealPath("/static/img/targets/" + targetNo + ".png");
        targetFile = new File(session.getServletContext().getRealPath("static/img/checkcode")+"/"+targetNo + ".png");
        FileUtil.copyFile(new File(tarfile), targetFile);

        //获取抠图坐标
        Map<String,Integer>  xyMap= VerifyImageUtil.generateCutoutCoordinates();
        //System.out.println(xyMap.get("X"));
        stringRedisConnection.hSet(session.getId(), "checkcode", gson.toJson(xyMap));
        stringRedisConnection.expire(session.getId(),60*60);
        //redisTemplate.opsForHash().put(session.getId(), "checkcode", xyMap);
        //redisTemplate.opsForHash().getOperations().expire(session.getId(), 60, TimeUnit.MINUTES);//60 mins 失效
        stringRedisConnection.hSet(session.getId(), "checkcode_verify", "0");
        //redisTemplate.opsForHash().put(session.getId(), "checkcode_verify", false);
        pictureMap = VerifyImageUtil.pictureTemplatesCut(templateFile, targetFile, "png", "png",Integer.valueOf(xyMap.get("X").toString()),Integer.valueOf(xyMap.get("Y").toString()));
        byte[] oriCopyImages = pictureMap.get("oriCopyImage");
        byte[] newImages = pictureMap.get("newImage");

        String newImage = Base64Utils.encodeToString(newImages);
        String oriCopyImage = Base64Utils.encodeToString(oriCopyImages);

//        System.out.println(newImage);

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("newImage", newImage);
        resultMap.put("oriCopyImage", oriCopyImage);
        resultMap.put("Y", xyMap.get("Y"));

        return resultMap;
    }

    @PostMapping("/api/util/img/check")
    @ResponseBody
    public Boolean checkCode(String X,String Y,HttpSession session) {
        Map<String,Integer> imgXyMap = gson.fromJson(stringRedisConnection.hGet(session.getId(), "checkcode"),new TypeToken<Map<String,Integer>>(){}.getType());//redisTemplate.opsForHash().get(session.getId(), "checkcode");
        if(imgXyMap!=null){
            if(Math.abs(Integer.parseInt(X)-imgXyMap.get("X")) <= 5){//&&Y.equals(imgXyMap.get("Y").toString()
                stringRedisConnection.hSet(session.getId(), "checkcode_verify", "1");
                //redisTemplate.opsForHash().put(session.getId(), "checkcode_verify", true);
                return true;
            }else{
                stringRedisConnection.hSet(session.getId(), "checkcode_verify", "0");
                //redisTemplate.opsForHash().put(session.getId(), "checkcode_verify", false);
                return false;
            }
        }else{
            stringRedisConnection.hSet(session.getId(), "checkcode_verify", "0");
            //redisTemplate.opsForHash().put(session.getId(), "checkcode_verify", false);
            return false;
        }
    }

    //CorrelationData中包含一个id(我们可以自定义，将其与发送的消息相关联)，当开启了confirm机制后，会收到包含该参数的回调，用于消息可靠性投递，可选的
    private CorrelationData corrData=new CorrelationData(String.valueOf(System.currentTimeMillis()));

    @Async
    public void sendMsgForEmail(String content) throws UnsupportedEncodingException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc", "邮件发送");
        messageProperties.getHeaders().put("type", "mail");
        Message message = new Message(content.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.send("mail_topic_exchange","mail.send", message,corrData);
    }


    @GetMapping("/api/rank/toindex")
    public Map<String,Object> findRanksByPageAndName() throws UnsupportedEncodingException {
        Map<String,Object> ret = new HashMap<>();
        List<User> users = userService.findRanksByPageAndName(0,10,null);
        List<Map<String,Object>> lists = new ArrayList<>();
        for(User user : users){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("uid",user.getUid());
            tmp.put("username", UserUtil.getStyleNameByUser(user,achievementService));
            tmp.put("ac_cnt",userService.findAc_numByUid(user.getUid()));
            tmp.put("note",user.getNote());
            tmp.put("rating",user.getRating());
            tmp.put("num",contestService.findContestsTotalByUid(user.getUid()));
            lists.add(tmp);
        }
        ret.put("rankset",lists);
        return ret;
    }

    @GetMapping("/api/rank/set")
    public Map<String,Object> findRanksByPageAndName(Integer page,String name) throws UnsupportedEncodingException {
        Map<String,Object> ret = new HashMap<>();
        name = name==null?null:new String(name.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
        List<User> users = userService.findRanksByPageAndName((page==null||page<=0)?0:page-1,50,name);
        List<Map<String,Object>> lists = new ArrayList<>();
        for(User user : users){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("uid",user.getUid());
            tmp.put("username", UserUtil.getStyleNameByUser(user,achievementService));
            tmp.put("ac_cnt",userService.findAc_numByUid(user.getUid()));
            tmp.put("note",user.getNote());
            tmp.put("rating",user.getRating());
            tmp.put("num",contestService.findContestsTotalByUid(user.getUid()));
            lists.add(tmp);
        }
        ret.put("pages",(userService.findUserTotal()+49)/50);
        ret.put("rankset",lists);
        return ret;
    }

    @GetMapping("/mobile/rank/set")
    public Map<String,Object> findMobileRanksByPageAndName(Integer page,String name) throws UnsupportedEncodingException {
        Map<String,Object> ret = new HashMap<>();
        name = new String(name.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
        List<User> users = userService.findRanksByPageAndName((page==null||page<=0)?0:page-1,50,name);
        List<Map<String,Object>> lists = new ArrayList<>();
        for(User user : users){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("uid",user.getUid());
            tmp.put("username", UserUtil.getStyleNameByUser(user,achievementService));
            tmp.put("ac_cnt",userService.findAc_numByUid(user.getUid()));
            tmp.put("note",user.getNote());
            tmp.put("rating",user.getRating());
            tmp.put("num",contestService.findContestsTotalByUid(user.getUid()));
            tmp.put("avatar",user.getImage());
            lists.add(tmp);
        }
        ret.put("pages",(userService.findUserTotal()+49)/50);
        ret.put("rankset",lists);
        return ret;
    }

    @PostMapping("/api/user/invite/newone")
    public Map<String,Integer> addOneInviteCode(String invitecode, HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 5) {
            ret.put("state",1);
            return ret;
        }
        Invitation invitation = new Invitation();
        invitation.setState(0);
        invitation.setInvitecode(invitecode);
        invitationService.insertInvitation(invitation);
        ret.put("state",0);
        return ret;
    }

    @PostMapping("/api/user/invite/newnum")
    public Map<String,Integer> addNumInviteCode(Integer num, HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 5) {
            ret.put("state",1);
            return ret;
        }
        for (int i = 0; i < num; i++) {
            Invitation invitation = new Invitation();
            invitation.setState(0);
            invitation.setInvitecode(MyUtil.randomInviteCode());
            invitationService.insertInvitation(invitation);
        }
        ret.put("state",0);
        return ret;
    }

    @GetMapping("/api/user/getpages")
    public Map<String,Long> getUserPages(String name,HttpSession session) throws UnsupportedEncodingException {
        Map<String,Long> ret = new HashMap<>();
        name = new String(name.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole()!=7) {
            return null;
        }
        ret.put("pages",(userService.findUserTotalByNameOrId(name)+19)/20);
        return ret;
    }

    @GetMapping("/api/user/getlist")
    public List<Map<String,Object>> getUserListByPageAndName(Integer page,String name,HttpSession session) {
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole()!=7) {
            return null;
        }
        if (page == null || page <= 0) page = 1;
        List<User> list = userService.findUserByPageAndName(page-1,name);
        for (User user1:list) {
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("uid",user1.getUid());
            tmp.put("username",user1.getUsername());
            tmp.put("rating",user1.getRating());
            tmp.put("role",user1.getRole());
            tmp.put("level",user1.getLevel());
            tmp.put("status",user1.getStatus());
            ret.add(tmp);
        }
        return ret;
    }

    @PostMapping("/api/user/adduser")
    public void addUser(@RequestParam("pre")String pre,@RequestParam("suf")Integer suf,@RequestParam("num")Integer num,HttpSession session,HttpServletResponse response) throws Exception {
        User user = (User) session.getAttribute("user");
        if (suf==null || num==null || user==null || suf>3 || suf<0 || num<=0 || num > 200 || user.getRole()<6) {
            return;
        }
        List<String> lists = new ArrayList<>();
        String passwd = EncodeUtil.sha1Encode("123456");
        for (int i = 0; i < num; i++) {
            StringBuilder name = new StringBuilder((pre==null||pre.length()==0)?"duser_":pre);
            switch (suf) {
                case 0: name.append(i);break;
                case 1: name.append(MyUtil.randomNumberString(6));break;
                case 2: name.append(MyUtil.randomAlphabetString(6));break;
                case 3: name.append(MyUtil.randomInviteCode());break;
            }
            boolean flag = userService.insertUser(null,name.toString(),passwd,"DOJ","123456");
            if (flag) {
                lists.add(name.toString());
            }
        }
        response.addHeader("Content-Disposition","attachment;filename=username.txt");
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write("用户名\n".getBytes(StandardCharsets.UTF_8));
        out.flush();
        for (String i : lists) {
            out.write(i.getBytes());
            out.write(10);
            out.flush();
        }
        out.write("默认密码皆为123456，请提醒用户登陆后自行更改密码\n".getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }

    @GetMapping("/api/user/getachi")
    public Map<String,Object> getUserAchievements(HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ret;
        }
        ret.put("username",UserUtil.getStyleNameByUser(user,achievementService));
        List<Achievementlist> achievementlists = achievementService.findAllAchisByUid(user.getUid());
        List<Map<String,Object>> lists = new ArrayList<>();
        for (Achievementlist achievementlist:achievementlists) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("aid",achievementlist.getAid());
            tmp.put("date",achievementlist.getDate());
            tmp.put("vaildday",achievementlist.getValidtime());
            Achievement achievement = achievementService.findAchiByAid(achievementlist.getAid());
            tmp.put("type",achievement.getStatus()==0);
            tmp.put("title",achievement.getTitle());
            tmp.put("desc",achievement.getDesciption());
            lists.add(tmp);
        }
        ret.put("achis",lists);
        return ret;
    }

    @PutMapping("/api/user/chooseachi")
    public Map<String,Integer> chooseAchievement(Integer aid,HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null || aid == null) {
            ret.put("state",1);
            return ret;
        }
        boolean flag = achievementService.checkUserOwnsAchiByUidAndAid(user.getUid(),aid);
        if (flag) {
            Achievement achievement = achievementService.findAchiByAid(aid);
            if (achievement.getStatus() == 0) {
                user.setPreaid(aid);
            } else {
                user.setSufaid(aid);
            }
            ret.put("state",0);
            userService.updateUserInfo(user);
        } else {
            ret.put("state",1);
        }
        return ret;
    }
}
