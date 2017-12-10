package com.ldthong.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.calculator.CalculatorApplication;
import com.calculator.cache.OperationCache;
import com.calculator.dao.OperationDAO;
import com.calculator.model.OperationDTO;
import com.calculator.model.OperationType;
import com.calculator.model.OperationVO;
import com.calculator.model.UserSession;
import com.calculator.service.OperationService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class OperationServiceTest {

	@MockBean
	private UserSession userSession;

	@MockBean
	private OperationDAO operationDAO;

	@MockBean
	private OperationCache operationServiceCache;

	@Autowired
	private OperationService operationService;

	@Test
	public void testCalcalateWithDivideFail() throws Exception {
		OperationVO result = operationService.calcalate(1, 0, OperationType.DIVIDE);
		assertNull(result);
	}

	@Test
	public void testCalcalateWithOutCache() throws Exception {
		given(operationServiceCache.getAllOperation()).willReturn(null);
		given(operationDAO.saveOperation(Mockito.any())).willReturn(true);
		OperationVO result = operationService.calcalate(1, 2, OperationType.ADD);
		assertNotNull(result);
		assertEquals(1, result.getFirstNumber());
		assertEquals(2, result.getSecondNumber());
		assertEquals("ADD", result.getOperationType());
		assertEquals(3, result.getResult());

	}

	@Test
	public void testCalculateWithCacheWithOutSaveDB() {
		List<OperationDTO> operationList = new ArrayList<>();
		OperationDTO operation1 = new OperationDTO(1, 2, "SUBTRACT", 10, 2, 8);
		operationList.add(operation1);
		Map<Integer, List<OperationDTO>> operationByType = new HashMap<>();
		operationByType.put(2, operationList);
		given(operationServiceCache.getAllOperation()).willReturn(operationByType);
		given(userSession.getUserId()).willReturn(1);

		OperationVO result = operationService.calcalate(10, 2, OperationType.SUBTRACT);
		assertNotNull(result);
		assertEquals(10, result.getFirstNumber());
		assertEquals(2, result.getSecondNumber());
		assertEquals("SUBTRACT", result.getOperationType());
		assertEquals(8, result.getResult());

	}

	@Test
	public void testCalculateWithCacheAndSaveDB() {
		List<OperationDTO> operationList = new ArrayList<>();
		OperationDTO operation1 = new OperationDTO(2, 2, "SUBTRACT", 10, 2, 8);
		operationList.add(operation1);
		Map<Integer, List<OperationDTO>> operationByType = new HashMap<>();
		operationByType.put(2, operationList);
		given(operationServiceCache.getAllOperation()).willReturn(operationByType);
		given(userSession.getUserId()).willReturn(1);
		given(operationDAO.saveOperation(Mockito.any())).willReturn(true);

		OperationVO result = operationService.calcalate(10, 2, OperationType.SUBTRACT);
		assertNotNull(result);
		assertEquals(10, result.getFirstNumber());
		assertEquals(2, result.getSecondNumber());
		assertEquals("SUBTRACT", result.getOperationType());
		assertEquals(8, result.getResult());

		verify(operationDAO, times(1)).saveOperation(Mockito.any());
		verify(operationServiceCache, times(1)).updateCache(Mockito.any());

	}

	@Test
	public void testCalculateButCannotSaveDB() throws Exception {
		List<OperationDTO> operationList = new ArrayList<>();
		OperationDTO operation1 = new OperationDTO(2, 2, "SUBTRACT", 10, 2, 8);
		operationList.add(operation1);
		Map<Integer, List<OperationDTO>> operationByType = new HashMap<>();
		operationByType.put(2, operationList);
		given(operationServiceCache.getAllOperation()).willReturn(operationByType);
		given(userSession.getUserId()).willReturn(1);
		given(operationDAO.saveOperation(Mockito.any())).willThrow(new RuntimeException());

		OperationVO result = operationService.calcalate(10, 2, OperationType.SUBTRACT);
		assertNull(result);

		verify(operationDAO, times(1)).saveOperation(Mockito.any());
		verify(operationServiceCache, times(0)).updateCache(Mockito.any());
	}

	@Test
	public void testCalculateWithCacheEmpty() throws Exception {
		List<OperationDTO> operationList = new ArrayList<>();
		OperationDTO operation1 = new OperationDTO(1, 4, "DIVIDE", 10, 2, 5);
		operationList.add(operation1);
		Map<Integer, List<OperationDTO>> operationByType = new HashMap<>();
		operationByType.put(2, operationList);
		given(operationServiceCache.getAllOperation()).willReturn(operationByType);
		given(userSession.getUserId()).willReturn(1);
		given(operationDAO.saveOperation(Mockito.any())).willReturn(true);

		OperationVO result = operationService.calcalate(10, 2, OperationType.DIVIDE);
		assertNotNull(result);
		assertEquals(10, result.getFirstNumber());
		assertEquals(2, result.getSecondNumber());
		assertEquals("DIVIDE", result.getOperationType());
		assertEquals(5, result.getResult());

		verify(operationDAO, times(1)).saveOperation(Mockito.any());
		verify(operationServiceCache, times(1)).updateCache(Mockito.any());
	}

	@Test
	public void testGetHistory() throws Exception {
		List<OperationVO> operationList = new ArrayList<>();
		OperationDTO operationDto = new OperationDTO(1, 1, "ADD", 1, 1, 2);
		OperationVO operation = new OperationVO(operationDto);
		operationList.add(operation);
		given(operationServiceCache.getOperationByUser()).willReturn(operationList);
		List<OperationVO> operationH = operationService.operationHistory();
		assertNotNull(operationH);
		assertEquals(1, operationH.size());
		OperationVO operationCache = operationH.get(0);
		assertEquals(1, operationCache.getFirstNumber());
		assertEquals(1, operationCache.getSecondNumber());
		assertEquals(2, operationCache.getResult());
		assertEquals("ADD", operationCache.getOperationType());

	}

}
