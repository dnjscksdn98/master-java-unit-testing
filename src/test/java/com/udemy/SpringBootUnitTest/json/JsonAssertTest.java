package com.udemy.SpringBootUnitTest.json;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonAssertTest {

    String actualResponse = "{\"id\":1,\"name\":\"Mac Book Pro\",\"price\":10,\"quantity\":100}";

    @Test
    public void jsonAssert_StrictTrue() throws Exception {
        String expectedResponse = "{\"id\":1,\"name\":\"Mac Book Pro\",\"price\":10,\"quantity\":100}";
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    public void jsonAssert_StrictFalse() throws Exception {
        String expectedResponse = "{\"id\":1,\"name\":\"Mac Book Pro\"}";
        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
    }

}
