package pers.dreamer.oj.judge.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Component("judge")
@Scope("prototype")
@PropertySource("classpath:CodeConfig.properties")
public abstract class Judge{
    public JudgeTask task = null;

    public JudgeTask getTask() {
        return task;
    }

    public void setTask(JudgeTask task) {
        this.task = task;
    }

    @Value("${DOCKER_NAME}")
    public String DOCKER_NAME;

    public String create() throws IOException{
        List<String> cmd = new ArrayList<>();
        cmd.add("docker");
        cmd.add("run");
        cmd.add("-d");
        cmd.add("-t");
        cmd.add(DOCKER_NAME);
        System.out.println(cmd);
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process process = processBuilder.start();

        String dockerId = "";
        byte[] bytes = new byte[1024];
        InputStream inputStream = process.getInputStream();
        if (inputStream.read(bytes) != -1) {
            dockerId = new String(bytes, StandardCharsets.UTF_8);
        } else {
            // throw new CreateDockerFailedException();
        }
        System.out.println(dockerId);
        dockerId = dockerId.trim();
        task.setDockerId(dockerId);
        return dockerId;
    }; //创建docker 返回docker——id
    abstract public void build() throws IOException;
    abstract public String complier() throws IOException; //编译
    public  void destroy() throws IOException{
        List<String> cmd = new ArrayList<>();
        cmd.add("docker");
        cmd.add("rm");
        cmd.add("-f");
        cmd.add(dockerId);
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        processBuilder.start();
    };
    abstract public JudgeResult exec() throws Exception;
    public static final Gson gson = new Gson();
    @Value("${initCores}")
    public int initcores;

    public Judge() {

    }

    @Value("${maxCores}")
    public int maxcores;
    @Value("${keepAliveTime}")
    public int keepAliveTime;
    @Value("${PROBLEM_PATH}")
    public String PROBLEM_PATH;
    public String dockerId;
    public ThreadPoolExecutor th;

    public String getDockerId() {
        return dockerId;
    }

    public void setDockerId(String dockerId) {
        this.dockerId = dockerId;
    }
}