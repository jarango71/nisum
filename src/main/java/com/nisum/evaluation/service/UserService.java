package com.nisum.evaluation.service;

import com.nisum.evaluation.dto.UserRegisterDTO;

public interface UserService {

	void register(UserRegisterDTO userRegisterDTO);

	boolean userExists(String email);

}