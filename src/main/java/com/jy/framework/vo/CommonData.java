package com.jy.framework.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "공통부")
public class CommonData
						implements
							Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4182467701951374331L;

	@NotNull
	@Schema(example = "02016101398487146504890949563021", description = "거래 Unique 한 키 (클라이언트에서 생성)")
	private String guid;

	@NotNull
	@Size(min = 17, max = 17, message = "YYYYMMDDHHmmssmmm")
	@Schema(example = "20210321122839243", description = "거래일자/시간")
	private String trxDatetime;

	@NotNull
	@Schema(example = "S",
			allowableValues = {
				"S",
				"R"
			},
			description = "요청 타입 [S:요청 , R:응답]")
	private String requestType;

	@Schema(example = " ",
			allowableValues = {
				"NM",
				"ER"
			},
			description = "요청 타입 [NM:정상 , ER:오류]")
	private String responseType;

	@Schema(example = "bizA", description = "인터페이스 아이디")
	private String interfaceId;

}
