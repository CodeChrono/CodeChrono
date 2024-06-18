package com.codechrono.idea.plugin.ui;


import com.codechrono.idea.plugin.entity.StatisticType;

import com.codechrono.idea.plugin.utils.DataCenter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.codechrono.idea.plugin.utils.CodeChronoBundle.message;

public class MainForm {
 
    private JPanel contentJpanel;
    private JTextArea a1TextArea;
    private JButton refreshButton;
    private JRadioButton dayRadioButton;
    private JRadioButton weekRadioButton;

    private DataCenter dataCenter=new DataCenter();

    public MainForm(Project project, ToolWindow toolWindow) {
        System.out.println("测试*******.MainForm" );

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = dayRadioButton.isSelected() ? StatisticType.DAY : (weekRadioButton.isSelected() ? StatisticType.WEEK : StatisticType.DAY);

                a1TextArea.setText(dataCenter.getDailyStatisticTeContent(selected).toString());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                MainDialog mainDialog = new MainDialog(project, toolWindow);
                mainDialog.show();

            }
        });
    }
    public JPanel getContentJpanel() {
        String selected = dayRadioButton.isSelected() ? StatisticType.DAY : (weekRadioButton.isSelected() ? StatisticType.WEEK : StatisticType.DAY);

        a1TextArea.setText(dataCenter.getDailyStatisticTeContent(selected).toString());
        dayRadioButton.setText(message("contentJpanel.dayRadioButton.text"));
        weekRadioButton.setText(message("contentJpanel.weekRadioButton.text"));
        try{

            this.a1TextArea.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    System.out.println("测试*******.MainForm keyTyped" );
                    // 当有键被按下时，模拟键盘输入
                    //char key = e.getKeyChar();
                    //robot.keyPress(key);
                    //robot.keyRelease(key);
                    //System.out.println("9Key Pressed: " + KeyEvent.getKeyText(key));
                    //System.out.println("NAKey Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    // 当有键被按下时，模拟键盘输入
                    int key = e.getKeyCode();
                    ;

                    //System.out.println("Key Pressed: " + KeyEvent.getKeyText(key));
                    //robot.keyPress(key);
                    //System.out.println("测试*******.MainForm keyPressed" );
                    //System.out.println("郭Key Pressed: " + KeyEvent.getKeyText(key));
                    //System.out.println("N Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    // 当有键被释放时，模拟键盘输入
                    //int key = e.getKeyCode();
                    //robot.keyRelease(key);
                    //System.out.println("测试*******.MainForm keyReleased" );
                   // System.out.println("8Key Pressed: " + KeyEvent.getKeyText(key));
                    //System.out.println("N Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
                }
            });
        }catch (Exception e){
            System.out.println("测试*******.MainForm Exception" );
        }
        return contentJpanel;
    }
}
