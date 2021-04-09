package com.jy.framework.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "에러 응답 전문")
public class ErrorResponseVO
								implements
									Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5212437056774586780L;

	private CommonData commonData;

	@Schema(description = "에러 응답 데이터부")
	private BizDataERR bizDataERR;
}
