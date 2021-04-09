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
@Table(name = "BIZ_INTEREST")
public class BizInterest {

	@Id
	@Column(name = "ISSU_YR")
	private String issuYr; // 발행년도

	@Column(name = "BOND_CCD")
	private String bondCcd; // 채권분류코드

	@Column(name = "ORCP_AMT")
	private String orcpAmt; // 원본금액

	@Column(name = "DVDN_AMT")
	private String dvdnAmt; // 배당금액

}
