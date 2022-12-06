package com.codevui.realworldapp.demoTest;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomMath {
    
    private final SortService sortService;

    public int getMinOrMaxValue(int numbers[], boolean findMax){
        //sap xep mang numbers theo thu tu tang dan
        int[] sortedNumbers = sortService.sort(numbers);
        if (findMax){
            return sortedNumbers[sortedNumbers.length - 1];
        }else{
            return sortedNumbers[0];
        }
    }
}
