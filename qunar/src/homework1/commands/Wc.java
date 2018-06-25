package homework1.commands;

import homework1.Utils;
import homework1.interfaces.Command;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Wc implements Command {

    @Override
    public String execute(String command){
        try {
            //wc 指定文件
            String regex1 = "wc\\s+(-[lwc]{1}\\s)*\\s*(\\**[a-zA-Z]*[0-9]*\\**.*\\**[a-zA-Z]*\\**)+";
            //wc 输入
            String regex2 = "wc\\s+(-[lwc]{1}\\s)*-*\\s*<<[A-Z]+";
            if(Pattern.matches(regex2,command)) {
                return Two(command);
            }
            if(Pattern.matches(regex1,command)) {
                return One(command);
            }
        }catch (Exception e) {
            return "No such file or directory";
        }
        return "No such file or directory";
    }

    /**
     * 对正则表达式1符合的命令操作
     *
     * @param command
     * @return
     */
    public String One(String command) {
        Map<String,Object> map = Utils.getOptionFiles(command);
        List<String> options = (List<String>)map.get("options");
        List<String> files = (List<String>)map.get("files");
        String str[] = select(Utils.path+files.get(0));
        StringBuffer sb = new StringBuffer();
        if(options==null||options.size()==0) {
            sb.append(str[0]+"  "+str[1]+" "+str[2]);
        }
        //是否输出行号
        if(options.contains("-l")) {
            sb.append(str[0]+"  ");
        }
        //是否输出字数
        if(options.contains("-w")) {
            sb.append(str[1]+" ");
        }
        //是否输出字节数
        if(options.contains("-c")) {
            sb.append(str[2]);
        }
        sb.append(" "+files.get(0));
        return sb.toString();
    }


    /**
     * 对正则表达式2符合的进行操作
     *
     * @param command
     * @return
     */
    public String Two(String command) {
        String s[] = command.trim().split("<<");
        Map<String,Object> map = Utils.getOptionFiles(s[0]);
        List<String> options = (List<String>)map.get("options");
        String stop = s[1];
        Scanner scanner = new Scanner(System.in);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while(true) {
            String str = scanner.nextLine();
            if(stop.equals(str)) {
                break;
            }
            i++;
            sb.append(str);
            sb.append("\n");
        }
        sb.deleteCharAt(sb.length()-1);
        int l = i;
        int w = sb.toString().trim().split("\\s").length;
        int c = sb.toString().getBytes().length;
        StringBuffer stringBuffer = new StringBuffer();
        if(options==null||options.size()==0) {
            stringBuffer.append(l+"  "+w+" "+c);
        }
        //是否输出行号
        if(options.contains("-l")) {
            stringBuffer.append(l+"  ");
        }
        //是否输出字号
        if(options.contains("-w")) {
            stringBuffer.append(w+" ");
        }
        //是否输出字节数
        if(options.contains("-c")) {
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    public String[] select(String filename) {
        File file = new File(filename);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = "";
            while((str=bufferedReader.readLine())!=null) {
                sb.append(str);
                i++;
                sb.append("\n");
            }
            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        sb.deleteCharAt(sb.length()-1);
        // 行号
        int l = i;
        // 字数
        int w = sb.toString().trim().split("\\s").length;
        // 字节数
        int c = sb.toString().getBytes().length;
        return new String[]{l+"",w+"",c+""};
    }
}
