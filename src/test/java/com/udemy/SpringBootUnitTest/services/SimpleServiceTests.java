package com.udemy.SpringBootUnitTest.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleServiceTests {

    @Test
    void calculateSum_basic() {
        SimpleService simpleService = new SimpleService();

        int actualResult = simpleService.calculateSum(new int[] {1, 2, 3});
        int expectedResult = 6;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void calculateSum_emptyArray() {
        SimpleService simpleService = new SimpleService();

        int actualResult = simpleService.calculateSum(new int[] {});
        int expectedResult = 0;
        assertEquals(expectedResult, actualResult);
    }

}