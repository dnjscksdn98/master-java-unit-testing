package com.udemy.SpringBootUnitTest.services;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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

    @Test
    public void verifyMethod_basic() {
        String value = mock.get(0);
        String value2 = mock.get(1);

        verify(mock).get(0);
        verify(mock, times(2)).get(anyInt());
        verify(mock, atLeast(1)).get(anyInt());
        verify(mock, atLeastOnce()).get(anyInt());
        verify(mock, atMost(2)).get(anyInt());
        verify(mock, never()).get(2);
    }

    @Test
    public void argumentCapture() {
        mock.add("Test String");

        // Argument Capture Verification
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mock).add(captor.capture());

        assertEquals("Test String", captor.getValue());
    }

    @Test
    public void multipleArgumentCapture() {
        mock.add("Test String 1");
        mock.add("Test String 2");

        // Argument Capture Verification
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mock, times(2)).add(captor.capture());

        List<String> allValues = captor.getAllValues();
        assertEquals("Test String 1", allValues.get(0));
        assertEquals("Test String 2", allValues.get(1));
    }

    @Test
    public void withoutSpying() {
        ArrayList<String> mockList = mock(ArrayList.class);

        System.out.println(mockList.get(0));  // null
        System.out.println(mockList.size());  // 0

        mockList.add("Test String 1");
        mockList.add("Test String 2");

        System.out.println(mockList.size());  // 0

        when(mockList.size()).thenReturn(5);
        System.out.println(mockList.size());  // 5
    }

    @Test
    public void spying() {
        ArrayList<String> spyList = spy(ArrayList.class);

        //System.out.println(spyList.get(0));  // IndexOutOfBoundsException
        System.out.println(spyList.size());  // 0

        spyList.add("Test String 1");
        spyList.add("Test String 2");

        System.out.println(spyList.size());  // 2
        System.out.println(spyList.get(0));  // Test String 1
        System.out.println(spyList.get(1));  // Test String 2

        when(spyList.size()).thenReturn(10);  // 행동 재정의
        System.out.println(spyList.size());  // 10
    }
}
