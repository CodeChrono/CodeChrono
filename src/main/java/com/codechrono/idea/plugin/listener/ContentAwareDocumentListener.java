package com.codechrono.idea.plugin.listener;

import com.codechrono.idea.plugin.entity.EditRecord;
import com.codechrono.idea.plugin.service.EditRecordInterface;
import com.codechrono.idea.plugin.service.impl.EditRecordService;
import com.codechrono.idea.plugin.entity.EditType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;

import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;


public class ContentAwareDocumentListener implements DocumentListener {
    private static final Logger LOGGER = Logger.getInstance(DocumentListener.class);

    @Override
    public void documentChanged(DocumentEvent event) {
        Document document = event.getDocument();
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);

        if (virtualFile != null) {
            String fileName = virtualFile.getName();
            String curProjectName = "";
            Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
            for (Project project : openProjects) {
                ProjectFileIndex fileIndex = ProjectFileIndex.getInstance(project);
                if (fileIndex.isInContent(virtualFile)
                        || fileIndex.isInLibrary(virtualFile)) {
                    // 找到了包含该VirtualFile的项目
                    curProjectName = project.getName();
                    break;
                }
            }
            int oldLength = event.getOldLength();
            //DocumentEventImpl documentImpl = (DocumentEventImpl) event;

            // 新插入的文本
            String insertedText = event.getNewFragment().toString();
            // 被替换或删除的文本
            String removedText = event.getOldFragment().toString();
            // document.getCharsSequence().subSequence(offset, offset + oldLength).toString(); // 被替换或删除的文本

            //System.out.println("insert:"+insertedText +"delete:"+ removedText);
            if (oldLength > 0 && !insertedText.isEmpty()) {
                System.out.println("Insert && delete at the same time ");
            }

            //修改内容存表
            EditRecordInterface editRecordService = EditRecordService.getInstance();
            EditRecord data = new EditRecord();

            if (curProjectName.isEmpty()) {
                curProjectName = fileName;//待完善
            }
            data.setProjectName(curProjectName);
            data.setCreateTime(System.currentTimeMillis());
            //去除空格' '
            long insrtEmptCount = insertedText.chars().filter(c -> c == ' ').count();
            long deleteEmptyCount = removedText.chars().filter(c -> c == ' ').count();
            if (oldLength > 0) {
                data.setEditType(EditType.DELETE);
                data.setEditNum(removedText.replace("\n", "").length() - deleteEmptyCount);
                data.setContent(oldLength <= 500 ? removedText : removedText.substring(0, 497) + "...");

                if (data.getEditNum().intValue() > 0) {
                    editRecordService.insert(data);
                }
            }
            if (!insertedText.isEmpty()) {
                data.setEditType(EditType.INSERT);
                data.setEditNum(insertedText.replace("\n", "").length() - insrtEmptCount);
                data.setContent(data.getEditNum().intValue() <= 500 ? insertedText : "...");

                if (data.getEditNum().intValue() > 0) {
                    editRecordService.insert(data);
                }
            }


        } else {
            LOGGER.warn("Document change event without associated VirtualFile.");
        }
    }


    public String getProjectName() {
        return "Unidentified";
    }

}
