package com.center.hamonize.cmmn.exception;

import com.center.hamonize.cmmn.util.CodeMessage;

public class ProjectException extends RuntimeException {
	private static final long serialVersionUID = 5767353259987547807L;

	private CodeMessage code;
	private Object[] params;

	public ProjectException(CodeMessage code) {
		this(code, (Object[]) null);
	}

	public ProjectException(CodeMessage code, Object[] params) {
		this.code = code;
		this.params = params;
	}

	public ProjectException(CodeMessage code, Throwable cause) {
		this.code = code;
	}

	public ProjectException(CodeMessage code, Object[] params, Throwable cause) {
		super(cause);
		this.code = code;
		this.params = params;
	}

	public CodeMessage getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return code.getMessage(params);
	}
}