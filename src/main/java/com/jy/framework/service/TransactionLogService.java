package com.jy.framework.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jy.app.exception.FrameworkException;
import com.jy.app.exception.FwErrorCode;
import com.jy.framework.entity.TransactionLog;
import com.jy.framework.repository.TransactionLogRepository;
import com.jy.framework.vo.FrameworkAttribute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionLogService {

	final private TransactionLogRepository transactionLogRepository;

	final private FrameworkUtils frameworkUtils;

	public List<TransactionLog> listAll() {
		return this.transactionLogRepository.findAll();
	}

	public List<TransactionLog> list(	String id,
										String guid,
										String trxDatetime,
										String requestType,
										String requestIo,
										String responseType,
										String interfaceId,
										String message) {

		return this.transactionLogRepository.findAll(this.searchWith(	id,
																		guid,
																		trxDatetime,
																		requestType,
																		requestIo,
																		responseType,
																		interfaceId,
																		message));
	}

	public TransactionLog logging(	String requestType,
									String requestIo,
									String responseType,
									Object messageObj) throws FrameworkException {

		TransactionLog log = null;
		String message;
		try {
			if (messageObj instanceof String) {
				message = (String) messageObj;
			}
			else {
				message = this.frameworkUtils.toJsonString(messageObj);

			}
			log = this.logging(	requestType,
								requestIo,
								responseType,
								message);

		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
			message = "unknown";
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new FrameworkException(	FwErrorCode.FL_INTERNAL_SERVER_ERROR,
											ExceptionUtils.getRootCauseMessage(e));
		}

		return log;

	}

	private TransactionLog logging(	String requestType,
									String requestIo,
									String responseType,
									String message) {

		log.debug(String.format("requestType[%s] "	+ "requestIo[%s] " + "responseType[%s] " + "messageObj[%s] ",
								requestType,
								requestIo,
								responseType,
								message));

		TransactionLog transactionLog = TransactionLog	.builder()
														.guid(FrameworkAttribute.get(FrameworkAttribute.guid))
														.trxDatetime(this.frameworkUtils.currentDate())
														.interfaceId(FrameworkAttribute.get(FrameworkAttribute.interfaceId))
														.requestType(requestType)
														.requestIo(requestIo)
														.responseType(responseType)
														.message(message)
														.build();

		return this.transactionLogRepository.save(transactionLog);
	}

	public Specification<TransactionLog> searchWith(String id,
													String guid,
													String trxDatetime,
													String requestType,
													String requestIo,
													String responseType,
													String interfaceId,
													String message) {
		return (Specification<TransactionLog>) ((	root,
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
										"guid",
										guid);
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"trxDatetime",
										trxDatetime);
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"requestType",
										requestType);
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"requestIo",
										requestIo);
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"requestIo",
										requestIo);
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"responseType",
										responseType);
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"interfaceId",
										interfaceId);
			FrameworkUtils.addPredicate(predicate,
										root,
										builder,
										"message",
										message);
			query.orderBy(builder.asc(root.get("trxDatetime")));
			return builder.and(predicate.toArray(new Predicate[0]));
		});
	}

}
