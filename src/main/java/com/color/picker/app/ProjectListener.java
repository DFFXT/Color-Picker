package com.color.picker.app;

import com.color.picker.colorpicker.ColorsUtil;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.editor.event.SelectionEvent;
import com.intellij.openapi.editor.event.SelectionListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

/**
 * idea 启动关闭监听
 */
public class ProjectListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        ProjectManagerListener.super.projectOpened(project);
        final Disposable[] disposable = {new MDisposable()};
        EditorFactoryListener listener = new EditorFactoryListener() {
            @Override
            public void editorCreated(@NotNull EditorFactoryEvent event) {
                System.out.println("editor create");
                EditorFactoryListener listener = this;
                // 监听文本选中
                event.getEditor().getSelectionModel().addSelectionListener(new SelectionListener() {
                    private Color originColor = null;
                    private Integer currentColor = null;
                    @Override
                    public void selectionChanged(@NotNull SelectionEvent e) {
                        SelectionListener.super.selectionChanged(e);
                        Editor editor = e.getEditor();
                        String selectedContent = editor.getSelectionModel().getSelectedText();
                        if (selectedContent == null) return;
                        // 解析选中文本是否是颜色
                        // 为了方便#无法快捷选中，这里额外获取选中的前一个字符
                        int selectionStart = editor.getSelectionModel().getSelectionStart();
                        String beforeText = null;
                        if (selectionStart != 0) {
                            beforeText = editor.getDocument().getText(new TextRange(selectionStart - 1, selectionStart));
                        }
                        Integer color = ColorsUtil.getColor(beforeText, selectedContent);
                        if (color == null){
                            if (originColor != null) {
                                // 颜色恢复
                                editor.getSelectionModel().getTextAttributes().setBackgroundColor(originColor);
                                originColor = null;
                                currentColor = null;
                            }
                            return;
                        }
                        if (Objects.equals(currentColor, color)) {
                            return;
                        }
                        currentColor = color;
                        if (originColor == null) {
                            originColor = editor.getSelectionModel().getTextAttributes().getBackgroundColor();
                        }
                        Color newColor = new Color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);
                        // JBColor jbColor = new JBColor(newColor, newColor);
                        editor.getSelectionModel().getTextAttributes().setBackgroundColor(newColor);

                        int start = editor.getSelectionModel().getSelectionStart();
                        // 修改选中范围，触发刷新UI，这里会导致selectionChanged回调
                        Disposer.dispose(disposable[0]);
                        editor.getSelectionModel().setSelection(start-1, editor.getSelectionModel().getSelectionEnd());
                        editor.getSelectionModel().setSelection(start, editor.getSelectionModel().getSelectionEnd());
                        disposable[0] = new MDisposable();
                        EditorFactory.getInstance().addEditorFactoryListener(listener, disposable[0]);
                        System.out.println(selectedContent);
                    }
                });
            }

            @Override
            public void editorReleased(@NotNull EditorFactoryEvent event) {
                System.out.println("editor released");
            }
        };
        // 获取编辑器工厂并监听编辑窗口的创建
        EditorFactory.getInstance().addEditorFactoryListener(listener, disposable[0]);
    }

    @Override
    public void projectClosed(@NotNull Project project) {
        ProjectManagerListener.super.projectClosed(project);
    }

    @Override
    public void projectClosing(@NotNull Project project) {
        ProjectManagerListener.super.projectClosing(project);
    }

    @Override
    public void projectClosingBeforeSave(@NotNull Project project) {
        ProjectManagerListener.super.projectClosingBeforeSave(project);
    }
    static class MDisposable implements Disposable {
        @Override
        public void dispose() {

        }
    }
}
