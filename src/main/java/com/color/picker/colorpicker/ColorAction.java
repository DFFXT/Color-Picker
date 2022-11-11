package com.color.picker.colorpicker;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.ui.JBColor;
import org.jdesktop.swingx.JXPanel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * 颜色快捷显示
 * 1、选中颜色字符串
 * 2、快捷方式ctrl + M
 * 3、选中背景会变色
 * 4、不选中颜色字符串，ctrl + M
 * 5、选中背景恢复
 */
public class ColorAction extends AnAction {

    // 当前显示颜色
    private int backgroundColor;


    public ColorAction() {


    }

    public static Color originColor;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 获取光标选中的内容
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor != null) {
            String selectedContent = editor.getSelectionModel().getSelectedText();
            if (selectedContent == null) return;
            Integer color = ColorsUtil.getColor(null, selectedContent);
            if (color == null){
                if (originColor != null) {
                    editor.getSelectionModel().getTextAttributes().setBackgroundColor(originColor);
                }
                return;
            }
            if (originColor == null) {
                originColor = editor.getSelectionModel().getTextAttributes().getBackgroundColor();
            }
            backgroundColor = color;
            Color newColor = new Color((backgroundColor >> 16) & 0xFF, (backgroundColor >> 8) & 0xFF, backgroundColor & 0xFF, (backgroundColor >> 24) & 0xFF);
            // JBColor jbColor = new JBColor(newColor, newColor);
            editor.getSelectionModel().getTextAttributes().setBackgroundColor(newColor);
            int start = editor.getSelectionModel().getSelectionStart();
            editor.getSelectionModel().setSelection(start-1, editor.getSelectionModel().getSelectionEnd());
            editor.getSelectionModel().setSelection(start, editor.getSelectionModel().getSelectionEnd());
            /*JXPanel jxPanel = new JXPanel() {
                @Override
                public void paint(Graphics g) {
                    // super.paint(g);
                    g.setColor(new Color((backgroundColor >> 16) & 0xFF, (backgroundColor >> 8) & 0xFF, backgroundColor & 0xFF, (backgroundColor >> 24) & 0xFF));
                    g.fillRect(100, 100, 200, 200);
                }
            };
            jxPanel.setSize(100, 100);
            editor.getComponent().add(jxPanel, 0);
            editor.getComponent().validate();*/
            /*editor.addEditorMouseListener(new EditorMouseListener() {
                @Override
                public void mouseClicked(@NotNull EditorMouseEvent event) {
                    backgroundColor = 0;
                    editor.getSelectionModel().getTextAttributes().setEffectColor(originColor);
                    *//*jxPanel.invalidate();
                    editor.getComponent().remove(jxPanel);
                    editor.getComponent().validate();*//*
                    editor.removeEditorMouseListener(this);
                }
            });*/
        }

    }

}
