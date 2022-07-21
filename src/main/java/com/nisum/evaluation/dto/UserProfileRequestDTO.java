package com.nisum.evaluation.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserProfileRequestDTO {
	
	@NotBlank
	private String name;

}

