package com.color.picker.colorpicker.color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 匹配 0xFFFFFFFF格式，8位
 */
public class ColorMatcher0x extends ColorMatcher {
    private static final Pattern colorPattern8 = Pattern.compile("^0x[A-F|a-f\\d]{8}");

    @Override
    protected @Nullable String getHexColorString(@NotNull String content) {
        Matcher matcher = colorPattern8.matcher(content);
        if (matcher.find()) {
            return matcher.group().substring(2, 10);
        }
        return null;
    }
}
