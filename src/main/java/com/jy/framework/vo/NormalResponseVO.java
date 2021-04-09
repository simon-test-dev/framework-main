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
@Schema(description = "정상 응답 전문")
public class NormalResponseVO
								implements
									Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4622344551281669323L;

	private CommonData commonData;

	@Schema(description = "정상 응답 데이터부")
	private Object bizDataOUT;

}
