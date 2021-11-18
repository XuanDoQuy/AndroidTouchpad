package com.xuandq.androidtouchpad.ui.widget;

public interface DrawModeListener {
    void onPress(float x, float y);
    void onMove(float x, float y);
    void onRelease(float x, float y);
}
