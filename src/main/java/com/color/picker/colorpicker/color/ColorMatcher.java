package com.color.picker.colorpicker.color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 颜色提取
 */
public abstract class ColorMatcher {
    /**
     * 格式化字符串
     *
     * @param selectedContent 选中的内容
     * @return 格式化后的字符串
     */
    @NotNull
    protected String getFormatContent(@NotNull String selectedContent) {
        return selectedContent.replace(" ", "");
    }

    /**
     * 获取16进制的颜色字符串
     *
     * @param content 原始字符
     * @return 颜色字符串
     */
    @Nullable
    protected abstract String getHexColorString(@NotNull String content);

    /**
     * 获取字符串中的颜色值
     *
     * @param content 原始字符
     * @return 颜色对应的int值，没有则null
     */
    @Nullable
    public Integer extractColor(@NotNull String content) {
        String formatString = getFormatContent(content);
        if (formatString.isEmpty()) return null;
        String hexString = getHexColorString(content);
        if (hexString != null && !hexString.isEmpty()) {
            try {
                return (int) Long.parseLong(hexString, 16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
