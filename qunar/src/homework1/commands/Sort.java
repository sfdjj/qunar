package homework1.commands;

import homework1.Utils;
import homework1.interfaces.Command;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Sort implements Command{

    @Override
    public String execute(String command) {
        String regex = "sort\\s+(-[rf]\\s*)*(\\**[a-zA-Z]*[0-9]*\\**.\\**[a-zA-Z]+\\**)*";
        if(Pattern.matches(regex,command)) {
            return One(command);
        }
        return "No such file or directory";
    }

    /**
     * 对返回的所有行进行排序
     *
     * @param command
     * @return
     */
    public String One(String command){
        Map<String,Object> map = Utils.getOptionFiles(command);
        List<String> files = (List<String>)map.get("files");
        List<String> options = (List<String>)map.get("options");
        List<String> list = new ArrayList<>();
        for(String file:files) {
            select(Utils.path+file,list);
        }
        StringBuffer sb = new StringBuffer();
        //根据ASCLL排序
        if(options==null||options.size()==0) {
            Collections.sort(list,(p1,p2)->p1.compareTo(p2));
        }
        //将小写字母视为大写字母
        if(options.contains("-f")) {
            Collections.sort(list,(p1,p2)->p1.compareToIgnoreCase(p2));
        }
        //反转查询
        if(options.contains("-r")) {
            if(options.contains("-f")) {
                Collections.sort(list,(p1,p2)->p2.compareToIgnoreCase(p1));
            }else {
                Collections.sort(list,(p1,p2)->p2.compareTo(p1));
            }
        }
        list.stream().forEach(p->sb.append(p+"\n"));
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    /**
     * 返回所有行
     *
     * @param filename
     * @param list
     * @return
     */
    public List<String> select(String filename,List<String> list) {
        File file = new File(filename);
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = "";
            while ((str=bufferedReader.readLine())!=null) {
                list.add(str);
            }
            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
