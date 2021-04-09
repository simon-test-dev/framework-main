package com.jy.framework.service;

import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.jy.app.exception.FrameworkException;
import com.jy.app.exception.FwErrorCode;
import com.jy.framework.entity.InterfaceProperty;
import com.jy.framework.entity.TransactionInterface;
import com.jy.framework.repository.InterfacePropertyRepository;
import com.jy.framework.repository.TransactionInterfaceRepository;
import com.jy.framework.vo.CommonData;
import com.jy.framework.vo.FrameworkAttribute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FrameworkService {

	final private TransactionInterfaceRepository transactionInterfaceRepository;

	final private InterfacePropertyRepository interfacePropertyRepository;

	final private FrameworkUtils frameworkUtils;

	public Object callBiz(	TransactionInterface transactionInterface,
							Object bizDataIN) throws FrameworkException {

		// transaction.mode : 데이터소스 확장성 테스트를 위한 속성
		String transactionMode = this.getInterfacePropertyValue(transactionInterface.getId(),
																"transaction.mode",
																"rest");

		final Object response;
		switch (transactionMode) {
			case "jdbc":
				response = this.callBizByJdbc(	transactionInterface,
												bizDataIN);
				break;

			case "rest":

			default:
				response = this.callBizByRest(	transactionInterface,
												bizDataIN);
				break;
		}
		return response;

	}

	private Object callBizByRest(	TransactionInterface transactionInterface,
									Object bizDataIN) {
		try {
			RestTemplate restTemplate = new RestTemplate();

			HttpMethod method = HttpMethod.valueOf(transactionInterface.getMethod()); // POST;
			String url;
			HttpEntity<Object> requestEntity;
			switch (method) {
				case POST:
					url = transactionInterface.getUrl();
					requestEntity = new HttpEntity<Object>(bizDataIN);
					break;
				case GET:
					MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

					if (bizDataIN instanceof Map == false) {
						throw new FrameworkException(	FwErrorCode.FL_INVALID_BIZ_REQUEST,
														String.format(	"json 형식 지원, 요청데이터: %s",
																		bizDataIN));
					}
					((Map<?, ?>) bizDataIN).forEach((	key,
														value) -> {
						params.add(	String.valueOf(key),
									String.valueOf(value));
					});

					UriComponents builder = UriComponentsBuilder.fromHttpUrl(transactionInterface.getUrl())
																.queryParams(params)
																.build();
					url = builder.toString();
					requestEntity = new HttpEntity<Object>(null);
					break;
				default:
					throw new FrameworkException(	FwErrorCode.FL_NOT_SUPPORT_METHOD,
													method.toString());
			}

			Class<?> responseType = StringUtils.equalsIgnoreCase(	transactionInterface.getResponseType(),
																	"STRING")	? String.class
																				: Object.class;

			// Object
			log.debug("call uri :: " + url);

			ResponseEntity<?> responseEntity = restTemplate.exchange(	url,
																		method,
																		requestEntity,
																		responseType);

			HttpStatus responseStatus = responseEntity.getStatusCode();
			Object response;
			switch (responseStatus) {
				case OK:
					response = responseEntity.getBody();
					break;

				default:
					Map<String, String> errorResponse = (Map) responseEntity.getBody();
					throw new FrameworkException(	FwErrorCode.BZ_INTERNAL_SERVER_ERROR,
													errorResponse.get("message"));
			}

			// Object response = restTemplate.getForObject(url, Object.class);

			System.out.println("response::" + response);

			return response;
		}
		catch (HttpServerErrorException e) {
			e.printStackTrace();
			throw new FrameworkException(	FwErrorCode.BZ_INTERNAL_SERVER_ERROR,
											e.getMessage());
		}
		catch (RestClientException e) {
			e.printStackTrace();
			throw new FrameworkException(	FwErrorCode.FL_INVALID_BIZ_RESPONSE,
											"json 형식 지원, " + e.getMessage());

		}
		catch (FrameworkException e) {
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new FrameworkException(	FwErrorCode.FL_INTERNAL_SERVER_ERROR,
											e.getMessage());
		}

	}

	private Object callBizByJdbc(	TransactionInterface transactionInterface,
									Object bizDataIN) {
		try {

			final String interfaceId = transactionInterface.getId();
			final String sql;

			// bizDataIN 에서 쿼리 가져오기
			if (bizDataIN instanceof Map && ((Map) bizDataIN).get("sql") != null) {
				sql = String.valueOf(((Map) bizDataIN).get("sql"));
			}
			else {
				throw new FrameworkException(	FwErrorCode.BZ_INVALID_REQUEST,
												"sql 누락");
			}

			// mandatory
			String driverClassName = this.getInterfacePropertyValue(interfaceId,
																	"datasource.driver.class.name",
																	null);
			if (Objects.isNull(driverClassName))
				throw new FrameworkException(	FwErrorCode.FL_INVALID_DATASOURCE_SETTING,
												interfaceId,
												"datasource.driver.class.name",
												driverClassName);

			String url = this.getInterfacePropertyValue(interfaceId,
														"datasource.url",
														null);
			if (Objects.isNull(url))
				throw new FrameworkException(	FwErrorCode.FL_INVALID_DATASOURCE_SETTING,
												interfaceId,
												"datasource.url",
												url);

			String username = this.getInterfacePropertyValue(	interfaceId,
																"datasource.username",
																null);
			if (Objects.isNull(username))
				throw new FrameworkException(	FwErrorCode.FL_INVALID_DATASOURCE_SETTING,
												interfaceId,
												"datasource.username",
												username);
			String password = this.getInterfacePropertyValue(	interfaceId,
																"datasource.password",
																null);
			if (Objects.isNull(password))
				throw new FrameworkException(	FwErrorCode.FL_INVALID_DATASOURCE_SETTING,
												interfaceId,
												"datasource.password",
												password);

			// optional
			String maxIdle = this.getInterfacePropertyValue(interfaceId,
															"datasource.maxIdle",
															null);
			String maxWaitMillis = this.getInterfacePropertyValue(	interfaceId,
																	"datasource.maxWaitMillis",
																	null);
			String readOnly = this.getInterfacePropertyValue(	interfaceId,
																"datasource.readonly",
																null);

			final DataSource dataSource = this.createDataSource(driverClassName,
																url,
																username,
																password,
																maxIdle,
																maxWaitMillis,
																readOnly);

			Object response = this.executeQuery(dataSource,
												sql);
			return response;
		}
		catch (FrameworkException e) {
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new FrameworkException(	FwErrorCode.FL_INTERNAL_SERVER_ERROR,
											e.getMessage());
		}
	}

	public TransactionInterface getInterface(String interfaceId) throws FrameworkException {
		if (StringUtils.isEmpty(interfaceId)) {
			throw new FrameworkException(FwErrorCode.FL_EMPTY_REQUEST_INTERFACE_ID);

		}
		return this.transactionInterfaceRepository	.findById(interfaceId)
													.orElseThrow(() -> new FrameworkException(	FwErrorCode.FL_INVALID_REQUEST_INTERFACE_ID,
																								interfaceId));
	}

	public void setFrameworkAttribute(CommonData commonData) {
		FrameworkAttribute.set(	FrameworkAttribute.guid,
								commonData.getGuid());
		FrameworkAttribute.set(	FrameworkAttribute.trxDatetime,
								commonData.getTrxDatetime());
		FrameworkAttribute.set(	FrameworkAttribute.requestType,
								commonData.getRequestType());
		FrameworkAttribute.set(	FrameworkAttribute.responseType,
								commonData.getResponseType());
		FrameworkAttribute.set(	FrameworkAttribute.interfaceId,
								commonData.getInterfaceId());

	}

	public CommonData generateResponseCommonData(	String requestType,
													String responseType) {
		return CommonData	.builder()
							.guid(FrameworkAttribute.get(FrameworkAttribute.guid))
							.trxDatetime(this.frameworkUtils.currentDate())
							.requestType(requestType)
							.responseType(responseType)
							.interfaceId(FrameworkAttribute.get(FrameworkAttribute.interfaceId))
							.build();
	}

	private DataSource createDataSource(String driverClassName,
										String url,
										String username,
										String password,
										String maxIdle,
										String maxWaitMillis,
										String readOnly) {
		BasicDataSource dataSource = new BasicDataSource();

		// mandatory
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		// optional
		if (Objects.nonNull(maxIdle))
			dataSource.setMaxIdle(Integer.parseInt(maxIdle));
		if (Objects.nonNull(maxWaitMillis))
			dataSource.setMaxIdle(Integer.parseInt(maxWaitMillis));
		if (Objects.nonNull(readOnly))
			dataSource.setDefaultReadOnly(Boolean.valueOf(readOnly));
		return dataSource;
	}

	public Object executeQuery(	DataSource dataSource,
								String sql) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object response = jdbcTemplate.queryForList(sql);

		return response;
	}

	private String getInterfacePropertyValue(	String interfaceId,
												String propertykey,
												String defaultValue) {

		InterfaceProperty property = this.interfacePropertyRepository	.findByInterfaceIdAndPropertyKey(	interfaceId,
																											propertykey)
																		.orElse(null);

		if (Objects.isNull(property)) {
			return defaultValue;
		}
		else {
			return property.getPropertyValue();
		}

	}
}
