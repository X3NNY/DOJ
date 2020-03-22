package pers.dreamer.oj.judge.util;

import java.util.HashMap;
import java.util.Map;

public class LangsUtil {
    public static final Map<String,String> execCmds = new HashMap<>();
    static {
        execCmds.put("GCC","/main");
        execCmds.put("G++","/main");
        execCmds.put("JAVA","javaDOJMain");
        execCmds.put("Python","python3DOJ/__pycache__/main.cpython-36.opt-1.pyc");
        //....
    }

    public static final  Map<String,Integer> langsCode = new HashMap<>();
    static {
        langsCode.put("GCC",0);
        langsCode.put("G++",1);
        langsCode.put("JAVA",2);
        langsCode.put("Python",3);
        //...
    }
}
