package homework3.Three2;


public class Main {
    public static void main(String[] args) {
        // 方法一采取排序
        Method1 method1 = new Method1();
        // 方法二通过找规律 有局限性
        Method2 method2 = new Method2();
        long a = System.currentTimeMillis();
        method1.execute();
        long b = System.currentTimeMillis();
        method2.execute();
        long c = System.currentTimeMillis();
        System.out.println(b-a);
        System.out.println(c-b);
    }
}
