package com.jy.framework.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "FW_TRANSACTION_LOG")
@Schema(description = "한 거래에 대하여 SI, SO, RI, RO 네 단계로 구분하여 로깅\n"
						+ "SI : Client -> Framework\n"
						+ "SO : Framework -> Business\n"
						+ "RI : Business -> Framework\n"
						+ "RO : Framework -> Client")
public class TransactionLog
							implements
								Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1740720772848042030L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Schema(example = "0338edd1-7356-4e6c-a1e0-c3c8ff5f8ec5", description = "로그 아이디(자동 생성)")
	private String id;

	@Column(name = "GUID", length = 32)
	@Schema(example = "02016101398487146504890949563021", description = "거래 Unique 한 키 (클라이언트에서 생성)")
	private String guid;

	@Size(min = 17, max = 17, message = "YYYYMMDDHHmmssmmm")
	@Schema(example = "20210321122839243", description = "로그 시간")
	@Column(name = "TRX_DATETIME", length = 17)
	private String trxDatetime;

	@Schema(example = "S",
			allowableValues = {
				"S",
				"R"
			},
			description = "요청 타입 [S:요청 , R:응답]")
	@Column(name = "REQUEST_TYPE", length = 1)
	@Check(constraints = "REQUEST_TYPE='S' OR REQUEST_TYPE='R'")
	private String requestType;

	@Schema(example = " ",
			allowableValues = {
				"I",
				"O"
			},
			description = "Framework 기준 거래 IN/OUT [I:프레임워크로 들어오는 거래(Inbound), O:프레임워크로 들어오는 거래(OutBound)]")
	@Column(name = "REQUEST_IO", length = 1)
	@Check(constraints = "requestIo='I' OR requestIo='O'")
	private String requestIo;

	@Schema(example = " ",
			allowableValues = {
				"NM",
				"ER"
			},
			description = "거래 정상 여부 [NM:정상 , ER:오류]")
	@Column(name = "RESPONSE_TYPE", length = 2)
	private String responseType;

	@Schema(example = "bizA", description = "인터페이스 아이디")
	@Column(name = "INTERFACE_ID")
	private String interfaceId;

	@Schema(example = "{\"commonData\":{}, \"bizData\":{}}", description = "거래  전문")
	@Column(name = "MESSAGE", length = 4096)
	private String message;
}
