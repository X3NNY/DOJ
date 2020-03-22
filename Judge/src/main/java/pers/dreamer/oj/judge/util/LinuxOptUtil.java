package pers.dreamer.oj.judge.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinuxOptUtil {
    public static void unzipTo(String zipPath, String newPath) throws IOException {
        File file = new File(zipPath), path = new File(newPath);
        if (!file.exists()) return;
        if (!path.exists()) path.mkdirs();
        List<String> cmd = new ArrayList<>(4);
        cmd.add("unzip");
        cmd.add(zipPath);
        cmd.add("-d");
        cmd.add(newPath);
        new ProcessBuilder(cmd).start();
    }

    public static void compileCPPToPath(String sourcePath, String targetPath) throws IOException {
        File file = new File(sourcePath);
        if (!file.exists()) return;
        List<String> cmd = new ArrayList<>(7);
        cmd.add("g++");
        cmd.add(sourcePath);
        cmd.add("-o");
        cmd.add(targetPath);
        cmd.add("-O2");
        cmd.add("-lm");
        cmd.add("-DDOJ");
        new ProcessBuilder(cmd).start();
    }

    public static void deleteAllFromPath(String s) throws IOException {
        File file = new File(s);
        if (!file.exists() || !s.startsWith("/var/oj")) return;
        List<String> cmd = new ArrayList<>(3);
        cmd.add("rm");
        cmd.add("-rf");
        cmd.add(s);
        new ProcessBuilder(cmd).start();
    }

    public static void moveData(String sourcePath, String targetPath) throws IOException {
        List<String> cmd = new ArrayList<>(3);
        cmd.add("mv");
        cmd.add(sourcePath);
        cmd.add(targetPath);
        new ProcessBuilder(cmd).start();
    }
}
