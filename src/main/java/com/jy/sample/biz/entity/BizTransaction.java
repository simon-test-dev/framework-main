package com.jy.sample.biz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BIZ_TRANSACTION")
public class BizTransaction {
	@Id
	@Column(name = "TRAN_ID")
	private String tranId; // 거래 고유 번호

	@Column(name = "TRAN_DATE")
	private String tranDate; // 20190101

	@Column(name = "TRAN_TIME")
	private String tranTime; // 010101

	@Column(name = "INOUT_TYPE")
	private String inoutType; // 입금/출금

	@Column(name = "PRINT_CONTENT")
	private String printContent; // 스타벅스

	@Column(name = "TRAN_AMT")
	private String tranAmt; // 10000

}
