package com.jy.framework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.jy.framework.entity.TransactionInterface;

@Repository
public interface TransactionInterfaceRepository
												extends
													JpaRepository<TransactionInterface, String>,
													JpaSpecificationExecutor<TransactionInterface> {

}
