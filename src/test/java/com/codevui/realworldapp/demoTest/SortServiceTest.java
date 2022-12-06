package com.codevui.realworldapp.demoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SortServiceTest {
    @Test
    void testSort() {

        //given
        SortService sortService = new SortService();
        int numbers[] = new int[] {1,4,5,3,2};

        //thuc thi
        int[] actual = sortService.sort(numbers);
        int[] expect = new int[] {1,2,3,4,5};

        //verify
        for (int i = 0; i < expect.length; i++) {
            assertEquals(expect[i], actual[i]);
        }

    }
}
