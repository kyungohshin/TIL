package tacos;
import static org.hamcrest.Matchers.containsString;
import static
	org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static
	org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static
	org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static
	org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/*
 * 웹테스트는 어렵지만, 스프링이 강력한 기능을 제공
 * 스프링 mvc형태로 테스트가 실행되도록함
 */
//@WebMvcTest(HomeController.class)
@WebMvcTest()
public class HomeControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Test
	public void testHomePage() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("home"))
		.andExpect(content().string(
				containsString("Welcome to...")));
	}
}
