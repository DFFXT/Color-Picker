package com.color.picker.colorpicker;

import com.color.picker.colorpicker.color.ColorMatcher;
import com.color.picker.colorpicker.color.ColorMatcher0x;
import com.color.picker.colorpicker.color.ColorMatcher6;
import com.color.picker.colorpicker.color.ColorMatcher8;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ColorsUtil {
    // 匹配 不带前缀格式，8位或6位
    private static ArrayList<ColorMatcher> colorMatchers = new ArrayList<>();
    static {
        colorMatchers.add(new ColorMatcher8());
        colorMatchers.add(new ColorMatcher6());
        colorMatchers.add(new ColorMatcher0x());
    }
    @Nullable
    public static Integer getColor(String beforeText, String args) {
        for (ColorMatcher colorMatcher : colorMatchers) {
            Integer color = colorMatcher.extractColor(beforeText, args);
            if (color != null) {
                return color;
            }
        }
        return null;
    }
}
