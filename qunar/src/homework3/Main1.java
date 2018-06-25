package homework3;

import java.io.*;

public class Main1 {
    //测试注释
    public static void main(String args[]) throws Exception{
        File file = new File("src\\homework1\\Method2.java");
        FileInputStream inputStream = new FileInputStream(file);
        int length = inputStream.available();
        byte[] bytes = new byte[length];
        while (inputStream.read(bytes)!=-1) {
            String str = new String(bytes,0,bytes.length);
            System.out.println(str.split("[\r]").length);
        }
    }
}