package com.udemy.SpringBootUnitTest.services;

import com.udemy.SpringBootUnitTest.models.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    public Item findItem(Long id) {
        return new Item(id, "Mac Book Pro", 10, 100);
    }
}
