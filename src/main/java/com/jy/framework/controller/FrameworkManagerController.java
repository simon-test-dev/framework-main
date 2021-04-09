package com.jy.framework.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jy.framework.entity.InterfaceProperty;
import com.jy.framework.entity.TransactionInterface;
import com.jy.framework.entity.TransactionLog;
import com.jy.framework.service.FrameworkUtils;
import com.jy.framework.service.InterfacePropertyService;
import com.jy.framework.service.TransactionInterfaceService;
import com.jy.framework.service.TransactionLogService;
import com.jy.sample.biz.service.BusinessService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/mng")
@RequiredArgsConstructor
public class FrameworkManagerController {

	final private TransactionInterfaceService transactionInterfaceService;

	final private TransactionLogService transactionLogService;

	final private InterfacePropertyService interfacePropertyService;

	final private BusinessService businessService;

	final private FrameworkUtils frameworkUtils;

	@Operation(summary = "초기 데이터 생성", description = "초기 데이터(인터페이스, biz샘플데이터)를 생성한다.")
	@PostMapping(path = "/init")
	public String insertInitData() {
		this.transactionInterfaceService.insertInitData();

		this.businessService.insertInitData();

		this.interfacePropertyService.insertInitData();

		return "SUCCESS";
	}

	@Operation(summary = "로그 목록 조회", description = "검색 조건에 맞는 로그 목록을 보여준다. (검색조건 : contains)")
	@GetMapping(path = "/log/list")
	public List<TransactionLog> logList(@Parameter(	example = "",
													description = "로그 아이디(자동 생성) ") @RequestParam(	name = "id",
																									required = false) String id,
										@Parameter(	example = "02016101398487146504890949563021",
													description = "거래 Unique 한 키 (클라이언트에서 생성)") @RequestParam(	name = "guid",
																												required = false) String guid,
										@Parameter(	example = "",
													description = "로그 시간") @RequestParam(	name = "trxDatetime",
																							required = false) String trxDatetime,
										@Parameter(	example = "",
													description = "요청 타입 [S:요청 , R:응답]") @RequestParam(	name = "requestType",
																										required = false) String requestType,
										@Parameter(	example = "",
													description = "Framework 기준 거래 IN/OUT [I:프레임워크로 들어오는 거래(Inbound), O:프레임워크로 들어오는 거래(OutBound)]") @RequestParam(	name = "requestIo",
																																									required = false) String requestIo,
										@Parameter(	example = "",
													description = "거래 정상 여부 [NM:정상 , ER:오류]") @RequestParam(name = "responseType",
																											required = false) String responseType,
										@Parameter(	example = "",
													description = "인터페이스 아이디") @RequestParam(	name = "interfaceId",
																								required = false) String interfaceId,
										@Parameter(	example = "",
													description = "거래  전문") @RequestParam(	name = "message",
																							required = false) String message) {

		return this.transactionLogService.list(	id,
												guid,
												trxDatetime,
												requestType,
												requestIo,
												responseType,
												interfaceId,
												message);
	}

	@Operation(summary = "인터페이스 목록 조회", description = "검색 조건에 맞는 인터페이스 목록을 보여준다. (검색조건 : contains)")
	@GetMapping(path = "/interface/list")
	public List<TransactionInterface> interfaceList(@RequestParam(name = "id", required = false) String id,
													@RequestParam(name = "url", required = false) String url,
													@RequestParam(name = "method", required = false) String method,
													@RequestParam(name = "responseType", required = false) String responseType) {

		return this.transactionInterfaceService.list(	id,
														url,
														method,
														responseType);
	}

	@Operation(summary = "인터페이스 속성 목록 조회", description = "검색 조건에 맞는 인터페이스 속성 목록을 보여준다. (검색조건 : contains)")
	@GetMapping(path = "/interface/property/list")
	public List<InterfaceProperty> interfacePropertyList(	@RequestParam(name = "interfaceId", required = false) String interfaceId,
															@RequestParam(name = "propertyKey", required = false) String propertyKey,
															@RequestParam(	name = "propertyValue",
																			required = false) String propertyValue) {

		return this.interfacePropertyService.list(	interfaceId,
													propertyKey,
													propertyValue);
	}

	@Operation(summary = "인터페이스 속성 UPSERT", description = "인터페이스 속성 있으면 UPATE, 없으면 INSERT")
	@PostMapping(path = "/interface/property")
	public InterfaceProperty upsertInterfaceProperty(@RequestBody InterfaceProperty interfaceProperty) {

		return this.interfacePropertyService.upsert(interfaceProperty);
	}

	@Operation(summary = "guid 생성", description = "거래 테스트를 위해 난수 생성 ")
	@GetMapping(path = "/guid")
	public String generateGuid() {
		return this.frameworkUtils.generateGuid();
	}

}
