package hse_st_group1.esbot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hse_st_group1.esbot.controller.TestController;


@SpringBootTest
@ActiveProfiles("test")
class EsbotApplicationTests {

	@Test
	void contextLoads() {
	}

}


@SpringBootTest
@ActiveProfiles("test")
class SmokeTest {

	@Autowired
	private TestController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}