package homework3;

import java.io.*;

/**
 *
 */
/*
 *测试整段注释
  12345
 */
public class Main3 {
    //测试注释
    //
    public static void main(String args[]) throws IOException{
        File file = new File("src\\homework1\\Main3.java");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int count = 0;
        String str = "";
        boolean bigflag = true;
        while ((str=bufferedReader.readLine())!=null) {
            boolean flag = false;
            for(int i=0;i<str.length();i++) {
                if(str.charAt(i)==' '||" ".equals(str.charAt(i))) {
                    continue;
                }else if(str.charAt(i)!='/'){
                    if(str.charAt(i)=='*') {
                        if(i+1<str.length()&&str.charAt(i+1)=='/') {
                            bigflag = true;
                            break;
                        }
                    }else {
                        flag = true;
                        break;
                    }
                }else {
                    if(str.charAt(i+1)=='*') {
                        bigflag = false;
                        break;
                    }else {
                        break;
                    }
                }
            }
            if(flag&&bigflag) {
                count++;
            }
        }
        System.out.println(count);
        fileReader.close();
        bufferedReader.close();
    }
}
