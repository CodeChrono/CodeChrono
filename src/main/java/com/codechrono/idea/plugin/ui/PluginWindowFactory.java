package com.codechrono.idea.plugin.ui;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class PluginWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        MainForm mainForm = new MainForm(project,toolWindow);

        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.getInstance();
        // 获取用于toolWindow显示的内容
        Content content = contentFactory.createContent(mainForm.getContentJpanel(), "", false);
        //给toolWindow设置内容
        toolWindow.getContentManager().addContent(content);
        // 注册内容，当toolWindow关闭时，内容也会被释放
        Disposer.register(project, content);

    }
}
