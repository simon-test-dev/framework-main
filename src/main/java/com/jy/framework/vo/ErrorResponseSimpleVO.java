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
@Schema(description = "내부에서 사용되는 에러 응답")
public class ErrorResponseSimpleVO
									implements
										Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4692755951435339290L;

	@Schema(description = "에러메세지")
	private String code;

	@Schema(description = "에러메세지")
	private String message;

}
