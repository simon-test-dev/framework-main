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
@Table(name = "BIZ_PRODUCTS")
public class BizProducts {
	@Id
	@Column(name = "ID_NO")
	private String idNo; // 순번

	@Column(name = "PRD_CD")
	private String prdCd; // 상품코드

	@Column(name = "PRD_NM")
	private String prdNm; // 상품명

}
