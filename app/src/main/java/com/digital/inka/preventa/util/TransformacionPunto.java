package com.digital.inka.preventa.util;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;


public class TransformacionPunto extends PasswordTransformationMethod {


    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(super.getTransformation(source,view));
    }

    private static char DOT = '\u2022';
    private static char BIGGER_DOT = '●';

private static final class PasswordCharSequence implements CharSequence {

    private final CharSequence transformation;

    public char get(int index) {
        return this.transformation.charAt(index) == DOT ? '●' : this.transformation.charAt(index);
    }

    // $FF: bridge method
    public final char charAt(int var1) {
        return this.get(var1);
    }

    public final CharSequence getTransformation() {
        return this.transformation;
    }

    public PasswordCharSequence(CharSequence transformation) {
        Intrinsics.checkParameterIsNotNull(transformation, "transformation");
        this.transformation = transformation;
    }

    public int getLength() {
        return this.transformation.length();
    }

    // $FF: bridge method
    public final int length() {
        return this.getLength();
    }

    @NotNull
    public CharSequence subSequence(int startIndex, int endIndex) {
        return this.transformation.subSequence(startIndex, endIndex);
    }
}
}

