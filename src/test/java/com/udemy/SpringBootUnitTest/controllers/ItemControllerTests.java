package com.udemy.SpringBootUnitTest.controllers;

import com.udemy.SpringBootUnitTest.models.Item;
import com.udemy.SpringBootUnitTest.services.ItemService;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void getItem_basic() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/items/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = "{\"id\":1,\"name\":\"Mac Book Pro\",\"price\":10,\"quantity\":100}";
        JSONAssert.assertEquals(result.getResponse().getContentAsString(), actualResponse, true);
    }

    @Test
    public void getItem_fromBusinessService() throws Exception {

        // mock the behavior for the service
        when(itemService.findItem(anyLong()))
                .thenReturn(new Item(1L, "Mac Book Pro", 10, 100));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/service/items/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = "{\"id\":1,\"name\":\"Mac Book Pro\",\"price\":10,\"quantity\":100}";
        JSONAssert.assertEquals(result.getResponse().getContentAsString(), actualResponse, true);
    }

    @Test
    public void list() throws Exception {
        when(itemService.findAll())
                .thenReturn(List.of(
                        new Item(1L, "Mac Book Pro", 10, 100),
                        new Item(2L, "IPhone", 5, 70),
                        new Item(3L, "IPad", 7, 90)));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/items")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponse = "[{id:1,name:\"Mac Book Pro\",price:10,quantity:100},{id:2,name:\"IPhone\",price:5,quantity:70},{id:3,name:\"IPad\",price:7,quantity:90}]";
        JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
    }
}