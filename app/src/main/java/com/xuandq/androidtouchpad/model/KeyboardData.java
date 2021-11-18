package com.xuandq.androidtouchpad.model;

public class KeyboardData {
    private String input;
    private boolean isText;
    private String keyCode;

    public KeyboardData(String input, boolean isText, String keyCode) {
        this.input = input;
        this.isText = isText;
        this.keyCode = keyCode;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public boolean isText() {
        return isText;
    }

    public void setText(boolean text) {
        isText = text;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }
}
