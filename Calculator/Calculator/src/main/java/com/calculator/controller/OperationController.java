package com.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.calculator.model.ErrorCode;
import com.calculator.model.OperationType;
import com.calculator.model.ResultVO;
import com.calculator.model.UserSession;
import com.calculator.service.OperationService;

@RestController
@RequestMapping("/operation")
public class OperationController {

	@Autowired
	private OperationService operationService;

	@Autowired
	private UserSession userSession;

	@RequestMapping("/add")
	public Object add(@RequestParam("firstNumber") int firstNumber, @RequestParam("secondNumber") int secondNumber) {
		if (userSession.getUserId() > 0) {
			return operationService.calcalate(firstNumber, secondNumber, OperationType.ADD);
		}
		return new ResultVO(ErrorCode.OPERATION_OPERMISSION.getErrorCode(),
				ErrorCode.OPERATION_OPERMISSION.getMessage());

	}

	@RequestMapping("/subtract")
	public Object subtract(@RequestParam("firstNumber") int firstNumber,
			@RequestParam("secondNumber") int secondNumber) {
		if (userSession.getUserId() > 0) {
			return operationService.calcalate(firstNumber, secondNumber, OperationType.SUBTRACT);
		}
		return new ResultVO(ErrorCode.OPERATION_OPERMISSION.getErrorCode(),
				ErrorCode.OPERATION_OPERMISSION.getMessage());

	}

	@RequestMapping("/multiply")
	public Object multiply(@RequestParam("firstNumber") int firstNumber,
			@RequestParam("secondNumber") int secondNumber) {
		if (userSession.getUserId() > 0) {
			return operationService.calcalate(firstNumber, secondNumber, OperationType.MULTIPLY);
		}
		return new ResultVO(ErrorCode.OPERATION_OPERMISSION.getErrorCode(),
				ErrorCode.OPERATION_OPERMISSION.getMessage());

	}

	@RequestMapping("/divide")
	public Object divide(@RequestParam("firstNumber") int firstNumber, @RequestParam("secondNumber") int secondNumber) {
		if (userSession.getUserId() > 0) {
			return operationService.calcalate(firstNumber, secondNumber, OperationType.DIVIDE);
		}
		return new ResultVO(ErrorCode.OPERATION_OPERMISSION.getErrorCode(),
				ErrorCode.OPERATION_OPERMISSION.getMessage());

	}

	@RequestMapping("/history")
	public Object history() {
		if (userSession.getUserId() > 0) {
			return operationService.operationHistory();
		}
		return new ResultVO(ErrorCode.OPERATION_OPERMISSION.getErrorCode(),
				ErrorCode.OPERATION_OPERMISSION.getMessage());
	}

}
