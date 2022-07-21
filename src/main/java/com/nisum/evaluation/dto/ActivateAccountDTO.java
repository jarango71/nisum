package com.nisum.evaluation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ActivateAccountDTO {

	@Email
	private String email;

	@NotBlank
	private String token;
}
