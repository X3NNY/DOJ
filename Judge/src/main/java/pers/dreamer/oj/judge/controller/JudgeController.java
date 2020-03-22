package pers.dreamer.oj.judge.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pers.dreamer.oj.judge.dto.ResultDto;
import pers.dreamer.oj.judge.pojo.Code;
import pers.dreamer.oj.judge.pojo.Problem;
import pers.dreamer.oj.judge.pojo.Submitlist;
import pers.dreamer.oj.judge.service.ProblemService;
import pers.dreamer.oj.judge.service.SubmitlistService;
import pers.dreamer.oj.judge.util.*;
import pers.dreamer.oj.judge.dto.CodeDto;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@EnableAsync
@Slf4j
public class JudgeController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    SubmitlistService submitlistService;

    @Autowired
    ProblemService problemService;

    @Autowired
    ApplicationContext applicationContext;

    @RabbitListener(queues = {"oj_judge"})
    public void receiveJudge(Integer sid){
        System.out.println("收到消息:"+sid);
        Code code = problemService.findCodeBySid(sid);
        CodeDto codeDto = new CodeDto();
        Submitlist submitlist = submitlistService.findSubmitlistLangBySid(sid);
        Problem problem = problemService.findProblemByPid(submitlist.getPid());
        codeDto.setSid(sid);
        codeDto.setPid(submitlist.getPid());
        codeDto.setSpecial(problem.getSpecial()==1);
        codeDto.setMemoryLimit(problem.getMemorylimit());
        codeDto.setTimeLimit(problem.getTimelimit());
        codeDto.setKind(problem.getType());
        codeDto.setCode(code.getText());
        codeDto.setLang(submitlist.getLang());
        //执行
        try {
            switch (codeDto.getKind()) {
                case 0:execCode(codeDto);break; //传统题
                case 1:execCode1(codeDto);break; //交互题
                case 2:execCode2(codeDto);break; //接口实现题
                case 3:execCode3(codeDto);break; //提交答案题
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void execCode3(CodeDto codeDto) {
    }

    private void execCode2(CodeDto codeDto) throws IOException {
        JudgeTask task = new JudgeTask();
        task.setLang(LangsUtil.langsCode.get(codeDto.getLang()));
        Judge judge = null;
        switch (task.getLang()) {
            case 0: judge = applicationContext.getBean("icJudge", ICJudge.class);
            break;
            case 1: judge = applicationContext.getBean("icppJudge", ICPPJudge.class);
            break;
            case 2: judge = applicationContext.getBean("ijavaJudge", IJavaJudge.class);
                task.setMemoryLimit(128);
                task.setRMemoryLimit(codeDto.getMemoryLimit());
            break;
            case 3: judge = applicationContext.getBean("ipyJudge", IPythonJudge.class);
            break;
        }
        setTaskAndJudge(codeDto, task, judge);
    }

    private void setTaskAndJudge(CodeDto codeDto, JudgeTask task, Judge judge) throws IOException {
        JudgeResult judgeResult = new JudgeResult();
        task.setPid(codeDto.getPid());
        task.setTimeLimit(codeDto.getTimeLimit());
        task.setMemoryLimit(codeDto.getMemoryLimit());
        task.setCode(codeDto.getCode());
        task.setSpecial(codeDto.getSpecial());
        task.setKind(codeDto.getKind());
        task.setExecCmd(LangsUtil.execCmds.get(codeDto.getLang()));
        judge.th = new ThreadPoolExecutor(judge.initcores,judge.maxcores,judge.keepAliveTime, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(),new ThreadPoolExecutor.DiscardOldestPolicy());
        judge.setTask(task);
        runExec(codeDto, judgeResult, judge);
    }

    private void execCode1(CodeDto codeDto) throws IOException {
        JudgeTask task = new JudgeTask();
        task.setLang(LangsUtil.langsCode.get(codeDto.getLang()));
        Judge judge = null;
        switch (task.getLang()){
            case 0: judge = applicationContext.getBean("acJudge", ACJudge.class);
                break;
            case 1: judge = applicationContext.getBean("acppJudge",ACPPJudge.class);
                break;
            case 2: judge = applicationContext.getBean("ajavaJudge",AJavaJudge.class);
                task.setMemoryLimit(300);
                task.setRMemoryLimit(codeDto.getMemoryLimit());
                break;
            case 3: judge = applicationContext.getBean("apyJudge",APythonJudge.class);
                break;
            //...
        }
        setTaskAndJudge(codeDto, task, judge);
    }

    private void execCode(CodeDto codeDto) throws IOException {
        JudgeTask task = new JudgeTask();
        task.setLang(LangsUtil.langsCode.get(codeDto.getLang()));
        Judge judge = null;
        switch (task.getLang()){
            case 0: judge = applicationContext.getBean("cJudge",CJudge.class);
            break;
            case 1: judge = applicationContext.getBean("cppJudge",CPPJudge.class);
            break;
            case 2: judge = applicationContext.getBean("javaJudge",JavaJudge.class);
                task.setMemoryLimit(300);
                task.setRMemoryLimit(codeDto.getMemoryLimit());
            break;
            case 3: judge = applicationContext.getBean("pyJudge",PythonJudge.class);
            break;
            //...
        }
        setTaskAndJudge(codeDto, task, judge);
    }

    private void runExec(CodeDto codeDto, JudgeResult judgeResult, Judge judge) throws IOException {
        String dockerId = judge.create();
        try {
            judge.setDockerId(dockerId);
            judge.build();
            String msg = judge.complier();
            System.out.println(msg);
            if (msg.contains("error") || msg.contains("Error")) {
                judgeResult.setRes(9);
                judgeResult.setMsg(msg);
            } else {
                judgeResult = judge.exec();
            }
        } catch (Exception e) {
            e.printStackTrace();
            judgeResult.setRes(8);
        }
        judge.destroy();

        ResultDto resultDto = new ResultDto();
        resultDto.setSid(codeDto.getSid());
        resultDto.setPos(judgeResult.getIndex());
        resultDto.setTimeUsed(judgeResult.getMaxTime());
        resultDto.setMemoryUsed(judgeResult.getMaxMemory());
        resultDto.setMsg(judgeResult.getMsg()==null?null:judgeResult.getMsg().substring(0,Math.min(judgeResult.getMsg().length(),512)));
        resultDto.setResult(judgeResult.getRes());
        resultDto.setScore(judgeResult.getScore());

        //发送resultDto
        sendMessageToSSM(resultDto);
    }

    @Async
    public void sendMessageToSSM(ResultDto resultDto) {
        System.out.println("发送判题结果更新数据库请求:"+resultDto);
        rabbitTemplate.convertAndSend("oj.direct.result","oj.jresult",resultDto);
    }


}
