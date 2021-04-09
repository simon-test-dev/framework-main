package com.jy.framework.vo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

public enum FrameworkAttribute {

	guid,
	trxDatetime,
	requestType,
	responseType,
	interfaceId;

	FrameworkAttribute() {
	}

	public static void set(	FrameworkAttribute frameworkAttribute,
							String value) {
		MDC.put(frameworkAttribute.name(),
				value);

	}

	public static String get(FrameworkAttribute frameworkAttribute) {

		String value = MDC.get(frameworkAttribute.name());

		return StringUtils.isEmpty(value)	? "unknown"
											: value;

	}

}
