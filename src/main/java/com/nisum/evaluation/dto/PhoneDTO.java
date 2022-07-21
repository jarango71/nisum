package com.nisum.evaluation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class PhoneDTO {
	@JsonIgnore
    private Long id;
    private String number;
    private String citycode;
    private String contrycode;
}
