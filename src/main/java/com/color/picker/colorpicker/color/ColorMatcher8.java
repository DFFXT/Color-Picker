package com.color.picker.colorpicker.color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 匹配 #FFFFFFFF格式，8位
 */
public class ColorMatcher8 extends ColorMatcher {
    private static final Pattern colorPattern8 = Pattern.compile("(^#[A-F|a-f\\d]{8}$)|(^[A-F|a-f\\d]{8}$)");

    @Override
    protected @Nullable String getHexColorString(@Nullable String beforeText, @NotNull String content) {
        Matcher matcher = colorPattern8.matcher(content);
        if (matcher.find()) {
            String r = matcher.group();
            if (r.startsWith("#")) {
                 return r.substring(1, 9);
            } else if (beforeText != null && beforeText.endsWith("#"))
            return r;
        }
        return null;
    }

    public static void main(String[] args) {
        int f = 0;
    }
}
