package homework3;

import java.io.*;
import java.util.*;

public class Main {
    public static class Node{
        String key;
        int val;
        public Node(String key,int val) {
            this.key = key;
            this.val = val;
        }
    }
    static Map<String,Integer> map = new HashMap<>();
    public static void main(String args[]) throws IOException{
        File file = new File("E:/weibo");
        Main main = new Main();
        main.reverse(file);
        ArrayList<Node> list = new ArrayList<>();
        for(Map.Entry<String,Integer> entry:map.entrySet()) {
            Node node = new Node(entry.getKey(),entry.getValue());
            list.add(node);
        }
        Collections.sort(list, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o2.val-o1.val;
            }
        });
        for(int i=0;i<10;i++) {
            Node node = list.get(i);
            System.out.println(node.key+" "+node.val);
        }
    }
    public void reverse(File file) throws IOException {
        if(file.isDirectory()) {
            File files[] = file.listFiles();
            for(File file1 :files) {
                if(file1.isDirectory()) {
                    reverse(file1);
                }else {
                    String s = file1.getName();
                    if(s.length()>6&&s.substring(s.length()-4,s.length()).equals("java")) {
                        getImport(file1);
                    }
                }
            }
        }
    }
    public void getImport(File file) throws IOException{
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s = "";
        while((s = bufferedReader.readLine())!=null) {
            String str = s.trim();
            if(str.equals("")||str==null) {
                continue;
            }
            if(str.length()>7&&str.substring(0,7).equals("package")) {
                continue;
            }
            if(str.length()>6&&str.substring(0,6).equals("import")) {
                String importStr = str.substring(6,str.length()-1).trim();
                if(map.containsKey(importStr)) {
                    map.put(importStr,map.get(importStr)+1);
                }else {
                    map.put(importStr,1);
                }
                continue;
            }
        }
        fileReader.close();
        bufferedReader.close();

    }
}

