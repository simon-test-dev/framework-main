package com.jy.sample.biz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jy.sample.biz.entity.BizInterest;

@Repository
public interface BizInterestRepository
										extends
											JpaRepository<BizInterest, String> {

	Optional<List<BizInterest>> findByIssuYr(String issuYr);

}
