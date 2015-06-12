package com.ugabuga.zitu.myapplication;

/**
 * Created by user on 12-Jun-15.
 */
public class Point {
    int x;
    int y;
    private int value;

    public Point(int x, int y) {
        // TODO Auto-generated constructor stub
        this.x = x;
        this.y = y;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        String str = (x + " " + y + " " + value);
        return str;
    }

}