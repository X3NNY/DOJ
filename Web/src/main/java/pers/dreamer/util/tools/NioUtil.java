package pers.dreamer.util.tools;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioUtil {

    private NioUtil() {}

    public static String readNIO(String path) {
        FileInputStream fin = null;
        StringBuilder str = new StringBuilder();
        try {
            fin = new FileInputStream(new File(path));
            FileChannel channel = fin.getChannel();

            int capacity = 1024;// 字节
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            int length = -1;
            while ((length = channel.read(bf)) != -1) {
                bf.clear();
                byte[] bytes = bf.array();
                str.append(new String(bytes, 0, length,"utf-8"));
            }
            channel.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str.toString();
    }

    public static void writeNIO(String path,String text) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(path));
            FileChannel channel = fos.getChannel();
            byte[] bytes = text.getBytes("utf-8");
            ByteBuffer src = ByteBuffer.wrap(bytes);
            while (channel.write(src) != 0) ;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}