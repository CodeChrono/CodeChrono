package com.codechrono.idea.plugin.ui;

import com.codechrono.idea.plugin.service.KeyService;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.util.ui.FormBuilder;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;


import static com.codechrono.idea.plugin.utils.CodeChronoBundle.message;

public class SettingDialog extends DialogWrapper {

    private JPanel contentPane;
    private JTextField apiKeyTextField;
    //private JBImageIcon wechatMiniPng;
    private JLabel wechatMiniPng;

    public SettingDialog(Project project) {
        // 设置为模态对话框
        super(project, true);
        // 设置对话框标题
        setTitle(message("settings.title"));
        setSize(550, 100);
        setOKButtonText(message("settings.ok"));
        setCancelButtonText(message("settings.cancel"));
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        // 设置对话框的内容
        contentPane = new JPanel(new BorderLayout());
        apiKeyTextField = new JTextField();
        wechatMiniPng = new JLabel();

        try {
            ImageIcon imageIcon = new ImageIcon(new URL("https://codechrono.cn/wechatMiniKeyPng.png"), "wechatMiniPng");
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
            wechatMiniPng.setIcon(imageIcon);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        //wechatMiniPng.setIcon(new ImageIcon("images/wechatMiniPng.png"));
        //获取当前项目的绝对路径
        System.out.println(System.getProperty("user.dir"));


        return (JComponent) contentPane.add(
                FormBuilder.createFormBuilder()
                        .addLabeledComponent(new JLabel("API key:"), apiKeyTextField)
                        .addComponent(new JLabel(message("tips.getKey.text")))
                        .addComponent(wechatMiniPng)
                        .getPanel()
        );
    }

    @Override
    protected void doOKAction() {
        String apiKey = apiKeyTextField.getText().trim();
        if (KeyService.validateKeyServer(apiKey)) {

            Messages.showMessageDialog("欢迎使用", "CodeChrono", Messages.getInformationIcon());
        } else {
            Messages.showInfoMessage(this.getWindow(), "联网校验失败", "CodeChrono");
            return;
        }

        super.doOKAction();
    }

   /* @Override
    protected void createDefaultActions() {
        super.createDefaultActions();
        // 如果你想要自定义"取消"按钮的行为，可以在这里修改或替换getAction(IDialogWrapper.CANCEL_ACTION)
    }*/


    // 如果你需要对话框在关闭前进行验证，可以覆盖此方法
    @Override
    protected ValidationInfo doValidate() {
        // 验证API Key的有效性...
        // 如果验证失败，返回一个ValidationInfo对象，描述错误；否则返回null
        return null;
    }


    // 你可以根据需要添加更多的方法和逻辑
}

