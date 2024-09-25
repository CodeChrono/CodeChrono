package com.codechrono.idea.plugin.listener;

import com.codechrono.idea.plugin.service.KeyService;
import com.intellij.ide.AppLifecycleListener;
import com.intellij.ide.IdeEventQueue;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.EditorFactory;

import com.intellij.openapi.project.impl.ProjectLifecycleListener;
import com.intellij.openapi.util.Disposer;

import java.util.List;

public class MyAppLifecycleListener implements AppLifecycleListener , ProjectLifecycleListener {
    @Override
    public void appFrameCreated(List<String> commandLineArgs) {
        System.out.println("appFrameCreated");
        if(!KeyService.validateKeyLocal()){
            System.out.println("appFrameCreated校验apiKey失败，无法使用");
           return ;
        }

        ApplicationManager.getApplication().invokeLater(() -> {
            // 注册一个Disposable对象，在插件卸载时调用dispose方法，内存优化
            Disposable disposable = Disposer.newDisposable("CodeChronoListener");
            // 在这里注册EditorFactoryListener
            EditorFactory.getInstance().addEditorFactoryListener(new MyEditorFactoryListener(), disposable);
            //焦点
            IdeEventQueue.getInstance().addDispatcher(new FocusDispatcher(), disposable);
        });
    }
    /*
    @Override
    public void appStarted() {
        System.out.println("此方法是私有api，未来可能会修改、删除，不能再intellij平台外使用");
    }
     */



}
