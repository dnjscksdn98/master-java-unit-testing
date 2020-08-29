package com.udemy.SpringBootUnitTest.repository;

import com.udemy.SpringBootUnitTest.models.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void findAll() {
        List<Item> items = itemRepository.findAll();
        assertEquals(4, items.size());
    }
}