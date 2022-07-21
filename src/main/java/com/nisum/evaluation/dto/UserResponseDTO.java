package com.nisum.evaluation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
	
	@NotBlank
	private String token;
	
	@NotBlank
	private UUID id;
	
	@NotBlank
	private LocalDateTime created;
	
	@NotBlank
	private LocalDateTime modified;
	
	@NotBlank
	@JsonProperty("last_login")
	private LocalDateTime lastLogin;
	
	@NotBlank
	@JsonProperty("isactive")
	private boolean active;
}