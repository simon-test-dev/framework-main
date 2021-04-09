package com.jy.framework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.app.exception.FrameworkException;
import com.jy.framework.entity.InterfaceProperty;
import com.jy.framework.repository.InterfacePropertyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InterfacePropertyService {

	final private InterfacePropertyRepository interfacePropertyRepository;

	public List<InterfaceProperty> list(String interfaceId,
										String propertyKey,
										String propertyValue) {
		return this.interfacePropertyRepository.findAll();
	}

	public Specification<InterfaceProperty> searchWith(	String interfaceId,
														String propertyKey,
														String propertyValue) {
		return (Specification<InterfaceProperty>) ((root,
													query,
													builder) -> {
			List<Predicate> predicate = new ArrayList<>();
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"interfaceId",
										interfaceId);

			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"propertyKey",
										propertyKey);
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"propertyValue",
										propertyValue);

			query.orderBy(	builder.asc(root.get("interfaceId")),
							builder.asc(root.get("propertyKey")));

			return builder.and(predicate.toArray(new Predicate[0]));
		});
	}

	public void insertInitData() throws FrameworkException {

		Map<String, String> prop = new HashMap<String, String>();
		prop.put(	"transaction.mode",
					"jdbc");
		prop.put(	"datasource.driver.class.name",
					"org.h2.Driver");
		prop.put(	"datasource.url",
					"jdbc:h2:mem:fw;");
		prop.put(	"datasource.username",
					"fw");
		prop.put(	"datasource.password",
					"");
		prop.put(	"datasource.maxIdle",
					"8");
		prop.put(	"datasource.maxWaitMillis",
					"10000");
		prop.put(	"datasource.readonly",
					"true");

		List<InterfaceProperty> initDatas = new ArrayList<>();
		prop.forEach((	key,
						value) -> {
			initDatas.add(InterfaceProperty	.builder()
											.interfaceId("bizD")
											.propertyKey(key)
											.propertyValue(value)
											.build());
		});

		this.interfacePropertyRepository.saveAll(initDatas);

	}

	public InterfaceProperty upsert(InterfaceProperty interfaceProperty) {
		return this.interfacePropertyRepository.save(interfaceProperty);
	}

}
