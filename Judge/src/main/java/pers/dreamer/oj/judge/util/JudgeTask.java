package pers.dreamer.oj.judge.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class JudgeTask implements Callable<String>, Cloneable {
    private int sid;
    private int pid;
    private int timeLimit;
    private int memoryLimit;
    private int rMemoryLimit;
    private int kind;
    private boolean isSpecial;
    private String code;
    private int lang;
    private String in;
    private int id;
    private String dockerId;
    private String execCmd;

    public int getRMemoryLimit() {
        return rMemoryLimit;
    }

    public void setRMemoryLimit(int rMempeyLimit) {
        this.rMemoryLimit = rMempeyLimit;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    private String result;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public String execone() throws UnsupportedEncodingException, IOException {
        List<String> cmd = new ArrayList<>();
        cmd.add("docker");
        cmd.add("exec");
        cmd.add("-i");
        cmd.add(dockerId);
        cmd.add("/bin/bash");
        cmd.add("-c");
        cmd.add(String.format("python3 /judge.py \'%s\' \'/%s\' \'/%s\' %d %d %d %d", execCmd, in,
                in.replace(".in", ".ans"), timeLimit, memoryLimit * 1024,kind,isSpecial?1:0));

        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process process = processBuilder.start();

        try {
            process.waitFor(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String res = "";
        byte[] bytes = new byte[1024];
        InputStream inputStream = process.getInputStream();
        if (inputStream.read(bytes) != -1) {
            res = new String(bytes, StandardCharsets.UTF_8);
        } else {
            // throw new ExecSystemException();
        }
        res = res.replace("}", String.format(", \"id\": %d}", id)).trim();
        return res;
    }

    @Override
    public String call() throws Exception {
        return execone();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDockerId() {
        return dockerId;
    }

    public void setDockerId(String dockerId) {
        this.dockerId = dockerId;
    }

    public String getExecCmd() {
        return execCmd;
    }

    public void setExecCmd(String execCmd) {
        this.execCmd = execCmd;
    }

    @Override
    public JudgeTask clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return (JudgeTask) super.clone();
    }
}