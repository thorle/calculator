package com.calculator.model;

public enum OperationType {
	ADD("ADD", 1), SUBTRACT("SUBTRACT", 2), MULTIPLY("MULTIPLY", 3), DIVIDE("DIVIDE", 4);

	private final int code;
	private final String type;

	OperationType(String type, int code) {
		this.code = code;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public int getCode() {
		return code;
	}

}
