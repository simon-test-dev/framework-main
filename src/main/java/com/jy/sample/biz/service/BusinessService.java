package com.jy.sample.biz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.app.exception.FrameworkException;
import com.jy.sample.biz.entity.BizInterest;
import com.jy.sample.biz.entity.BizProducts;
import com.jy.sample.biz.entity.BizTransaction;
import com.jy.sample.biz.repository.BizInterestRepository;
import com.jy.sample.biz.repository.BizProductsRepository;
import com.jy.sample.biz.repository.BizTransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BusinessService {

	final private BizTransactionRepository bizTransactionRepository;

	final private BizInterestRepository bizInterestRepository;

	final private BizProductsRepository bizProductsRepository;

	public List<BizTransaction> transactionListByTranDate(String tranDate) throws RuntimeException {
		return this.bizTransactionRepository.findByTranDate(tranDate)
											.orElse(new ArrayList<>());

	}

	public List<BizInterest> interestListByIssuYr(String issuYr) throws RuntimeException {
		return this.bizInterestRepository	.findByIssuYr(issuYr)
											.orElse(new ArrayList<>());

	}

	public List<BizProducts> productsListByPrdNm(String prdNm) throws RuntimeException {
		return this.bizProductsRepository	.findByPrdNmContains(prdNm)
											.orElse(new ArrayList<>());

	}

	public void insertInitData() throws FrameworkException {

		this.insertTransactionInitData();
		this.insertInterestInitData();
		this.insertProductsInitData();
	}

	private List<BizTransaction> insertTransactionInitData() throws FrameworkException {
		List<BizTransaction> list = new ArrayList<>();
		Integer id = 10001;

		for (int i = 0; i < 5; i++) {
			BizTransaction entity = BizTransaction	.builder()
													.tranId(String.valueOf(id++))
													.tranDate("20190101")
													.tranTime("120101")
													.inoutType("I")
													.printContent("스타벅스")
													.tranAmt("10000")
													.build();
			list.add(entity);
		}
		return this.bizTransactionRepository.saveAll(list);

	}

	private List<BizInterest> insertInterestInitData() throws FrameworkException {
		List<BizInterest> list = new ArrayList<>();
		Integer id = 10001;

		for (int i = 0; i < 5; i++) {
			BizInterest entity = BizInterest.builder()
											.issuYr(String.valueOf(id++))
											.bondCcd("KKB")
											.orcpAmt("10000")
											.dvdnAmt("50")
											.build();
			list.add(entity);
		}
		return this.bizInterestRepository.saveAll(list);
	}

	private List<BizProducts> insertProductsInitData() throws FrameworkException {
		List<BizProducts> list = new ArrayList<>();
		Integer id = 10001;

		for (int i = 0; i < 5; i++) {
			BizProducts vo = BizProducts.builder()
										.idNo(String.valueOf(id++))
										.prdCd("MINI")
										.prdNm("미니")
										.build();
			list.add(vo);
		}
		return this.bizProductsRepository.saveAll(list);
	}
}
