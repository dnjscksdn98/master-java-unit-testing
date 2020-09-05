package com.udemy.SpringBootUnitTest.assertj;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertJTests {

    @Test
    public void assertJ() {
        List<Integer> numbers = Arrays.asList(12, 15, 45);

        assertThat(numbers)
                .hasSize(3)
                .contains(12, 15)
                .allMatch(number -> number > 10)
                .allMatch(number -> number < 100)
                .noneMatch(number -> number < 0);

        assertThat("").isEmpty();

        assertThat("abcde")
                .contains("abc")
                .startsWith("abc")
                .endsWith("cde");
    }
}
