package com.example.e_commerce.samples;

import java.util.HashMap;
import java.util.Map;

public class TestClass {

    static int result = Integer.MAX_VALUE;

    public static int findShortestSubArray(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for(int v: nums){
            if(map.containsKey(v)){
                map.put(v, map.get(v) + 1);
            }else{
                map.put(v, 1);
            }
        }

        System.out.println(map);

        int max = -1;
        for(Map.Entry<Integer, Integer> entry: map.entrySet()){
            max = max < entry.getValue()? entry.getValue() : max;
        }

        System.out.println(max);

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == max) {
                setResult(entry.getKey(), nums);
            }
        }

        return result;
    }

    public static void setResult(int value, int[] nums){
        int i = 0, j = nums.length-1;
        while(nums[i] != value)
            i++;
        while (nums[j] != value)
            j--;
        int len = j - i + 1;
        result = result > len? len : result;
    }

    public static void main(String[] args) {
        int[] arr = {1,2,2,3,1};
        int res = findShortestSubArray(arr);
        System.out.println("The result is : " + res);
    }
}
