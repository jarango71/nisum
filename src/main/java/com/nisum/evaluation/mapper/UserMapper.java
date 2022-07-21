package com.nisum.evaluation.mapper;

import org.mapstruct.Mapper;

import com.nisum.evaluation.domain.User;
import com.nisum.evaluation.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {}
