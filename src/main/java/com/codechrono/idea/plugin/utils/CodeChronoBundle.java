package com.codechrono.idea.plugin.utils;


import com.intellij.AbstractBundle;

import org.jetbrains.annotations.*;


public final class CodeChronoBundle extends AbstractBundle {
    @NonNls
    public static final String ONEDAY_BUNDLE = "CodeChronoBundle";
    private static final CodeChronoBundle INSTANCE = new CodeChronoBundle();

    private CodeChronoBundle() {
        super(ONEDAY_BUNDLE);
    }
    //项目中可调用该静态方法，为组件文本赋值
    @NotNull
    public static String message(@NotNull @PropertyKey(resourceBundle = ONEDAY_BUNDLE) String key, @NotNull Object  ... params) {
        return INSTANCE.getMessage(key, params);
    }

}