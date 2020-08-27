package com.udemy.SpringBootUnitTest.services;

import com.udemy.SpringBootUnitTest.repository.SimpleDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimpleServiceMockTests {

    @InjectMocks
    SimpleService simpleService;

    @Mock
    SimpleDataRepository simpleDataRepository;

//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    public void calculateSumUsingDataService_basic() {
//        SimpleService simpleService = new SimpleService();
//
//        // 1. Create mock
//        SimpleDataRepository simpleDataRepositoryMock = mock(SimpleDataRepository.class);

        // 2. Specify mock -> when -> then
        when(simpleDataRepository.findAll()).thenReturn(new int[] {1, 2, 3});

        int actualResult = simpleService.calculateSumUsingDataService();
        int expectedResult = 6;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void calculateSumUsingDataService_emptyArray() {
//        SimpleService simpleService = new SimpleService();
//
//        // 1. Create mock
//        SimpleDataRepository simpleDataRepositoryMock = mock(SimpleDataRepository.class);

        // 2. Specify mock -> when -> then
        when(simpleDataRepository.findAll()).thenReturn(new int[] {});

        int actualResult = simpleService.calculateSumUsingDataService();
        int expectedResult = 0;
        assertEquals(expectedResult, actualResult);
    }
}
