package com.jy.framework.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class InterfacePropertyPk
									implements
										Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6091324507154721013L;

	private String interfaceId;

	private String propertyKey;

}
