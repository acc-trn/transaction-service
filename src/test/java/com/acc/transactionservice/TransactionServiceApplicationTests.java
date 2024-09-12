package com.acc.transactionservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class TransactionServiceApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> TransactionServiceApplication.main(new String[] {}));
	}

}
