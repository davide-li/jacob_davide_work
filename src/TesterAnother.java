import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

class TesterAnother {
    static JFrame frame = new JFrame("Time - _荼锦_");   //创建一个JFrame类顶级容器
    //须设置为static才能被Timer的run方法所访问

    public static void main(String[] args) {
        //JFrame frame = new JFrame("Time - _荼锦_");   //创建一个JFrame类顶级容器
        frame.setVisible(true); //显示窗口
        frame.setSize(960, 750);    //设置窗口大小

        final JLabel label = new JLabel("", SwingConstants.CENTER); //创建原子组件Label，并将其数据放置在中间
        label.setForeground(Color.MAGENTA);    //设置字体颜色为品红色
        Font font = new Font("宋体", Font.PLAIN, 100);//创建1个字体实例font，字体为宋体，大小为100px
        label.setFont(font);//设置JLabel的字体为font

        Container contentPane = frame.getContentPane(); //获得顶级容器的内容面板contentPane，只有通过它才能加入其他组件
        contentPane.add(label); //通过contentPane将label加入到frame中

        //启动Timer定时器，实质是启动一个子线程
        new java.util.Timer().schedule(new TimerTask() {
            //实现TimerTask中的run()方法
            @Override
            public void run() {
                Date date = new Date(); //使用Date类获取当前日期和时间
                /*
                1、yyyy表示年，如2013；
                2、MM表示月，如12；
                3、dd表示天，如31；
                4、hh表示用12小时制，如7；
                5、HH表示用24小时制，如18；
                6、mm表示分，如59；
                7、ss表示秒，如59；
                8、SSS表示毫米，如333
                 */
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");   //获取当前日期
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");     //获取当前时间

                //将日期和时间打印到JLabel中使用'\n'或者'\r'不能进行换行操作，需要使用html语言进行换行
                //html中<p><\p>中间是文字内容，align表示设置居中显示，<br/>标签表示换行
                label.setText("<html><body><p align=\"center\">" + dateFormat1.format(date) + "<br/>" + dateFormat2.format(date) + "</p></body></html>");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //关闭此GUI即关闭该程序
            }
        }, new Date(),1000); //Timer定时器从new Date()时间（当前时间）开始，每隔1000ms，重复一次TimerTask内的操作（run方法）
        //这里的第一个参数TimerTask task使用的是匿名内部类的方式实现
    }
}