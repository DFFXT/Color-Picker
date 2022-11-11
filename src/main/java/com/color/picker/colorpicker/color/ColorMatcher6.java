package com.color.picker.colorpicker.color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 匹配 #FFFFFF格式，6位
 */
public class ColorMatcher6 extends ColorMatcher {
    // 匹配 #ffffff和ffffff
    private static final Pattern colorPattern6 = Pattern.compile("(^#[A-F|a-f\\d]{6}$)|(^[A-F|a-f\\d]{6}$)");

    @Override
    protected @Nullable String getHexColorString(@Nullable String beforeText, @NotNull String content) {
        Matcher matcher = colorPattern6.matcher(content);
        if (matcher.find()) {
            // 需要默认alpha为FF
            String r = matcher.group();
            if (r.startsWith("#")) {
                return "FF" + r.substring(1, 7);
            } else if (beforeText != null && beforeText.endsWith("#")){
                return "FF" + r;
            }
        }
        return null;
    }
}
