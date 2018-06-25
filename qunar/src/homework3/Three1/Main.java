package homework3.Three1;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("StringUtils.java");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = "";
            int count = 0;
            while((str = bufferedReader.readLine())!=null) {
                if(str.trim().equals("")) {
                    continue;
                }
                if(str.trim().length()>2&&str.trim().substring(0,2).equals("<!")) {
                    continue;
                }
                if(str.trim().length()>=2&&str.trim().substring(0,2).equals("//")){
                    continue;
                }
                count++;
            }
            System.out.println(count);
        }catch (IOException e) {

        }
    }
}
