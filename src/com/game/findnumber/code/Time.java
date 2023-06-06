package com.game.findnumber.code;

public class Time {
    private int Second;
    private int Minute;

    public Time() {
        this.Second = 0;
        this.Minute = 0;
    }

    public Time(int Minute, int Second) {
        this.Minute = Minute;
        this.Second = Second;
    }

    public void setTime(int Minute, int Second) {
        this.Second = Second;
        this.Minute = Minute;
    }

    public void nextSecond() {
        if (Second == 59) {
            Minute++;
            Second = 0;
            return;
        }
        Second++;
    }

    public String toString() {
        String minuteString = Minute < 10 ? "0" + Minute : Integer.toString(Minute);
        String secondString = Second < 10 ? "0" + Second : Integer.toString(Second);
        return minuteString + ":" + secondString;
    }
}
