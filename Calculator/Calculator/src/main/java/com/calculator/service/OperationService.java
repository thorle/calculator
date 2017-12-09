package com.calculator.service;

import java.util.List;

import com.calculator.model.OperationType;
import com.calculator.model.OperationVO;

public interface OperationService {

	public OperationVO calcalate(int firstNumber, int secondNumber, OperationType method);

	public List<OperationVO> operationHistory();

}
