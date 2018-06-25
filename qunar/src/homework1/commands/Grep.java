package homework1.commands;

import homework1.Utils;
import homework1.interfaces.Command;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Grep implements Command {

    @Override
    public String execute(String command){
        String regex = "grep\\s+(-[vEnhHcr]{1}\\s*)*(\\**[a-zA-Z]*[0-9]*\\**.*\\**[a-zA-Z]+\\**)*";
        try {
            if (Pattern.matches(regex, command)) {
                return One(command);
            }
        }catch (Exception e) {
            return "No such file or directory";
        }
        return "No such file or directory";
    }

    public String One(String command) {
        StringBuffer sb = new StringBuffer();
        Map<String,Object> map = Utils.getOptionFiles(command);
        List<String> options = (List<String>)map.get("options");
        List<String> f = (List<String>)map.get("files");
        String regex = "";
        if(f!=null&&f.size()>=1) {
            regex = f.remove(0);
        }
        List<String> files = Utils.getFiles(f,false);
        //判断是否需要递归查询
        if(options.contains("-r")) {
            //返回所有文件 第一个元素为正则表达式
            files = Two(command);
            regex = files.remove(files.size()-1);
        }
        // 判断是否只有1个文件 如果只有一个文件 则标志默任无需输出文件名
        boolean flag = files.size()==1?false:true;
        // 若有-h参数 无需输出文件名
        if(options.contains("-h")) {
            flag = false;
        }
        // 若有-H参数 输出文件名
        if(options.contains("-H")) {
            flag = true;
        }
        //对所有文件进行操作
        for(String p:files) {
            String str = select(regex,Utils.path+p,options,flag);
            if(options.contains("-c")) {
                int a = 0;
                int b = 0;
                try{
                    b = Integer.parseInt(sb.toString());
                }catch (Exception e) {
                    b = 0;
                }
                a = b+Integer.parseInt(str);
                sb = new StringBuffer(a+"");
            }else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    // 对文件夹进行递归查询
    public List<String> Two(String command) {
        // 标志位 判断是否是匹配正则表达式 命令第一个参数为命令名称
        // 第二个参数如果不为命令参数则为正则表达式
        boolean flag = false;
        // 判断是否有文件参数 如果没有，则为默认当前文件夹
        boolean f0 = true;
        List<String> list = new ArrayList<>();
        String[] s = command.trim().split(" ");
        String regex = "";
        for(int i=1;i<s.length;i++) {
            if(!Pattern.matches("-[a-zA-Z]{1}",s[i])) {
                if(flag) {
                    // 对文件目录进行递归查询 返回所有非目录文件
                    Utils.getAllFiles(Utils.path + s[i], list);
                    f0 = false;
                }else {
                    flag = false;
                    regex = s[i];
                }
            }
        }
        //如果为真 则没有找到文件参数 将当前文件夹作为默任参数 递归查找当前文件夹下所有文件
        if(f0) {
            Utils.getAllFiles(Utils.path.substring(0,Utils.path.length()-1),list);
        }
        list.add(regex);
        return list;
    }

    /**
     *  查看符合正则表达式的行
     *
     * @param regex   正则表达式
     * @param filename  需要匹配的文件名
     * @param options   命令参数
     * @param flag      是否需要打印文件名
     * @return
     */
    public String select(String regex,String filename,List<String> options,boolean flag){
        regex = "\\w*"+regex+"\\w*";
        File file = new File(filename);
        StringBuffer sb = new StringBuffer();
        int c = 0;    //记录匹配行数
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = "";
            // 记录行号
            int i = 1;
            while((str=bufferedReader.readLine())!=null) {
                // -v 反转查找 -n 标识行号
                if(Pattern.matches(regex,str)){
                    if(!options.contains("-v")) {
                        if(flag) {
                            sb.append(file.getPath()
                                    .replaceAll("E:\\\\qunar\\\\src\\\\test\\\\","")
                                    .replaceAll("\\\\","/") + ":");
                        }
                        if(options.contains("-n")) {
                            sb.append(i+":");
                        }
                        if(options.contains("-c")) {
                            c++;
                        }
                        sb.append(str);
                        sb.append("\n");
                    }
                }else if(options.contains("-v")) {
                    if(options.contains("-c")) {
                        c++;
                    }
                    if(flag) {
                        sb.append(file.getPath()
                                .replaceAll("E:\\\\qunar\\\\src\\\\test\\\\","")
                                .replaceAll("\\\\","/") + ":");
                    }
                    if(options.contains("-n")) {
                        sb.append(i+":");
                    }
                    sb.append(str);
                    sb.append("\n");
                }
                i++;
            }
            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if(options.contains("-c")) {
            return c+"";
        }
        return sb.toString();
    }
}
