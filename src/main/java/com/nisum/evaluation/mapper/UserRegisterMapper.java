package com.nisum.evaluation.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nisum.evaluation.domain.User;
import com.nisum.evaluation.dto.UserRegisterRequestDTO;

@Mapper(componentModel = "spring", uses = {PhoneMapper.class})
public abstract class UserRegisterMapper implements EntityMapper<UserRegisterRequestDTO, User> {
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
	public abstract User toEntity(UserRegisterRequestDTO dto);
	
	public User fromId(UUID id) {
		if (id == null) 
			return null;
		
		User user = new User();
		user.setId(id);
		return user;
	} 
	
}
