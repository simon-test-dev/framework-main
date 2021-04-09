package com.jy.app.exception;

import lombok.Getter;

public class FrameworkException
										extends
											RuntimeException {

	private static final long serialVersionUID = -3329637241028270896L;

	@Getter
	private FwErrorCode errorCode;

	private Object[] messageArgs;

	public FrameworkException(	FwErrorCode errorCode,
									String... messageArgs) {
		this.errorCode = errorCode;
		this.messageArgs = messageArgs;

	}

	@Override
	public String getMessage() {

		return String.format(	this.errorCode.getMessage(),
								this.messageArgs);
	}

}
