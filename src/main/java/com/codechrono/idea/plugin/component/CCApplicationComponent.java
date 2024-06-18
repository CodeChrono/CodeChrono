package com.codechrono.idea.plugin.component;


import com.codechrono.idea.plugin.listener.MyEditorFactoryListener;
import com.codechrono.idea.plugin.listener.FocusDispatcher;
import com.intellij.ide.IdeEventQueue;
import com.intellij.openapi.Disposable;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;


public class CCApplicationComponent implements ApplicationComponent {

    @Override
    public void initComponent() {

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

    @Nullable
    public static VirtualFile getFile(Document document) {
        if (document == null) return null;
        FileDocumentManager instance = FileDocumentManager.getInstance();
        if (instance == null) return null;
        VirtualFile file = instance.getFile(document);
        return file;
    }
    public static String getProjectName() {

        return project.getName();
    }
    private static Project project=null;
    public CCApplicationComponent(Project project)
    {
        this.project = project;

    }



}
