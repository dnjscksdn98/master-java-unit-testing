package com.udemy.SpringBootUnitTest.hamcrest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersTests {

    @Test
    public void hamcrestMatchers() {
        List<Integer> numbers = Arrays.asList(12, 15, 45);

        assertThat(numbers, hasSize(3));
        assertThat(numbers, hasItems(12, 45));
        assertThat(numbers, everyItem(greaterThan(10)));
        assertThat(numbers, everyItem(lessThan(100)));

        assertThat("abcde", containsString("abc"));
        assertThat("abcde", startsWith("abc"));
        assertThat("abcde", endsWith("cde"));
    }
}
