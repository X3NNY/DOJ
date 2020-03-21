package pers.dreamer.util.tools;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyUtil {

    public static Map<Integer,Double> infty = new HashMap<>();

    static {
        double v = .0;
        for (int i = 1; i <= 200; i++) {
            v += 1.0 / (i*i);
            infty.put(i,v);
        }
    }

    public static String codeDecode(String str) {
        StringBuffer s = new StringBuffer(str);
        for (int i = 0; i < s.length()-1; i++) {
            if (i+1 >= s.length()) break;
            if (s.charAt(i) == '\\') {
                if (s.charAt(i+1) == '\\') {
                    s.replace(i, i + 2, "\\");
                }
                if (s.charAt(i+1) == '\"') {
                    s.replace(i,i+2,"\"");
                }
            }
        }
        return s.toString();
    }

    public static long getDateMinutesDifference(Date endsTime, Date startTime) {
        long diff = endsTime.getTime() - startTime.getTime();
        return diff/(60*1000);
    }

    public static double getInfty(int num) {
        return infty.getOrDefault(num,1.64493406);
    }

    public static boolean isNumber(String name) {
        if(name==null||name.equals("")) return false;
        for(int i = 0;i < name.length();i++) if(!Character.isDigit(name.charAt(i))) return false;
        return true;
    }

    public static String randomInviteCode() {
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 6; i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String randomNumberString(int l) {
        String str="0123456789";
        Random random=new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < l; i++){
            int number=random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String randomAlphabetString(int l) {
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random=new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < l; i++){
            int number=random.nextInt(52);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
