package homework3.Three2;


import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class Method2 {
    public String execute() {
        StringBuffer sb2 = new StringBuffer();
        try {
            URL url = new URL("http://qfc.qunar.com/homework/sdxl_prop.txt");
            InputStream inputStream = url.openStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            List<String> list = new ArrayList<>();
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                String s[] = str.split("\t");
                list.add(s[1]);
            }
            str = "";
            int i0 = 0;
            int i1 = 3;
            int i2 = 1;
            int i3 = 2;
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            url = new URL("http://qfc.qunar.com/homework/sdxl_template.txt");
            inputStream = url.openStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            File file = new File("sdx2.txt");
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            String regex = "[^a-zA-Z]*\\$[a-z-A-Z]*\\([0-9]+\\)[^a-zA-Z]*";
            while ((str = bufferedReader.readLine()) != null) {
                if (Pattern.matches(regex, str)) {
                    if (str.contains("natureOrder")) {
                        str = natureOrder(str, list, i0);
                        i0 +=4;
                    }
                    if (str.contains("indexOrder")) {
                        str = indexOrder(str, list, i1);
                        i1 +=4;
                    }
                    if (str.contains("charOrder") && !str.contains("charOrderDESC")) {
                        str = charOrder(str, list, i2);
                        i2 +=4;
                    }
                    if (str.contains("charOrderDESC")) {
                        str = charOrderDESC(str, list, i3);
                        i3 +=4;
                    }
                }
                bufferedWriter.write(str);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sb2.toString();
    }
    public String natureOrder(String s,List<String> list,int index) {
        int begin = s.indexOf("$natureOrder(");
        int end = s.indexOf(")",begin);
        s = s.substring(0,begin)+list.get(index)+s.substring(end+1);
        return s;
    }
    public String indexOrder(String s,List<String> list,int index) {
        int begin = s.indexOf("$indexOrder(");
        int end = s.indexOf(")",begin);
        s = s.substring(0,begin)+list.get(index)+s.substring(end+1);
        return s;
    }
    public String charOrder(String s,List<String> list,int index) {
        int begin = s.indexOf("$charOrder(");
        int end = s.indexOf(")",begin);
        s = s.substring(0,begin)+list.get(index)+s.substring(end+1);
        return s;
    }

    public String charOrderDESC (String s,List<String> list,int index) {
        int begin = s.indexOf("$charOrderDESC(");
        int end = s.indexOf(")",begin);
        s = s.substring(0,begin)+list.get(index)+s.substring(end+1);
        return s;
    }
}
