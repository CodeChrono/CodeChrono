package com.codechrono.idea.plugin.ui;


import com.codechrono.idea.plugin.entity.StatisticType;
import com.codechrono.idea.plugin.listener.FocusDispatcher;
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
import java.util.concurrent.CompletableFuture;

import static com.codechrono.idea.plugin.utils.CodeChronoBundle.message;

public class MainForm {
    private static Logger logger = LoggerFactory.getLogger("ui.MainForm");
    private JPanel contentJpanel;
    private JTextArea a1TextArea;
    private JButton refreshButton;
    private JRadioButton dayRadioButton;
    private JRadioButton weekRadioButton;
    private JLabel scopeLabel;
    private JButton settingButton;
    private JButton helpButton;
    private ButtonGroup type;


    public MainForm(Project project, ToolWindow toolWindow) {
        addListener();
    }

    public JPanel getContentJpanel() {
        load();
        return contentJpanel;
    }

    /**
     * 加载界面
     */
    private void load() {
        if (validateKey()) {
            showContent();
        } else {
            a1TextArea.setText(message("contentJpanel.error.text"));
            Messages.showMessageDialog(message("contentJpanel.error.message"), "CodeChrono", Messages.getWarningIcon());
        }
    }

    /**
     * 添加监听器事件
     */
    private void addListener() {
        refreshButton.addActionListener(e -> {
            load();
        });
        dayRadioButton.addActionListener(e -> {
            load();
        });
        weekRadioButton.addActionListener(e -> {
            load();
        });
        settingButton.addActionListener(e -> {
            SettingDialog dialog = new SettingDialog(null);
            dialog.show();
            if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
                refreshButton.doClick();
            }
        });
        helpButton.addActionListener(e -> {
            HelpDialog dialog = new HelpDialog(null);
            dialog.show();
            if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
                refreshButton.doClick();
            }
        });

//        jb_note.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                load();
//            }
//        });
    }


    /**
     * 显示统计内容的方法
     */
    private void showContent() {
        logger.info("logback showContent");
        //1、结束事件并创建新事件
        FocusDispatcher focusDispatcher = new FocusDispatcher();
        focusDispatcher.onIdeaLostFocus(true);

        // 2、endTime、beginTime计算
        // 判断是日统计还是周统计
        String selected = dayRadioButton.isSelected() ? StatisticType.DAY : (weekRadioButton.isSelected() ? StatisticType.WEEK : StatisticType.DAY);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long endTime = calendar.getTime().getTime() + 86399000;
        //86399000=60s*60*24*1000-1000
        if (StatisticType.WEEK.equals(selected)) {
            calendar.add(Calendar.DATE, -7);
            //604799000=7*86400000-1000
            endTime = calendar.getTime().getTime() + 604799000;
        }
        Long beginTime = calendar.getTime().getTime();

        // 3、设置时间范围显示标签文本
        setScopeLabel(beginTime, endTime);

        //4 异步加载数据
        Long finalEndTime = endTime;
        CompletableFuture<String> futureData = CompletableFuture.supplyAsync(() -> {
            return DataCenter.getDailyStatisticTeContent(beginTime, finalEndTime).toString();
        });
        // 等待数据加载完成并更新UI组件
        try {
            String data = futureData.get();
            a1TextArea.setText(data);

        } catch (Exception ex) {
            // 处理异常，例如显示错误信息
            a1TextArea.setText("Failed to load data: " + ex.getMessage());
            logger.error("Failed to load data", ex);
        }
            /*
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = now.truncatedTo(ChronoUnit.DAYS);
            // 如果选择的是日统计，则end为当天的23:59:59；周统计，则end为一周前的最后一天的23:59:59
            LocalDateTime end = selected.equals(StatisticType.DAY) ? start.plusDays(1).minusSeconds(1)
                    : start.minusDays(7).plusDays(1).minusSeconds(1);
            */
    }

    /**
     * 设置时间范围显示
     *
     * @param beginTime
     * @param endTime
     */
    private void setScopeLabel(Long beginTime, Long endTime) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //设置时间范围
        scopeLabel.setText(formatter.format(new Date(beginTime)) + " " + message("contentJpanel.scopeLabel.text") + " " + formatter.format(endTime));
    }

    /**
     * 校验key是否有效
     *
     * @return true: 有效; false: 无效
     */
    private boolean validateKey() {
        if (KeyService.validateKeyLocal()) {
            return true;
        }

        // 无效，弹出对话框提示用户输入key
        SettingDialog dialog = new SettingDialog(null);
        dialog.show();
        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            return true;
        }
        return false;
    }

}
