package com.nisum.evaluation.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class PhoneDTO {
	
	@JsonIgnore
    private Long id;
	
	@NotBlank
    private String number;
	
	@NotBlank
    private String citycode;
	
	@NotBlank
    private String contrycode;
}
