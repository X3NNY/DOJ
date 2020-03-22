package pers.dreamer.oj.judge.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component("cppJudge")
@PropertySource("classpath:CodeConfig.properties")
@Scope("prototype")
public class CPPJudge extends Judge {

    @Value("${CPP_DOCKER_NAME}")
    public String DOCKER_NAME;
    public CPPJudge(){}

    @Override
    public void build() throws IOException {
        List<String> cmd = new ArrayList<>();
        cmd.add("docker");
        cmd.add("cp");
        cmd.add(String.format("%s/%d/", PROBLEM_PATH,task.getPid()));
        cmd.add(String.format("%s:/", dockerId));

        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        processBuilder.start();

        cmd.clear();
        cmd.add("docker");
        cmd.add("exec");
        cmd.add("-i");
        cmd.add(dockerId);
        cmd.add("/bin/bash");
        cmd.add("-c");
        cmd.add(String.format("mv /%d/* /",task.getPid()));
        new ProcessBuilder(cmd).start();

        cmd.clear();
        cmd.add("docker");
        cmd.add("exec");
        cmd.add("-i");
        cmd.add(dockerId);
        cmd.add("/bin/bash");
        cmd.add("-c");
        cmd.add(String.format("echo \"%s\">/main.cpp", task.getCode()));
        Process process = new ProcessBuilder(cmd).start();
        try{
            process.waitFor(5,TimeUnit.SECONDS);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String complier() throws IOException {
        List<String> cmd = new ArrayList<>();
        cmd.add("docker");
        cmd.add("exec");
        cmd.add("-i");
        cmd.add(dockerId);
        cmd.add("/bin/bash");
        cmd.add("-c");
        cmd.add("g++ /main.cpp -o /main -std=c++11 -lm -O2 -DDOJ");

        long start = System.currentTimeMillis();

        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process process = processBuilder.start();
        try {
            process.waitFor(10,TimeUnit.SECONDS);
            long end = System.currentTimeMillis();
            if (end - start >= 10000) {
                throw new IOException();
            }
        } catch (Exception e) {
            throw new IOException();
        }

        byte[] bytes = new byte[1024];
        InputStream inputStream = process.getErrorStream();
        StringBuilder res = new StringBuilder();
        while (inputStream.read(bytes) != -1) {
            res.append(new String(bytes, StandardCharsets.UTF_8));
            res.append("\n");
        }
        return res.toString();
    }

    private List<JudgeTask> tasks= new ArrayList<>();
    public JudgeResult exec() throws Exception {
        File files = new File(PROBLEM_PATH+"/"+task.getPid());
        JudgeResult res = new JudgeResult();
        System.out.println(files.getAbsolutePath()+"："+files.isDirectory());
        if(files.isDirectory()) {
            int tot = 1;
            for(File file: Objects.requireNonNull(files.listFiles())){
                if(file.getName().endsWith(".in")){
                    System.out.println(file.getName());
                    JudgeTask tmp = null;
                    try {
                        tmp = task.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    tmp.setIn(file.getName());
                    tmp.setId(tot++);
                    tasks.add(tmp);
                }
            }
            List<Future<String>> futures = th.invokeAll(tasks);
            int ac_cnt = 0;
            int pos = 10000;//错在第几组数据
            for(Future<String> future : futures) {
                //{"result": 0, "timeused": 0, "memoryused": 9036}
                SimpleResult simpleResult = gson.fromJson(future.get(10,TimeUnit.SECONDS),SimpleResult.class);
                //0->AC,1->PE,2->TLE,3->MLE,4->WA,5->RE,6->OLE,8->SE
                int flag = simpleResult.getResult();
                res.setMaxTime(Integer.max(res.getMaxTime(), simpleResult.getTimeused()));
                res.setMaxMemory(Integer.max(res.getMaxMemory(), simpleResult.getMemoryused()));
                if (flag == 0) ac_cnt++;
                else {
                    if (pos > simpleResult.getId()) {
                        pos = simpleResult.getId();
                        res.setRes(simpleResult.getResult());
                        res.setIndex(pos);
                    }
                }
            }
            res.setScore(ac_cnt*1.0f/futures.size()*100);
            th.shutdownNow();
        }else{
            //exception
            System.out.println("目录出错");
        }
        return  res;
    }
}