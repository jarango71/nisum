package com.nisum.evaluation.service;

import com.nisum.evaluation.dto.ActivateAccountDTO;
import com.nisum.evaluation.dto.UserLoginRequestDTO;
import com.nisum.evaluation.dto.UserProfileRequestDTO;
import com.nisum.evaluation.dto.UserRegisterRequestDTO;
import com.nisum.evaluation.dto.UserResponseDTO;
import com.nisum.evaluation.security.JwtToken;

public interface UserService {

	UserResponseDTO register(UserRegisterRequestDTO userRegisterDTO);
	
	JwtToken login(UserLoginRequestDTO userLoginDTO);
	
	void activateAccount(ActivateAccountDTO activateAccountDTO);
	
	UserResponseDTO updateProfile(String email, UserProfileRequestDTO userProfileDTO);
	
	boolean userExists(String email);
	
}