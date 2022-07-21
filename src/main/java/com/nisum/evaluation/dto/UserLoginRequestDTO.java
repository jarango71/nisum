package com.nisum.evaluation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UserLoginRequestDTO", description = "")
public class UserLoginRequestDTO {
	
	@Email
	@NotBlank
	@Schema(description = "", required = true, example = "user@gmail.com")
	private String email;
	
	@NotBlank
	private String password;
}

