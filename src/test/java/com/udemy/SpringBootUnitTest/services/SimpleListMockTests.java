package com.udemy.SpringBootUnitTest.services;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleListMockTests {

    private final List<String> mock = mock(List.class);

    @Test
    public void listSize_basic() {
        when(mock.size()).thenReturn(5);

        assertEquals(5, mock.size());
    }

    @Test
    public void returnMultipleValues() {
        when(mock.size()).thenReturn(5).thenReturn(10);

        assertEquals(5, mock.size());
        assertEquals(10, mock.size());
    }

    @Test
    public void returnWithParameters() {
        when(mock.get(0)).thenReturn("in28minutes");
        assertEquals("in28minutes", mock.get(0));
        assertNull(mock.get(1));
    }

    @Test
    public void returnWithGenericParameters() {
        when(mock.get(anyInt())).thenReturn("in28minutes");
        assertEquals("in28minutes", mock.get(0));
        assertEquals("in28minutes", mock.get(1));
    }
}
