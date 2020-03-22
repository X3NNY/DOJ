package pers.dreamer.oj.judge.util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component("acppJudge")
@PropertySource("classpath:CodeConfig.properties")
@Scope("prototype")
public class ACPPJudge extends CPPJudge {

    @Value("${CPP_DOCKER_NAME}")
    public String DOCKER_NAME;
    public ACPPJudge(){}

    @Override
    public String complier() throws IOException {
        List<String> cmd = new ArrayList<>();
        cmd.add("docker");
        cmd.add("exec");
        cmd.add("-i");
        cmd.add(dockerId);
        cmd.add("/bin/bash");
        cmd.add("-c");
        cmd.add("gcc /alt.cpp /main.cpp -o /main -std=c++11 -lm -O2 -DDOJ");

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
}
