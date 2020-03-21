package pers.dreamer.util.tools;

public class ValidCheck {
    public static boolean isValidString(String str) {
        char[] bar = {'|','\\','\'','\"',',','.','-','@','#','&','*','~','`','!',';','/','<','>','+',' ','*','^','%','(',')','=','?'};
        for (char c : bar) {
            if (str.indexOf(c) != -1) {
                return false;
            }
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) <= 30) {
                return false;
            }
        }
        return str.length()!=0;
    }
}
