package com.codevui.realworldapp.demoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomMathTest {

    @InjectMocks // class muon test
    private CustomMath customMath;

    @Mock // class gia su test ra true
    private SortService sortService;

    // moi @test la 1 testcase
    @Test
    void testGetMinOrMaxValue_Return_Max_Value() {
            // given - import dau vao
            
            int numbers[] = new int[] {1,4,2,5,3};
            boolean findMax = true;

            // when -- di gia su ket qua
            when(sortService.sort(numbers)).thenReturn(new int[]{1,2,3,4,5});

            //then - call method

            int maxValue = customMath.getMinOrMaxValue(numbers, findMax);

            assertEquals(5, maxValue);
    }

    @Test
    void testGetMinOrMaxValue_Return_Min_Value() {
            // given - import dau vao
            
            int numbers[] = new int[] {1,4,2,5,3};
            boolean findMax = false;

            SortService sortService = new SortService();
            CustomMath customMath = new CustomMath(sortService);

            //then - call method

            int minValue = customMath.getMinOrMaxValue(numbers, findMax);

            assertEquals(1, minValue);
    }
}
