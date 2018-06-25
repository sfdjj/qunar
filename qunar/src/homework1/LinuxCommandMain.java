package homework1;

import homework1.interfaces.Command;

import java.util.Scanner;

public class LinuxCommandMain {

    /**
     *   main方法 输入命令 接受命令
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String command = scanner.nextLine();
            if(command==null||command.equals("")) {
                continue;
            }
            //执行命令
            execute(command);
        }
    }

    private static void execute(String command) {
        String str = "";
        // 判断结果是否需要写入新文件
        if(command.contains(">")&&!command.contains("<<")) {
            // 获取处理结果
            str = getResult(command.split(">")[0].trim());
            if(str.equals("No such file or directory")) {
                System.out.println(str);
                return ;
            }
            String filename = Utils.path+command.split(">")[1].trim();
            // 写入新文件
            Utils.writeStr(str,filename,true);
            return;
        }else {
            // 执行命令
            str = getResult(command);
        }
        System.out.println(str);
    }

    private static String getResult(String command) {
        String result = "";
        // 判断命令中是否含有管道
        if(command.contains("|")) {
            String s[] = command.split("\\|");
            String c = getObjectResult(s[0].trim());
            Utils.writeStr(c,Utils.tempI,false);
            // 对管道中命令依次执行
            for(int i=1;i<s.length;i++) {
                // 构造新命令
                String newCommand = s[i].trim()+" "+Utils.tempI.replaceAll(Utils.path,"");
                // 获取单个命令结果
                result = getObjectResult(newCommand);
                // 将单个命令结果写入临时文件 作为管道中命令的处理文件
                Utils.writeStr(c,Utils.tempI,false);
            }
            // 删除处理文件
            Utils.deleteFile(Utils.temp);
            return result;
        }else {
            result = getObjectResult(command);
        }
        return result;
    }

    private static String getObjectResult(String command) {
        String s[] = command.trim().split(" ");
        //获取执行命令名称
        String className = Utils.commandPath+s[0].substring(0,1).toUpperCase()+s[0].substring(1);
        try{
            // 加载命令名称类
             Class<Command> clazz = (Class<Command>)Class.forName(className);
             // 构造命令执行对象
             Command target = clazz.newInstance();
             // 返回结果集
             return target.execute(command);
        }catch (ClassNotFoundException e) {
            return "No such file or directory";
        }catch (InstantiationException e) {
            return "No such file or directory";
        }catch (IllegalAccessException e) {
            return "No such file or directory";
        }
    }

}
