package com.codechrono.idea.plugin.listener;

import com.codechrono.idea.plugin.entity.EditRecord;
import com.codechrono.idea.plugin.service.EditRecordService;
import com.codechrono.idea.plugin.service.impl.EditRecordServiceImpl;
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

import java.util.Date;


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
                //ProjectFileIndex fileIndex = ProjectFileIndex.SERVICE.getInstance(project);
                ProjectFileIndex fileIndex = ProjectFileIndex.getInstance(project);
                if (fileIndex.isInContent(virtualFile)
                        || fileIndex.isInLibrary(virtualFile)) {
                    // 找到了包含该VirtualFile的项目
                    curProjectName = project.getName();
                    break;
                }
            }
            int offset = event.getOffset();
            int oldLength = event.getOldLength();
            String insertedText = event.getNewFragment().toString(); // 新插入的文本
            String removedText = document.getCharsSequence().subSequence(offset, offset + oldLength).toString(); // 被替换或删除的文本

            if (oldLength > 0) { // 删除或替换
                System.out.println("In file [" + fileName + "], removed [" + removedText + "] at position " + offset);
                LOGGER.info("In file [" + fileName + "], removed [" + removedText + "] at position " + offset);
            }
            if (!insertedText.isEmpty()) { // 插入
                System.out.println("In file [" + fileName + "], added [" + insertedText + "] at position " + offset);
                LOGGER.info("In file [" + fileName + "], added [" + insertedText + "] at position " + offset);
            }
            if (oldLength > 0 && !insertedText.isEmpty()) {
                System.out.println("同时插入和删除");
            }

            //修改内容存表
            EditRecordService notebookService = EditRecordServiceImpl.getInstance();
            EditRecord data = new EditRecord();

            if (curProjectName.isEmpty()) {
                //curProjectName = getProjectName();//待完善
            }
            data.setProjectName(curProjectName);
            data.setCreateTime(new Date());
            //去除空格' '
            long insrtEmptCount = insertedText.chars().filter(c -> c == ' ').count();
            long deleteEmptyCount = removedText.chars().filter(c -> c == ' ').count();
            if (oldLength > 0) {
                data.setEditType(EditType.DELETE);
                data.setEditNum(removedText.replace("\n", "").length() - deleteEmptyCount);
                data.setContent(removedText);
                if (data.getEditNum().intValue() > 0) {
                    notebookService.insert(data);
                }
            }
            if (!insertedText.isEmpty()) {
                data.setEditType(EditType.INSERT);
                data.setEditNum(insertedText.replace("\n", "").length() - insrtEmptCount);
                data.setContent(insertedText);
                if (data.getEditNum().intValue() > 0) {
                    notebookService.insert(data);
                }
            }


        } else {
            LOGGER.warn("Document change event without associated VirtualFile.");
        }
    }
}
