package com.xuandq.androidtouchpad.model;

import java.io.Serializable;

public class MouseData implements Serializable {
    private int x = 0;
    private int y = 0;
    private int wheeel = 0;
    private boolean leftClick = false;
    private boolean rightClick = false;

    public MouseData(int x, int y, int wheeel, boolean leftClick, boolean rightClick) {
        this.x = x;
        this.y = y;
        this.wheeel = wheeel;
        this.leftClick = leftClick;
        this.rightClick = rightClick;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWheeel() {
        return wheeel;
    }

    public void setWheeel(int wheeel) {
        this.wheeel = wheeel;
    }

    public boolean isLeftClick() {
        return leftClick;
    }

    public void setLeftClick(boolean leftClick) {
        this.leftClick = leftClick;
    }

    public boolean isRightClick() {
        return rightClick;
    }

    public void setRightClick(boolean rightClick) {
        this.rightClick = rightClick;
    }

}
