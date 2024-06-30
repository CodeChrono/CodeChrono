package com.codechrono.idea.plugin.listener;

import com.intellij.ide.AppLifecycleListener;
import com.intellij.ide.IdeEventQueue;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.EditorFactory;

import com.intellij.openapi.project.impl.ProjectLifecycleListener;
import com.intellij.openapi.util.Disposer;

import java.util.List;

public class MyAppLifecycleListener implements AppLifecycleListener , ProjectLifecycleListener {
    /* appStarted*/

    @Override
    public void appFrameCreated(List<String> commandLineArgs) {
        System.out.println("appFrameCreated");

        ApplicationManager.getApplication().invokeLater(new Runnable() {
            public void run() {
                // 注册一个Disposable对象，在插件卸载时调用dispose方法，内存优化
                Disposable disposable = Disposer.newDisposable("CodeChronoListener");
                // 在这里注册EditorFactoryListener
                EditorFactory.getInstance().addEditorFactoryListener(new MyEditorFactoryListener(), disposable);
                //焦点
                IdeEventQueue.getInstance().addDispatcher(new FocusDispatcher(), disposable);
            }
        });
    }

}
