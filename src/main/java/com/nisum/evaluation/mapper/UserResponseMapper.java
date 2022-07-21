package com.nisum.evaluation.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;

import com.nisum.evaluation.domain.User;
import com.nisum.evaluation.dto.UserResponseDTO;

@Mapper(componentModel = "spring")
public interface UserResponseMapper extends EntityMapper<UserResponseDTO, User> {
	
	default User fromId(UUID id) {
		if (id == null) 
			return null;
		
		User user = new User();
		user.setId(id);
		return user;
	} 
	
}
