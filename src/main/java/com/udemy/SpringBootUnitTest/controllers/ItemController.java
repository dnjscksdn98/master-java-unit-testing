package com.udemy.SpringBootUnitTest.controllers;

import com.udemy.SpringBootUnitTest.models.Item;
import com.udemy.SpringBootUnitTest.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(path = "items/{id}")
    public Item getItem(@PathVariable("id") Long id) {
        return new Item(id, "Mac Book Pro", 10, 100);
    }

    @GetMapping(path = "service/items/{id}")
    public Item getItemFromService(@PathVariable("id") Long id) {
        return itemService.findItem(id);
    }
}
