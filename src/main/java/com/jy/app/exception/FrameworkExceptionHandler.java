package com.jy.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jy.framework.service.FrameworkUtils;
import com.jy.framework.service.TransactionLogService;
import com.jy.framework.vo.BizDataERR;
import com.jy.framework.vo.CommonData;
import com.jy.framework.vo.ErrorResponseSimpleVO;
import com.jy.framework.vo.ErrorResponseVO;
import com.jy.framework.vo.FrameworkAttribute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class FrameworkExceptionHandler {

	final private FrameworkUtils frameworkUtils;

	final private TransactionLogService transactionLogService;

	@ExceptionHandler(FrameworkException.class)
	protected ResponseEntity<?> handleException(FrameworkException e) {
		log.error(	"FrameworkException  ",
					e);

		Class<?> responseType = e	.getErrorCode()
									.getResponseType();
		if (responseType == ErrorResponseVO.class) {

			// 에러 응답 전문 생성 (공통부+데이터부)
			ErrorResponseVO response = this.generateErrorResponseVO(e);

			// 에러 응답 로깅
			this.transactionLogService.logging(	"R",
												"O",
												"ER",
												response);
			return new ResponseEntity<>(response,
										HttpStatus.valueOf(e.getErrorCode()
															.getStatus()));

		}
		else {
			// 내부 에러 응답 생성 (code+message)
			ErrorResponseSimpleVO response = this.generateErrorResponseSimpleVO(e	.getErrorCode()
																					.getCode(),
																				e.getMessage());

			return new ResponseEntity<>(response,
										HttpStatus.valueOf(e.getErrorCode()
															.getStatus()));

		}
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<?> handleException(Exception e) {
		log.error(	"Exception",
					e);

		// 내부 에러 응답 생성
		final ErrorResponseSimpleVO response = this.generateErrorResponseSimpleVO(	FwErrorCode.ER_INTERNAL_SERVER_ERROR.getCode(),
																					e.getMessage());

		return new ResponseEntity<>(response,
									HttpStatus.valueOf(FwErrorCode.ER_INTERNAL_SERVER_ERROR.getStatus()));
	}

	private ErrorResponseVO generateErrorResponseVO(FrameworkException e) {

		final CommonData commonData = CommonData.builder()
												.guid(FrameworkAttribute.get(FrameworkAttribute.guid))
												.trxDatetime(this.frameworkUtils.currentDate())
												.requestType("R")
												.responseType("ER")
												.interfaceId(FrameworkAttribute.get(FrameworkAttribute.interfaceId))
												.build();

		final BizDataERR bizData = BizDataERR	.builder()
												.errorCode(e.getErrorCode()
															.getCode())
												.errorMessage(e.getMessage())
												.build();

		final ErrorResponseVO response = ErrorResponseVO.builder()
														.commonData(commonData)
														.bizDataERR(bizData)
														.build();
		return response;
	}

	private ErrorResponseSimpleVO generateErrorResponseSimpleVO(String code,
																String messge) {

		return ErrorResponseSimpleVO.builder()
									.code(code)
									.message(messge)
									.build();

	}

}