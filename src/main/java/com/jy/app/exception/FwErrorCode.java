package com.jy.app.exception;

import com.jy.framework.vo.ErrorResponseSimpleVO;
import com.jy.framework.vo.ErrorResponseVO;

import lombok.Getter;

public enum FwErrorCode {
	// framework layer
	FL_INTERNAL_SERVER_ERROR(200, ErrorResponseVO.class, "FL001", "framework layer internal server error [%s]"),
	FL_EMPTY_REQUEST_INTERFACE_ID(200, ErrorResponseVO.class, "FL002", "interfaceId 가 누락되었습니다."),
	FL_INVALID_REQUEST_INTERFACE_ID(200, ErrorResponseVO.class, "FL003", "해당 인터페이스를 찾을 수 없습니다. [interfaceId: %s]"),
	FL_NOT_SUPPORT_METHOD(200, ErrorResponseVO.class, "FL004", "지원하지 않는 메소드 입니다. [요청 메소드: %s, 지원 메소드: GET,POST]"),
	FL_INVALID_BIZ_REQUEST(200, ErrorResponseVO.class, "FL005", "요청 데이터부 형식이 부적절합니다.[%s]"),
	FL_INVALID_BIZ_RESPONSE(200, ErrorResponseVO.class, "FL006", "응답 데이터부 형식이 부적절합니다.[%s]"),
	FL_INVALID_DATASOURCE_SETTING(200,
		ErrorResponseVO.class,
		"FL007",
		"데이터소스 설정이 부적절합니다. [interfaceId: %s, propertyKey: %s, propertyValue: %s]"),

	// business layer
	BZ_INTERNAL_SERVER_ERROR(200, ErrorResponseVO.class, "BZ001", "business layer internal server error [%s]"),
	BZ_INVALID_REQUEST(200, ErrorResponseVO.class, "BZ002", "잘못된 요청입니다. [%s]"),

	// framework manager
	FM_INTERNAL_SERVER_ERROR(500, ErrorResponseSimpleVO.class, "FM001", "framework manager internal server error [%s]"),
	FM_EMPTY_REQUEST_INTERFACE_ID(401, ErrorResponseSimpleVO.class, "FM002", "interfaceId 가 누락되었습니다."),
	FM_INVALID_REQUEST_INTERFACE_ID(401, ErrorResponseSimpleVO.class, "FM003", "해당 인터페이스를 찾을 수 없습니다. [interfaceId: %s]"),

	// 공통
	ER_INTERNAL_SERVER_ERROR(500, ErrorResponseSimpleVO.class, "ER001", "internal server error [%s]"),;

	@Getter
	private final int status;

	@Getter
	private final String code;

	@Getter
	private final String message;

	@Getter
	private final Class<?> responseType;

	FwErrorCode(final int status,
				final Class<?> responseType,
				final String code,
				final String message) {
		this.status = status;
		this.responseType = responseType;
		this.message = message;
		this.code = code;

	}

}