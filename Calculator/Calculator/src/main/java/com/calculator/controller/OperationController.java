package com.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calculator.model.ErrorCode;
import com.calculator.model.OperationRequest;
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
	public Object add(@RequestBody OperationRequest operation) {
		if (userSession.getUserId() > 0) {
			return operationService.calcalate(operation.getFirstNumber(), operation.getSecondNumber(),
					OperationType.ADD);
		}
		return new ResultVO(ErrorCode.OPERATION_OPERMISSION.getErrorCode(),
				ErrorCode.OPERATION_OPERMISSION.getMessage());

	}

	@RequestMapping("/subtract")
	public Object subtract(@RequestBody OperationRequest operation) {
		if (userSession.getUserId() > 0) {
			return operationService.calcalate(operation.getFirstNumber(), operation.getSecondNumber(),
					OperationType.SUBTRACT);
		}
		return new ResultVO(ErrorCode.OPERATION_OPERMISSION.getErrorCode(),
				ErrorCode.OPERATION_OPERMISSION.getMessage());

	}

	@RequestMapping("/multiply")
	public Object multiply(@RequestBody OperationRequest operation) {
		if (userSession.getUserId() > 0) {
			return operationService.calcalate(operation.getFirstNumber(), operation.getSecondNumber(),
					OperationType.MULTIPLY);
		}
		return new ResultVO(ErrorCode.OPERATION_OPERMISSION.getErrorCode(),
				ErrorCode.OPERATION_OPERMISSION.getMessage());

	}

	@RequestMapping("/divide")
	public Object divide(@RequestBody OperationRequest operation) {
		if (userSession.getUserId() > 0) {
			return operationService.calcalate(operation.getFirstNumber(), operation.getSecondNumber(),
					OperationType.DIVIDE);
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
