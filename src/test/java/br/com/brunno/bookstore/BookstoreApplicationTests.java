package br.com.brunno.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;

@SpringBootTest
class BookstoreApplicationTests {

	@MockBean
	Clock clock;

	@Test
	void contextLoads() {
	}

}
