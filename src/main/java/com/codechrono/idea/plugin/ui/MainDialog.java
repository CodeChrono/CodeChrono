package com.codechrono.idea.plugin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MainDialog extends DialogWrapper {

    public MainDialog(Project project, ToolWindow toolWindow) {
        super(true);
        setTitle("CodeChrono");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("CodeChrono"));

        return jPanel;
    }
}
