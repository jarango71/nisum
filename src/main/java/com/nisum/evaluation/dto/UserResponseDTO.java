package com.nisum.evaluation.dto;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
	private String token;
	private String id;
	private Date created;
	private Date modified;
	@JsonProperty("last_login")
	private Date lastLogin;
	@JsonProperty("isactive")
	private boolean active;
}