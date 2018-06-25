package homework1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 工具类 进行一些公有操作
 */
public class Utils {

    /**
     * 获取命令对象包
     */
    public final static String commandPath = "homework1.commands.";

    /**
     *     指定文件夹
     */
    public final static String path = "E:/qunar/src/test/";

    /**
     *      临时存储数据文件
     */
    public final static String temp = "E:/qunar/src/test/temp/temp.txt";

    public final static String tempI = "E:/qunar/src/test/temp/tempI.txt";

    /**
     * 此方法输入文件名或正则表达式，返回能够匹配的所有文件
     *
     * @param l  给定文件名或文件名正则表达式集合
     * @return   返回所有匹配正则表达式的文件名集合
     * @param flag 是否需要替换*
     */
    public static List<String> getFiles(List<String> l,boolean flag) {
        List<String> list = new ArrayList<>();
        for(String s:l) {
            if(s.contains("*")&&flag) {
                s = s.replaceAll("\\*","\\\\w*");
            }
            File directory = new File(Utils.path.substring(0,Utils.path.length()-1));
            File[] files = directory.listFiles();
            for(File file:files) {
                if(Pattern.matches(s,file.getName())&&!list.contains(s)) {
                    list.add(file.getName());
                }
            }
        }
        return list;
    }

    public static List<String> getAllFiles(String path,List<String> list) {
        File file = new File(path);
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File f:files) {
                getAllFiles(f.getPath(), list);
            }
        }else {
            list.add(file.getPath()
                    .replaceAll("E:\\\\qunar\\\\src\\\\test\\\\","")
                    .replaceAll("\\\\","/"));
        }
        return list;
    }

    /**
     * 对输入命令进行分割处理
     *
     * @param s   给定命令
     * @return   返回map集合 包括参数集合与文件名或文件正则表达式集合
     */

    public static Map<String,Object> getOptionFiles(String s){
        Map<String,Object> map = new HashMap<>();
        List<String> options = new ArrayList<>();
        List<String> files = new ArrayList<>();
        String b[] = s.trim().split(" ");
        /**
         * b集合第一个元素为命令名称
         */
        for(int i=1;i<b.length;i++) {
            if(Pattern.matches("-[a-zA-Z]{1}",b[i])) {
                options.add(b[i]);
            }else {
                files.add(b[i]);
            }
        }
        map.put("options",options);
        map.put("files",files);
        return map;
    }

    /**
     * 需要写入文件可能为目标文件，可能为临时存储文件
     *
     * @param str  查询的结果集
     * @param filename   需要写入的文件名
     * @param flag    是否追加以及文件是否需要删除与重新创建
     */

    public static void writeStr(String str,String filename,boolean flag) {
        try{
            File file = new File(filename) ;
            if(!flag) {
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
            }else {
                if(!file.exists()) {
                    file.createNewFile();
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(str);
            outputStreamWriter.close();
            fileOutputStream.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFile(String filename) {
        File file = new File(filename);
        if(file.exists()) {
            file.delete();
        }
    }
}
