package com.jy.framework.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FrameworkUtils {

	private SimpleDateFormat sdf;

	private ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void init() {
		this.sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		this.objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
									true);
	}

	public String currentDate() {
		return this.sdf.format(new Date());
	}

	public String toJsonString(Object obj) throws JsonProcessingException {
		return this.objectMapper.writeValueAsString(obj);

	}

	public String generateGuid() {
		return RandomStringUtils.randomNumeric(32);

	}

	public static void addPredicate(final List<Predicate> predicate,
									final Root<?> root,
									final CriteriaBuilder builder,
									String key,
									String value) {
		if (Objects.nonNull(value)) {
			predicate.add(builder.like(	root.get(key),
										"%" + value + "%"));

		}
	}
}
