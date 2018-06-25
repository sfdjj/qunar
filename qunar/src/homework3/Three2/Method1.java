package homework3.Three2;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class Method1 {
    public void execute() {
        try{
            URL url = new URL("http://qfc.qunar.com/homework/sdxl_prop.txt");
            InputStream inputStream = url.openStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //自然排序
            List<String> list1 = new ArrayList<>();
            //索引排序
            Map<String,String> list2 = new HashMap<>();
            //文本排序
            List<String> list3 = new ArrayList<>();
            //文本倒序
            List<String> list4 = new ArrayList<>();
            String str = "";
            while((str=bufferedReader.readLine())!=null) {
                String s[] = str.split("\t");
                list1.add(s[1]);
                list2.put(s[0],s[1]);
                list3.add(s[1]);
                list4.add(s[1]);
            }
            str = "";
            Collections.sort(list3,(p1,p2)->p1.compareTo(p2));
            Collections.sort(list4,(p1,p2)->p2.compareTo(p1));
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            url = new URL("http://qfc.qunar.com/homework/sdxl_template.txt");
            inputStream = url.openStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            File file = new File("sdxl.txt");
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            String regex = "[^a-zA-Z]*\\$[a-z-A-Z]*\\([0-9]+\\)[^a-zA-Z]*";
            while((str=bufferedReader.readLine())!=null) {
                if(Pattern.matches(regex,str)) {
                    if(str.contains("natureOrder")) {
                        str = natureOrder(str,list1);
                    }
                    if(str.contains("indexOrder")) {
                        str = indexOrder(str,list2);
                    }
                    if(str.contains("charOrder")&&!str.contains("charOrderDESC")) {
                        str = charOrder(str,list3);
                    }
                    if(str.contains("charOrderDESC")) {
                        str = charOrderDESC(str,list4);
                    }
                }
                bufferedWriter.write(str);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static String natureOrder(String s,List<String> list) {
        int begin = s.indexOf("$natureOrder(")+13;
        int end = s.indexOf(")",begin);
        int index = Integer.parseInt(s.substring(begin,end));
        s = s.substring(0,begin-13)+list.get(index)+s.substring(end+1);
        return s;
    }
    public static String indexOrder(String s,Map<String,String> map) {
        int begin = s.indexOf("$indexOrder(")+12;
        int end = s.indexOf(")",begin);
        String key = s.substring(begin,end);
        s = s.substring(0,begin-12)+map.get(key)+s.substring(end+1);
        return s;
    }
    public static String charOrder(String s,List<String> list) {
        int begin = s.indexOf("$charOrder(")+11;
        int end = s.indexOf(")",begin);
        int index = Integer.parseInt(s.substring(begin,end));
        s = s.substring(0,begin-11)+list.get(index)+s.substring(end+1);
        return s;
    }

    public static String charOrderDESC (String s,List<String> list) {
        int begin = s.indexOf("$charOrderDESC(")+15;
        int end = s.indexOf(")",begin);
        int index = Integer.parseInt(s.substring(begin,end));
        s = s.substring(0,begin-15)+list.get(index)+s.substring(end+1);
        return s;
    }
}
