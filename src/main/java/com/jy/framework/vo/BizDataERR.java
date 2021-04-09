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
@Schema(description = "에러 응답 데이터부")
public class BizDataERR
						implements
							Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3280577083296252991L;

	@Schema(description = "에러 코드")
	private String errorCode;

	@Schema(description = "에러 매세지")
	private String errorMessage;

}
