package com.color.picker.editor;

import com.intellij.icons.AllIcons;

import javax.swing.*;
import java.awt.*;

public class ColorIcon implements Icon {
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        System.out.println("x="+x);
    }

    @Override
    public int getIconWidth() {
        return 40;
    }

    @Override
    public int getIconHeight() {
        return 40;
    }

    public final static Icon icon = AllIcons.Actions.BuildLoadChanges;
}
