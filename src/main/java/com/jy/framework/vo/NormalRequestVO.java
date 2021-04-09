package com.jy.framework.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "요청 전문")
public class NormalRequestVO
								implements
									Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8915201701995868785L;

	protected CommonData commonData;

	@Schema(example = "{\r\n" + "  \"tranDate\":\"20190101\"\r\n" + "}\r\n" + "", description = "요청 데이터부")
	protected Object bizDataIN;
}
