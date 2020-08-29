package com.udemy.SpringBootUnitTest.services;

import com.udemy.SpringBootUnitTest.models.Item;
import com.udemy.SpringBootUnitTest.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTests {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void findAll() {
        when(itemRepository.findAll())
                .thenReturn(List.of(
                        new Item(1L, "Mac Book Pro", 10, 100),
                        new Item(2L, "IPhone", 5, 70),
                        new Item(3L, "IPad", 7, 90)));

        List<Item> items = itemService.findAll();
        assertEquals(1000, items.get(0).getValue());
        assertEquals(350, items.get(1).getValue());
        assertEquals(630, items.get(2).getValue());
    }
}