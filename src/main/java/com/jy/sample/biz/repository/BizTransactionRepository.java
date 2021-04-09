package com.jy.sample.biz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jy.sample.biz.entity.BizTransaction;

@Repository
public interface BizTransactionRepository
											extends
												JpaRepository<BizTransaction, String> {
	Optional<List<BizTransaction>> findByTranDate(String tranDate);

}
