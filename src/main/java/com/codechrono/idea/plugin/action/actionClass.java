package com.codechrono.idea.plugin.action;

import com.codechrono.idea.plugin.ui.SettingDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

public class actionClass extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {

        Project project = event.getData(PlatformDataKeys.PROJECT);

        new SettingDialog(project).show();

    }
}
