package com.color.picker.colorpicker;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 颜色匹配接口
 */
public interface IColorMatcher {
    /**
     * 匹配颜色
     * @param content 原始字符
     * @return 颜色值；未匹配成功则返回null
     */
    @Nullable
    Integer extractColor(@NotNull String content);
}
