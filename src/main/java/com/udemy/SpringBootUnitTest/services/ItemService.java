package com.udemy.SpringBootUnitTest.services;

import com.udemy.SpringBootUnitTest.models.Item;
import com.udemy.SpringBootUnitTest.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findItem(Long id) {
        return new Item(id, "Mac Book Pro", 10, 100);
    }

    public List<Item> findAll() {
        List<Item> items = itemRepository.findAll();
        items.forEach(item -> item.setValue(item.getPrice() * item.getQuantity()));
        return items;
    }
}
