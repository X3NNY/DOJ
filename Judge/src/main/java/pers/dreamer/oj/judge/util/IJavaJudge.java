package pers.dreamer.oj.judge.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component("ijavaJudge")
@PropertySource("classpath:CodeConfig.properties")
@Scope("prototype")
public class IJavaJudge extends JavaJudge {
    @Value("${JAVA_DOCKER_NAME}")
    public String DOCKER_NAME;
    public IJavaJudge(){}

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
        cmd.add(String.format("cat /pre.java>>/Main.java && echo \"%s\">>/Main.java && cat /suf.java>>/Main.java", task.getCode()));
        System.out.println(task.getCode());
        Process process = new ProcessBuilder(cmd).start();
        try{
            process.waitFor(2, TimeUnit.SECONDS);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
