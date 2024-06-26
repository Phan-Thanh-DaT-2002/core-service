package com.neo.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

 

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterCodeDTO {
	private String key;
	private String value;
	private String id;
	private String name;
	private String code;
	private String desc;
	private String unit;
	private String type;

	public MasterCodeDTO(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

}
