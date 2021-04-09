package com.jy.sample.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jy.sample.biz.entity.BizInterest;
import com.jy.sample.biz.entity.BizProducts;
import com.jy.sample.biz.entity.BizTransaction;
import com.jy.sample.biz.service.BusinessService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/biz")
@RequiredArgsConstructor
public class BusinessController {
	final private BusinessService businessService;

	@Operation(summary = "거래내역 조회 ", description = "Framework Layer -> Business Layer 호출, 인터페이스 아이디:bizA")
	@PostMapping(path = "/A")
	public Map<String, Object> bizA(@RequestBody Map<String, String> body) throws RuntimeException {
		// bizA POST OBJECT OBJECT http://localhost:8081/biz/A

		if (Objects.isNull(body) || Objects.isNull(body.get("tranDate"))) {// 20190101
			throw new RuntimeException("tranDate 누락");
		}
		String tranDate = body.get("tranDate");
		List<BizTransaction> list = this.businessService.transactionListByTranDate(tranDate);

		Map<String, Object> result = new HashMap<>();
		result.put(	"list",
					list);

		return result;

	}

	@Operation(summary = "이자내역 조회 ", description = "Framework Layer -> Business Layer 호출, 인터페이스 아이디:bizB")
	@PostMapping(path = "/B")
	public String bizB(@RequestBody String issuYr) throws RuntimeException {
		// bizB POST STRING STRING http://localhost:8081/biz/B

		if (StringUtils.isAllEmpty(issuYr)) {// 10001
			throw new RuntimeException("issuYr 누락");
		}

		List<BizInterest> list = this.businessService.interestListByIssuYr(issuYr);
		int sum = list	.stream()
						.mapToInt(interest -> Integer.parseInt(interest.getOrcpAmt()))
						.sum();
		return String.valueOf(sum);

	}

	@Operation(summary = "상품내역 조회 ", description = "Framework Layer -> Business Layer 호출, 인터페이스 아이디:bizC")
	@GetMapping(path = "/C")
	public Map<String, Object> bizC(@RequestParam(name = "prdNm") String prdNm) throws RuntimeException {
		// bizC GET STRING OBJECT http://localhost:8081/biz/C

		if (StringUtils.isAllEmpty(prdNm)) {// 미
			throw new RuntimeException("prdNm 누락");
		}

		List<BizProducts> list = this.businessService.productsListByPrdNm(prdNm);

		Map<String, Object> result = new HashMap<>();
		result.put(	"list",
					list);

		return result;
	}

}
