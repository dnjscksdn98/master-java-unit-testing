package com.udemy.SpringBootUnitTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"classpath:test-configuration.yml"})
class SpringBootUnitTestApplicationTests {

	@Test
	void contextLoads() {
	}

}
