package com.example.user.quickcoding1;

/**
 * Created by user on 2016-11-02.
 */

public class MyAverage implements MyValue {
    int sum = 0;
    int aver = 0;
    public int getResult() {
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        return aver = sum / 5;
    }
}