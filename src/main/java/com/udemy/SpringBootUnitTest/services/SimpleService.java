package com.udemy.SpringBootUnitTest.services;

import com.udemy.SpringBootUnitTest.repository.SimpleDataRepository;

public class SimpleService {

    private SimpleDataRepository simpleDataRepository;

    public void setSimpleDataRepository(SimpleDataRepository simpleDataRepository) {
        this.simpleDataRepository = simpleDataRepository;
    }

    public int calculateSum(int[] data) {
        int sum = 0;
        for(int value: data) {
            sum += value;
        }
        return sum;
    }

    public int calculateSumUsingDataService() {
        int sum = 0;
        int[] data = simpleDataRepository.findAll();
        for(int value: data) {
            sum += value;
        }
        return sum;
    }
}
