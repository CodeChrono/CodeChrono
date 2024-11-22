package com.codechrono.idea.plugin.ui;


import com.codechrono.idea.plugin.entity.StatisticType;

import com.codechrono.idea.plugin.service.KeyService;
import com.codechrono.idea.plugin.utils.DataCenter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

import static com.codechrono.idea.plugin.utils.CodeChronoBundle.message;

public class MainForm {
    private static Logger logger = LoggerFactory.getLogger("sz.Root");
    private JPanel contentJpanel;
    private JTextArea a1TextArea;
    private JButton refreshButton;
    private JRadioButton dayRadioButton;
    private JRadioButton weekRadioButton;
    private JLabel scopeLabel;
    private ButtonGroup type;


    public MainForm(Project project, ToolWindow toolWindow) {

        refreshButton.addActionListener(e -> {
            showContent();
        });
        dayRadioButton.addActionListener(e -> {
            showContent();
        });
        weekRadioButton.addActionListener(e -> {
            showContent();
        });
    }

    public JPanel getContentJpanel() {
        String selected = dayRadioButton.isSelected() ? StatisticType.DAY : (weekRadioButton.isSelected() ? StatisticType.WEEK : StatisticType.DAY);

        showContent();
         //dayRadioButton.setText(message("contentJpanel.dayRadioButton.text"));
        //weekRadioButton.setText(message("contentJpanel.weekRadioButton.text"));

        return contentJpanel;
    }

    private void showContent() {
        logger.info("logback showContent");
        if(!KeyService.validateKeyLocal()){
            a1TextArea.setText(message("contentJpanel.error.text"));

            Messages.showMessageDialog(message("contentJpanel.error.message"), "CodeChrono", Messages.getWarningIcon());
            SettingDialog dialog = new SettingDialog(null);
            dialog.show();
            if(dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE ){
                refreshButton.doClick();
            }
            return ;
        }
        //类型：天/周
        String selected = dayRadioButton.isSelected() ? StatisticType.DAY : (weekRadioButton.isSelected() ? StatisticType.WEEK : StatisticType.DAY);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        //86399000=60s*60*24*1000-1000
        Long endTime = calendar.getTime().getTime() + 86399000;
        if (StatisticType.WEEK.equals(selected)) {
            calendar.add(Calendar.DATE, -7);
            //604799000=7*86400000-1000
            endTime = calendar.getTime().getTime() + 604799000;
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
