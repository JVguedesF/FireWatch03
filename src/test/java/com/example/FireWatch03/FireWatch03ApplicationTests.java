package com.example.FireWatch03;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
		properties = {
				"spring.flyway.enabled=false",
				"spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
				"spring.datasource.driver-class-name=org.h2.Driver"
		}
)
@ActiveProfiles("test")
class FireWatch03ApplicationTests {
	@Test
	void contextLoads() { }
}
