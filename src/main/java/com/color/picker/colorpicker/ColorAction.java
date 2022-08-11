package com.color.picker.colorpicker;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import org.jdesktop.swingx.JXPanel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@NotNull
public class ColorAction extends AnAction {

    // 当前显示颜色
    private int backgroundColor;


    public ColorAction() {


    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 获取光标选中的内容
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor != null) {
            String selectedContent = editor.getSelectionModel().getSelectedText();
            if (selectedContent == null) return;
            Integer color = ColorsUtil.getColor(selectedContent);
            if (color == null) return;

            backgroundColor = color;
            JXPanel jxPanel = new JXPanel() {
                @Override
                public void paint(Graphics g) {
                    // super.paint(g);
                    g.setColor(new Color((backgroundColor >> 16) & 0xFF, (backgroundColor >> 8) & 0xFF, backgroundColor & 0xFF, (backgroundColor >> 24) & 0xFF));
                    g.fillRect(100, 100, 200, 200);
                }
            };
            jxPanel.setSize(100, 100);
            editor.getComponent().add(jxPanel, 0);
            editor.getComponent().validate();
            editor.addEditorMouseListener(new EditorMouseListener() {
                @Override
                public void mouseClicked(@NotNull EditorMouseEvent event) {
                    backgroundColor = 0;
                    jxPanel.invalidate();
                    editor.getComponent().remove(jxPanel);
                    editor.getComponent().validate();
                    editor.removeEditorMouseListener(this);
                }
            });
        }

    }

}
