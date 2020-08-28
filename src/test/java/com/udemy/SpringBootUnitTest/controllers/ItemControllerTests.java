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
}