package com.nisum.evaluation.service;

import com.nisum.evaluation.dto.UserRegisterRequestDTO;
import com.nisum.evaluation.dto.UserResponseDTO;

public interface UserService {

	UserResponseDTO register(UserRegisterRequestDTO userRegisterDTO);

	boolean userExists(String email);

}