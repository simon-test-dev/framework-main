package com.jy.framework.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@IdClass(InterfacePropertyPk.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FW_PROPERTY")
public class InterfaceProperty
								implements
									Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5423487771720029914L;

	@Id
	@Column(name = "INTERFACE_ID")
	private String interfaceId;

	@Column(name = "PROPERTY_KEY")
	private String propertyKey;

	@Column(name = "PROPERTY_VALUE")
	private String propertyValue;

}
