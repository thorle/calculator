package com.calculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculator.cache.OperationCache;
import com.calculator.dao.OperationDAO;
import com.calculator.model.OperationDTO;
import com.calculator.model.OperationType;
import com.calculator.model.OperationVO;
import com.calculator.model.UserSession;
import com.calculator.validator.OperationValidator;

@Service
public class OperationServiceImpl implements OperationService {

	private static final Logger LOGGER = Logger.getLogger(OperationServiceImpl.class.toString());

	@Autowired
	private UserSession userSession;

	@Autowired
	private OperationDAO operationDAO;

	@Autowired
	private OperationCache operationServiceCache;

	@Override
	public OperationVO calcalate(int firstNumber, int secondNumber, OperationType method) {
		OperationVO operationVO = null;
		if (method.equals(OperationType.DIVIDE) && OperationValidator.isNumberValid(secondNumber)) {
			return operationVO;
		}
		List<OperationDTO> operationsCached = retrieveCache(firstNumber, secondNumber, method);
		if (operationsCached != null && !operationsCached.isEmpty()) {
			OperationDTO operation = operationsCached.stream().filter(o -> o.getUserId() == getUserId()).findFirst()
					.orElse(null);
			if (operation != null) {
				operationVO = new OperationVO(operation);
				return operationVO;
			}
			return saveAndUpdateCache(firstNumber, secondNumber, method, operationsCached.get(0).getResult());

		}

		int result = getResult(firstNumber, secondNumber, method);
		return saveAndUpdateCache(firstNumber, secondNumber, method, result);
	}

	private OperationVO saveAndUpdateCache(int firstNumber, int secondNumber, OperationType method, int result) {
		OperationDTO operationDTO = new OperationDTO(getUserId(), method.getCode(), method.getType(), firstNumber,
				secondNumber, result);
		OperationVO operationResult = null;
		LOGGER.log(Level.SEVERE, "OperationServiceImpl:saveAndUpdateCache --> save and update cache for operation: "
				+ operationDTO.toString());
		try {

			if (operationDAO.saveOperation(operationDTO)) {
				operationServiceCache.updateCache(operationDTO);
				operationResult = new OperationVO(operationDTO);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "OperationServiceImpl:saveAndUpdateCache --> An error during save operation: "
					+ operationDTO.toString());
			e.printStackTrace();
		}
		return operationResult;
	}

	private List<OperationDTO> retrieveCache(int firstNumber, int secondNumber, OperationType method) {
		List<OperationDTO> operationResult = new ArrayList<>();
		Map<Integer, List<OperationDTO>> operationsResult = operationServiceCache.getAllOperation();
		if (operationsResult != null && !operationsResult.isEmpty()) {
			List<OperationDTO> operationList = operationsResult.get(method.getCode());
			if (operationList == null) {
				return operationResult;
			}
			List<OperationDTO> operationsCached = operationList.stream()
					.filter(o -> o.getFirstNumber() == firstNumber && o.getSecondNumber() == secondNumber)
					.collect(Collectors.toList());
			if (operationsCached != null) {
				operationResult.addAll(operationsCached);
			}
		}
		return operationResult;
	}

	private int getResult(int firstNumber, int secondNumber, OperationType method) {
		int result = 0;
		switch (method) {
		case ADD:
			result = firstNumber + secondNumber;
			break;
		case SUBTRACT:
			result = firstNumber - secondNumber;
			break;
		case MULTIPLY:
			result = firstNumber * secondNumber;
			break;
		case DIVIDE:
			result = firstNumber / secondNumber;
			break;
		default:
			break;
		}
		return result;
	}

	private int getUserId() {
		return userSession.getUserId();
	}

	@Override
	public List<OperationVO> operationHistory() {
		return operationServiceCache.getOperationByUser();
	}

}
