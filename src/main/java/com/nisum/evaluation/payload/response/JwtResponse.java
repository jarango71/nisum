package com.nisum.evaluation.payload.response;

import lombok.*;

import java.sql.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String token;
	private String type;
	private UUID id;
	private Date created;
	private Date modified;
	@JsonProperty("last_login")
	private Date lastLogin;
	@JsonProperty("isactive")
	private boolean active;
}