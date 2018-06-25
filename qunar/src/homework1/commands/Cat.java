package homework1.commands;

import homework1.Utils;
import homework1.interfaces.Command;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Cat implements Command {

    @Override
    public String execute(String command){
        // 利用cat查询
        String regex1 = "cat\\s+(-[nbsv]\\s)*(\\**[a-zA-Z]*[0-9]*\\**.\\**[a-zA-Z]+\\**)*";
        // 利用cat创建文件并写入数据正则表达式
        String regex2 = "cat\\s+>\\s+[a-zA-Z]+.[a-zA-Z]+[0-9]*\\s*<<[A-Z]*";
        try {
            if (Pattern.matches(regex1, command)) {
                return One(command);
            }
            if (Pattern.matches(regex2, command)) {
                return Two(command);
            }
        }catch (Exception e) {
            return "No such file or directory";
        }
        return "No such file or directory";
    }

    /**
     *  执行符合正则表达式1的命令
     */
    private String One(String command) {
        //获取参数集合与文件名集合
        Map<String,Object> map = Utils.getOptionFiles(command);
        List<String> options = (List<String>)map.get("options");
        StringBuffer sb = new StringBuffer();
        sb.append("cat ");
        options.stream().forEach(p->sb.append(p+" "));
        List<String> files = (List<String>)map.get("files");
        List<String> f = Utils.getFiles(files,true);
        if(f.size()==1) {
            return select(sb.toString(),Utils.path+f.get(0));
        }
        //对所有符合条件的文件都执行不带参数cat操作 并将其写入临时文件
        for(String filename:f) {
            String str = select("cat ",Utils.path+filename);
            Utils.writeStr(str,Utils.temp,true);
        }
        //对临时文件进行带参数的cat操作
        String result =  select(sb.toString(),Utils.temp);
        Utils.deleteFile(Utils.temp);
        return result.length()>1?result.substring(0,result.length()-1):result;
    }

    /**
     * 执行符合正则表达式2的命令
     */
    private String Two(String command) {
        String filename = command.substring(command.indexOf(">")+1,command.indexOf("<<")).trim();
        filename = Utils.path+filename;
        String flag = command.substring(command.indexOf("<<")+2);
        Scanner scanner = new Scanner(System.in);
        try{
            File file = new File(filename);
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            while(true) {
                System.out.print("> ");
                String s = scanner.nextLine();
                if(s.equals(flag)) {
                    break;
                }
                bufferedWriter.write(s);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return One("cat "+filename.substring(filename.lastIndexOf("/")+1));
    }

    /**
     * 进行cat命令查询操作
     *
     * @param command   命令 包括参数
     * @param filename  需要操作文件
     * @return
     */
    public String select(String command,String filename) {
        File file = new File(filename);
        if(!file.exists()) {
            return "No such file or directory";
        }else {
            try{
                //标志位标志空格是否输出
                boolean flag = true;
                int i = 1;
                StringBuffer sb = new StringBuffer();
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = "";
                while((str = bufferedReader.readLine())!=null) {
                    if(str.equals("")) {
                        if(command.contains("-s")&&!flag) {
                            continue;
                        }
                        flag = false;
                        if(!command.contains("-b")&&command.contains("-n")) {
                            sb.append(i++);
                            sb.append(" ");
                        }
                    }else {
                        flag = true;
                        if(command.contains("-b")||command.contains("-n")) {
                            sb.append(i++);
                            sb.append(" ");
                        }
                    }
                    sb.append(str);
                    sb.append("\n");
                }
                fileInputStream.close();
                inputStreamReader.close();
                bufferedReader.close();
                return sb.toString();
            }catch (IOException e) {
                System.out.println(e.getMessage());
                return "No such file or directory";
            }
        }
    }

}
