package com.example.user.quickcoding1;

/**
 * Created by user on 2016-11-02.
 */

public class MyMinimum implements MyValue {
    int min = 0;
    public int getResult() {
        for(int i = 0; i < nums.length-1; i++ ) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    int temp = nums[j];
                    nums[j] = nums[i];
                    nums[i] = temp;
                }
            }
        }
        return min = nums[0];
    }
}
