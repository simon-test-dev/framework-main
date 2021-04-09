package com.jy.framework.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.app.config.ServerProperties;
import com.jy.app.exception.FrameworkException;
import com.jy.app.exception.FwErrorCode;
import com.jy.framework.entity.TransactionInterface;
import com.jy.framework.repository.TransactionInterfaceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionInterfaceService {

	final private TransactionInterfaceRepository transactionInterfaceRepository;

	final private ServerProperties serverProperties;

	public List<TransactionInterface> listAll() {
		return this.transactionInterfaceRepository.findAll();
	}

	public List<TransactionInterface> list(	String id,
											String url,
											String method,
											String responseType) {

		return this.transactionInterfaceRepository.findAll(this.searchWith(	id,
																			url,
																			method,
																			responseType));
	}

	public TransactionInterface getInterface(String interfaceId) throws FrameworkException {
		if (StringUtils.isEmpty(interfaceId)) {
			throw new FrameworkException(FwErrorCode.FM_EMPTY_REQUEST_INTERFACE_ID);

		}
		return this.transactionInterfaceRepository	.findById(interfaceId)
													.orElseThrow(() -> new FrameworkException(FwErrorCode.FM_INVALID_REQUEST_INTERFACE_ID));
	}

	public void insertInitData() throws FrameworkException {
		List<TransactionInterface> initDatas = new ArrayList<>();

		initDatas.add(TransactionInterface	.builder()
											.id("bizA")
											.url(String.format(	"http://localhost:%s%s/biz/A",
																this.serverProperties.getPort(),
																this.serverProperties.getContextPath()))
											.method("POST")
											.responseType("OBJECT")
											.build());
		initDatas.add(TransactionInterface	.builder()
											.id("bizB")
											.url(String.format(	"http://localhost:%s%s/biz/B",
																this.serverProperties.getPort(),
																this.serverProperties.getContextPath()))
											.method("POST")
											.responseType("STRING")
											.build());
		initDatas.add(TransactionInterface	.builder()
											.id("bizC")
											.url(String.format(	"http://localhost:%s%s/biz/C",
																this.serverProperties.getPort(),
																this.serverProperties.getContextPath()))
											.method("GET")
											.responseType("OBJECT")
											.build());

		initDatas.add(TransactionInterface	.builder()
											.id("bizD")
											.responseType("OBJECT")
											.build());

		this.transactionInterfaceRepository.saveAll(initDatas);

	}

	public Specification<TransactionInterface> searchWith(	String id,
															String url,
															String method,
															String responseType) {
		return (Specification<TransactionInterface>) ((	root,
														query,
														builder) -> {
			List<Predicate> predicate = new ArrayList<>();
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"id",
										id);

			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"url",
										url);
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"method",
										method);
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"responseType",
										responseType);
			query.orderBy(builder.asc(root.get("id")));
			return builder.and(predicate.toArray(new Predicate[0]));
		});
	}

}
