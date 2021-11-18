package com.xuandq.androidtouchpad.model;

public class PowerpointData {
    private int state = 0; // 0 - non, 1 - press, 2 - move, 3 - release, 4 - prev, 5 - next
    private int x;
    private int y;

    public PowerpointData(int state, int x, int y) {
        this.state = state;
        this.x = x;
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
}
