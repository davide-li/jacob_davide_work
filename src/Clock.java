//Clock.java
import javax.swing.*;

public class Clock{
    public static void main(String[] args) {
        JFrame frame=new JFrame("Clock");    //创建图文框
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ClockPanel()); //添加面板
        frame.setVisible(true);
        frame.setSize(640,480);
    }
}
