package com.udemy.SpringBootUnitTest.controllers;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws JSONException {
        String response = this.testRestTemplate.getForObject("/items", String.class);

        JSONAssert.assertEquals("[{id:10001},{id:10002},{id:10003},{id:10004}]", response, false);
    }
}
