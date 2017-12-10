package com.ldthong.calculator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.calculator.CalculatorApplication;
import com.calculator.model.OperationRequest;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CalculatorApplication.class)
//@WebAppConfiguration
public class OperationControllerTest {
//	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
//
//	private MockMvc mockMvc;
//
//	@Autowired
//	private WebApplicationContext webApplicationContext;
//
//	@Before
//	public void setup() throws Exception {
//		this.mockMvc = webAppContextSetup(webApplicationContext).build();
//
//	}
//
//	@Test
//	public void testAdd() throws Exception {
//		mockMvc.perform(post("/operation/add")
//				.content(TestUtil.asJsonString(new OperationRequest(Mockito.anyInt(), Mockito.anyInt())))
//				.contentType(contentType)).andExpect(status().isOk());
//	}
//
//	@Test
//	public void testSubtract() throws Exception {
//		mockMvc.perform(post("/operation/subtract")
//				.content(TestUtil.asJsonString(new OperationRequest(Mockito.anyInt(), Mockito.anyInt())))
//				.contentType(contentType)).andExpect(status().isOk());
//	}
//
//	@Test
//	public void testMultiply() throws Exception {
//		mockMvc.perform(post("/operation/multiply")
//				.content(TestUtil.asJsonString(new OperationRequest(Mockito.anyInt(), Mockito.anyInt())))
//				.contentType(contentType)).andExpect(status().isOk());
//	}
//
//	@Test
//	public void testDivide() throws Exception {
//		mockMvc.perform(post("/operation/divide")
//				.content(TestUtil.asJsonString(new OperationRequest(Mockito.anyInt(), Mockito.anyInt())))
//				.contentType(contentType)).andExpect(status().isOk());
//	}
//	
//	@Test 
//	public void testHistory() throws Exception {
//		mockMvc.perform(get("/operation/history")
//				.content("")
//				.contentType(contentType)).andExpect(status().isOk());
//	}

}
