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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.codechrono.idea.plugin.utils.CodeChronoBundle.message;

public class MainForm {

    private JPanel contentJpanel;
    private JTextArea a1TextArea;
    private JButton refreshButton;
    private JRadioButton dayRadioButton;
    private JRadioButton weekRadioButton;
    private JLabel scopeLabel;


    public MainForm(Project project, ToolWindow toolWindow) {
        System.out.println("测试*******.MainForm");

        refreshButton.addActionListener(e -> {
            showContent();
        });
    }

    public JPanel getContentJpanel() {
        String selected = dayRadioButton.isSelected() ? StatisticType.DAY : (weekRadioButton.isSelected() ? StatisticType.WEEK : StatisticType.DAY);

        showContent();
        //a1TextArea.setText(dataCenter.getDailyStatisticTeContent(selected).toString());
        //dayRadioButton.setText(message("contentJpanel.dayRadioButton.text"));
        //weekRadioButton.setText(message("contentJpanel.weekRadioButton.text"));

        return contentJpanel;
    }

    private void showContent() {
        //类型：天/周
        String selected = dayRadioButton.isSelected() ? StatisticType.DAY : (weekRadioButton.isSelected() ? StatisticType.WEEK : StatisticType.DAY);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long endTime = calendar.getTime().getTime() + 86399000; //60s*60*24*1000-1000
        if (StatisticType.WEEK.equals(selected)) {
            calendar.add(Calendar.DATE, -7);
            endTime = calendar.getTime().getTime() + 604799000;//7*86400000-1000
        }
        Long beginTime = calendar.getTime().getTime();
        a1TextArea.setText(new DataCenter().getDailyStatisticTeContent(beginTime, endTime).toString());

        try {
            java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            StringBuffer sb = new StringBuffer();
            sb.append(formatter.format(new Date(beginTime)));
            sb.append(" ");
            sb.append(message("contentJpanel.scopeLabel.text"));
            sb.append(" ");
            sb.append(formatter.format(new Date(endTime)));
            scopeLabel.setText(sb.toString());
            //Thread.sleep(1000);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
