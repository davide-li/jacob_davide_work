package src;

import src.CustomTimer;

public class Tester {
    public static void main(String[] args) {
        System.out.println("main thread starts!");  //提示主线程开始运行
        CustomTimer timer = new CustomTimer();  //实例化Timer类
        new Thread(timer).start();  //通过Thread类生成子线程Timer
        System.out.println("main thread ends!");    //提示主线程结束
    }
}