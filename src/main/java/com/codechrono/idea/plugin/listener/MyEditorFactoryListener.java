package com.codechrono.idea.plugin.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;

public class MyEditorFactoryListener implements EditorFactoryListener {
    private static Editor editor;

    @Override
    public void editorCreated(EditorFactoryEvent event) {
        editor = event.getEditor();
        if (editor != null) {
            editor.getDocument().addDocumentListener(new ContentAwareDocumentListener());
        }
    }

}