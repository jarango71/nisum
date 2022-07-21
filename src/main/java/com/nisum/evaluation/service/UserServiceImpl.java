package com.nisum.evaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nisum.evaluation.domain.User;
import com.nisum.evaluation.dto.UserRegisterDTO;
import com.nisum.evaluation.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Override
	public void register(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setName(userRegisterDTO.getName());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(userRegisterDTO.getPassword());
        user.setActive(true);
        user.setCreated(null);
        user.setModified(null);
        user.setToken(null);
        //user.setPhones(userRegisterDTO.getPhones());
        userRepository.save(user);
    }

    @Override
	public boolean userExists(String email) {
    	return userRepository.findByEmail(email).isPresent();
    }
}
