package com.zainabed.spring.security.jwt.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.zainabed.spring.security.jwt.SecurityJwtApplication;
import com.zainabed.spring.security.jwt.controller.security.TestJwtWebSecurity;
import com.zainabed.spring.security.jwt.service.AuthorizationHeaderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityJwtApplication.class, TestJwtWebSecurity.class })
@AutoConfigureMockMvc
@TestPropertySource(properties = { "jwt.authentication=true" })
public class JwtAuthenticationControllerTest {

	@Autowired
	MockMvc mvc;

	String apiPath;
	String username;
	String password;
	String authBasicHeader;

	@Before
	public void setup() {
		apiPath = "/auth";
		username = "testuser";
		password = "testpassword";
		buildToken(username, password);
	}

	@Test
	public void shouldReturnValidAuthToken() throws Exception {
		mvc.perform(post(apiPath).header(AuthorizationHeaderService.AUTH_HEADER, authBasicHeader)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists());
	}
	
	@Test
	public void shouldReturnInValidAuthToken() throws Exception {
		buildToken(username, "invalid");
		mvc.perform(post(apiPath).header(AuthorizationHeaderService.AUTH_HEADER, authBasicHeader)).andDo(print())
				.andExpect(status().is4xxClientError());
	}
	
	void buildToken(String username, String password) {
		authBasicHeader = AuthorizationHeaderService.AUTH_TYPE_BASIC
				+ Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}
}
