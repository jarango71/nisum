package com.nisum.evaluation.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserRegisterDTO {
	private String name;
	@Email
	private String email;
	@Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
	private String password;
	private Set<PhoneDTO> phones;
}
