package com.udemy.SpringBootUnitTest.services;

import com.udemy.SpringBootUnitTest.repository.SimpleDataRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleDataRepositoryStub implements SimpleDataRepository {

    @Override
    public int[] findAll() {
        return new int[] {1, 2, 3};
    }
}

class SimpleDataRepositoryEmptyStub implements SimpleDataRepository {

    @Override
    public int[] findAll() {
        return new int[] {};
    }
}

public class SimpleServiceStubTests {

    @Test
    public void calculateSumUsingDataService_basic() {
        SimpleService simpleService = new SimpleService();
        simpleService.setSimpleDataRepository(new SimpleDataRepositoryStub());

        int actualResult = simpleService.calculateSumUsingDataService();
        int expectedResult = 6;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void calculateSumUsingDataService_emptyArray() {
        SimpleService simpleService = new SimpleService();
        simpleService.setSimpleDataRepository(new SimpleDataRepositoryEmptyStub());

        int actualResult = simpleService.calculateSumUsingDataService();
        int expectedResult = 0;
        assertEquals(expectedResult, actualResult);
    }

}
