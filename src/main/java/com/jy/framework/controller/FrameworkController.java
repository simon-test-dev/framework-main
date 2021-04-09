package com.jy.framework.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jy.app.exception.FrameworkException;
import com.jy.framework.entity.TransactionInterface;
import com.jy.framework.service.FrameworkService;
import com.jy.framework.service.TransactionInterfaceService;
import com.jy.framework.service.TransactionLogService;
import com.jy.framework.vo.CommonData;
import com.jy.framework.vo.NormalRequestVO;
import com.jy.framework.vo.NormalResponseVO;
import com.jy.sample.biz.service.BusinessService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/fw")
@RequiredArgsConstructor
public class FrameworkController {

	final private FrameworkService frameworkService;

	final private TransactionInterfaceService transactionInterfaceService;

	final private TransactionLogService transactionLogService;

	final private BusinessService businessService;

	@Operation(	summary = "Framework Layer End-Point 호출",
				description = "Framework Flow\r\n"
								+ "1. Framework Layer End-Point 호출\r\n"
								+ "2. 공통부, 데이터부 분리\r\n"
								+ "3. 내부 데이터 셋에 적재\r\n"
								+ "4. 요청 공통부+데이터부 로깅\r\n"
								+ "5. 호출할 비지니스 정보 가져오기\r\n"
								+ "6. 요청 데이터부 로깅\r\n"
								+ "7. 비지니스 호출\r\n"
								+ "8. 정상 응답 데이터부 로깅\r\n"
								+ "9. 정상 응답 공통부 데이터 생성\r\n"
								+ "10. 정상 응답 전문 생성\r\n"
								+ "11. 응답 공통부+데이터부 로깅\r\n"
								+ "12 client에 응답    \r\n"
								+ "   \r\n"
								+ "   \r\n"
								+ "* 비지니스 A 요청전문: { \"commonData\": { \"guid\": \"02016101398487146504890949563021\", \"trxDatetime\": \"20210321122839243\", \"requestType\": \"S\", \"responseType\": \"\", \"interfaceId\": \"bizA\" }, \"bizDataIN\": { \"tranDate\":\"20190101\" } }\r\n"
								+ "* 비지니스 B 요청전문: { \"commonData\": { \"guid\": \"27219962563392962090840127134881\", \"trxDatetime\": \"20210321122839243\", \"requestType\": \"S\", \"responseType\": \"\", \"interfaceId\": \"bizB\" }, \"bizDataIN\": \"10001\" }\r\n"
								+ "* 비지니스 C 요청전문: { \"commonData\": { \"guid\": \"39718442043902569086688658531818\", \"trxDatetime\": \"20210321122839243\", \"requestType\": \"S\", \"responseType\": \"\", \"interfaceId\": \"bizC\" }, \"bizDataIN\": { \"prdNm\":\"미니\" } }\r\n"
								+ "* 비지니스 D 요청전문: { \"commonData\": { \"guid\": \"82097965703068010856468600657355\", \"trxDatetime\": \"20210321122839243\", \"requestType\": \"S\", \"responseType\": \"\", \"interfaceId\": \"bizD\" }, \"bizDataIN\": { \"sql\":\"select * from biz_transaction where tran_id = '10002'\" } }")
	@PostMapping
	public NormalResponseVO frameworkLayer(@Valid @RequestBody NormalRequestVO normalRequestVO) throws FrameworkException {

		// 공통부, 데이터부 분리
		final CommonData commonData = normalRequestVO.getCommonData();
		final Object bizDataIN = normalRequestVO.getBizDataIN();

		// 내부 데이터 셋에 적재
		this.frameworkService.setFrameworkAttribute(commonData);

		// 요청 공통부+데이터부 로깅
		this.transactionLogService.logging(	"S",
											"I",
											"NM",
											normalRequestVO);

		// 호출할 비지니스 정보 가져오기
		final TransactionInterface transactionInterface = this.frameworkService.getInterface(commonData.getInterfaceId());

		// 요청 데이터부 로깅
		this.transactionLogService.logging(	"S",
											"O",
											"NM",
											bizDataIN);

		// 비지니스 호출
		final Object bizDataOUT = this.frameworkService.callBiz(transactionInterface,
																bizDataIN);

		// 정상 응답 데이터부 로깅
		this.transactionLogService.logging(	"R",
											"I",
											"NM",
											bizDataOUT);

		// 정상 응답 공통부 데이터 생성
		final CommonData responseCommonData = this.frameworkService.generateResponseCommonData(	"R",
																								"NM");

		// 정상 응답 전문 생성
		final NormalResponseVO normalResponseVO = NormalResponseVO	.builder()
																	.commonData(responseCommonData)
																	.bizDataOUT(bizDataOUT)
																	.build();

		// 응답 공통부+데이터부 로깅
		this.transactionLogService.logging(	"R",
											"O",
											"NM",
											normalResponseVO);

		// client에 응답
		return normalResponseVO;
	}

}
