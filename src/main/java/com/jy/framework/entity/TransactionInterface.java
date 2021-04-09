package com.jy.framework.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FW_TRANSACTION_INTERFACE")
@Schema(description = "비지니스 호출에 필요한 정보를 프레임워크 인터페이스에 등록")
public class TransactionInterface
									implements
										Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1740720772848042030L;

	@Id
	@Column(name = "ID")
	@Schema(example = "bizA", description = "비지니스를 구분하기 위한 값")
	private String id;

	@Column(name = "URL")
	@Schema(example = "http://localhost:8081/biz/A", description = "호출해야 할 비지니스 URL")
	private String url;

	@Column(name = "METHOD")
	@Schema(allowableValues = {
		"GET",
		"POST"
	}, example = "POST", description = "호출해야 할 비지니스 METHOD (GET,POST 지원)")
	private String method;

	@Schema(allowableValues = {
		"OBJECT",
		"STRING"
	}, example = "OBJECT", description = "비지니스 응답이 문자열 인경우 : STRING, 그 외: OBJECT")
	@Column(name = "RESPONSE_TYPE")
	private String responseType;

}
