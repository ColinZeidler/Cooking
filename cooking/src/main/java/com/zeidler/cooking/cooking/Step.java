package com.zeidler.cooking.cooking;

import java.util.Random;

/**
 * Created by Colin on 2014-04-13.
 */
public class Step implements Comparable<Step> {
    private int number;
    private String instructions;
    private long timer; //Timer of 0 means that the Step has no timer
    private long uID;   //will be set when added to the database the first time
    private boolean uIDLock = false; //prevents modification of the uID after it is set

    public Step(int n, String i, long t) {
        this.number = n;
        this.instructions = i;
        this.timer = t;

        setuID((new Random()).nextLong());
    }

    public Step(int n, String i, long t, long id) {
        this.number = n;
        this.instructions = i;
        this.timer = t;

        setuID(id);
    }

    public int getNumber() { return number; }
    public String getInstruct() { return instructions; }
    public long getTimer() { return timer; }
    public long getuID() { return uID; }

    public void setInstruct(String newIns) {
        instructions = newIns;
    }

    public void setTimer(long t) {
        timer = t;
    }

    public boolean hasTimer() {
        if (timer > 0)
            return true;
        else
            return false;
    }

    public void setuID(long l) {
        if (!uIDLock) { //only change if not already set
            uID = l;
            uIDLock = true;
        }
    }

    @Override
    public int compareTo(Step another) {
        return this.number - another.getNumber();
    }
}
