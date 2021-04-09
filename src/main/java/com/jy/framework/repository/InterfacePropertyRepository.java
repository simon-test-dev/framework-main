package com.jy.framework.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.jy.framework.entity.InterfaceProperty;
import com.jy.framework.entity.InterfacePropertyPk;

@Repository
public interface InterfacePropertyRepository
												extends
													JpaRepository<InterfaceProperty, InterfacePropertyPk>,
													JpaSpecificationExecutor<InterfaceProperty> {

	Optional<InterfaceProperty> findByInterfaceIdAndPropertyKey(String interfaceId,
																String propertykey);

}
