package com.codechrono.idea.plugin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.awt.*;

import static com.codechrono.idea.plugin.utils.CodeChronoBundle.message;

public class SettingDialog extends DialogWrapper {

    private JPanel contentPane;
    private JTextField apiKeyTextField;

    public SettingDialog(Project project) {
        super(project, true); // 设置为模态对话框
        setTitle(message("settings.title")); // 设置对话框标题
        setSize(550, 100);
        init();
    }
    @Override
    protected @Nullable JComponent createCenterPanel() {
        // 设置对话框的内容
        contentPane = new JPanel(new BorderLayout());
        apiKeyTextField = new JTextField();

        return (JComponent) contentPane.add(
                FormBuilder.createFormBuilder()
                        .addLabeledComponent(new JLabel("API key:"), apiKeyTextField)
                        .addComponent(new JLabel("扫码小程序获取；或发送邮件yuluo.guo@qq.com 关键字：获取 KEY"))
                        .getPanel()
        );
    }

    @Override
    protected void doOKAction() {
        // 在这里添加保存API Key的逻辑
        String apiKey = apiKeyTextField.getText();
        // 验证或保存apiKey...
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

