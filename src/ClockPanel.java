package src;//src.ClockPanel.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class ClockPanel extends JPanel{
    private GregorianCalendar calendar;
    private JButton btn;
    private JButton btn2;
    private int currentState=8;
    private String zone;
    private int hourTemp;
    final int X=320, Y=240, R=120;   // 圆心坐标，半径
    private int xPos,yPos;
    private int hour,minute,second;
    private int xHour,yHour,xMinute,yMinute,xSecond,ySecond;//表针位置（大端）
    private int xHour1,yHour1,xMinute1,yMinute1,xSecond1,ySecond1;//表针位置（小端）
    private double a_sec,a_min ,a_hour;//角度

    ClockPanel() {   // 创建定时器对象
        Timer t = new Timer();
        Task task = new Task();
        t.schedule(task, 0, 1000);
        setLayout(new BorderLayout(10,20));
        btn=new JButton("时区  上");
        btn2=new JButton("时区 下");
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn2.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.setBackground(Color.green);
        btn2.setBackground(Color.green);
        btn.addActionListener(new ButtonListener());
        btn2.addActionListener(new ButtonListener());
        add(btn,BorderLayout.WEST);
        add(btn2,BorderLayout.EAST);
    }
    //相关事件处理
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            if (event.getSource()==btn)
                currentState++;
            if (event.getSource()==btn2)
                currentState--;
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        double alfa;    //所画点对应的角度
        Graphics2D g2d=(Graphics2D)g;
        BasicStroke bstroke=new BasicStroke(1.0f);
        BasicStroke bstroke2=new BasicStroke(2.0f);
        BasicStroke bstroke3=new BasicStroke(3.0f);

        g2d.setStroke(bstroke2);
        for(int i=0;i<=360;i+=6)  {
            alfa=Math.toRadians(i);  //角度用弧度表示
            xPos=X+(int)(R*Math.cos(alfa));   // x坐标
            yPos=Y-(int)(R*Math.sin(alfa));   // y坐标
            int xBegin=320+(int)(144*Math.sin(alfa));
            int yBegin=240-(int)(144*Math.cos(alfa));
            int xEnd=320+(int)(159*Math.sin(alfa));
            int yEnd=240-(int)(159*Math.cos(alfa));

            g2d.setColor(Color.BLACK);
            g2d.drawLine(xBegin,yBegin,xEnd,yEnd);

            g2d.setColor(Color.RED);
            switch(i){  // 写时钟数字刻度
                case 0: g2d.drawString("3", xPos,yPos);
                    break;
                case 90: g2d.drawString("12", xPos,yPos);
                    break;
                case 180: g2d.drawString("9", xPos,yPos);
                    break;
                case 270: g2d.drawString("6",xPos,yPos);
                    break;
            }

            if(i%30==0){
                g2d.drawLine(xBegin,yBegin,xEnd,yEnd);
            }

        }


        g2d.setColor(Color.BLACK);
        g2d.setStroke(bstroke3);
        g2d.drawLine(X, Y, xHour,yHour);    // 画时针
        g2d.drawLine(X, Y, xHour1,yHour1);
        g2d.setColor(Color.BLUE);
        g2d.setStroke(bstroke2);
        g2d.drawLine(X, Y, xMinute,yMinute);    // 画分针
        g2d.drawLine(X, Y, xMinute1,yMinute1);
        g2d.setColor(Color.RED);
        g2d.setStroke(bstroke);
        g2d.drawLine(X, Y, xSecond,ySecond);    // 画秒针
        g2d.drawLine(X, Y, xSecond1,ySecond1);
        //表盘中心点1
        g2d.drawOval(317,237,6,6);
        g2d.fillOval(317,237,6,6);
        //表盘中心点2
        g2d.setColor(Color.BLACK);
        g2d.drawOval(319,238,4,4);
        g2d.fillOval(319,238,4,4);
        //表盘中心圆环
        g2d.setColor(Color.ORANGE);
        g2d.drawOval(300,220,40,40);
        g2d.setColor(Color.black);
        g2d.drawString("15010140079",290,376);

        GregorianCalendar gre=new GregorianCalendar();
        SimpleDateFormat dateforamt1=new SimpleDateFormat("yyyy年MM月dd日E");
        //SimpleDateFormat dateforamt2=new SimpleDateFormat("H时m分s秒");
        g2d.setColor(Color.black);
        g2d.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        g2d.drawString(dateforamt1.format(gre.getTime()),250,50);

        g2d.drawString(hour+"时"+minute+"分"+second+"秒",260,430);
        //时区判断
        if(currentState>12){
            currentState=-11;
        }
        else if(currentState<-11){
            currentState=12;
        }
        if(currentState<=12&&currentState>=1)
            zone="东"+currentState+"区";
        else
            zone="西"+(1-currentState)+"区";
        g2d.drawString(zone,170,50);

    }

    class Task extends TimerTask {
        public void run() {
            calendar = new GregorianCalendar();
            hourTemp=currentState>0?(currentState-8):(currentState-1);
            hour = calendar.get(Calendar.HOUR)+hourTemp;
            minute = calendar.get(Calendar.MINUTE);
            second = calendar.get(Calendar.SECOND);

            a_sec = second * 2 * Math.PI / 60;
            a_min = minute * 2 * Math.PI / 60 + a_sec / 60;
            a_hour = hour * 2 * Math.PI / 12 + a_min / 12;
            // 计算时、分、秒针的末端位置
            xSecond=320+(int)(110*Math.sin(a_sec));
            ySecond=240-(int)(110*Math.cos(a_sec));
            xMinute=320+(int)(90 *Math.sin(a_min));
            yMinute=240-(int)(90 *Math.cos(a_min));
            xHour=  320+(int)(70 *Math.sin(a_hour));
            yHour=  240-(int)(70 *Math.cos(a_hour));
            xSecond1=320-(int)(22*Math.sin(a_sec));
            ySecond1=240+(int)(22*Math.cos(a_sec));
            xMinute1=320-(int)(15*Math.sin(a_min));
            yMinute1=240+(int)(15*Math.cos(a_min));
            xHour1  =320-(int)(5 *Math.sin(a_hour));
            yHour1  =240+(int)(5 *Math.cos(a_hour));

            repaint();
        }
    }
}