package com.jy.sample.biz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jy.sample.biz.entity.BizProducts;

@Repository
public interface BizProductsRepository
										extends
											JpaRepository<BizProducts, String> {

	Optional<List<BizProducts>> findByPrdNmContains(String prdNm);

}
