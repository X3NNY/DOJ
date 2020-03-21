package pers.dreamer.util.tools;

import java.io.*;

public class FileUtil {
    public static boolean copyFile(File src, File dest)  {
        byte buf[] = new byte[1024];
        OutputStream out = null;
        InputStream is = null;
        try {
            is = new FileInputStream(src);
            out = new FileOutputStream(dest);
            int len = 0;
            while((len = is.read(buf)) != -1) out.write(buf,0,len);
            out.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
