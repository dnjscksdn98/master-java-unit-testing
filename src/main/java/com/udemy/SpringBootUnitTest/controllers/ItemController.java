package com.udemy.SpringBootUnitTest.controllers;

import com.udemy.SpringBootUnitTest.models.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @GetMapping(path = "items/{id}")
    public Item getItem(@PathVariable("id") Long id) {
        return new Item(id, "Mac Book Pro", 10, 100);
    }
}
