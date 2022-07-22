package com.nisum.evaluation.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nisum.evaluation.domain.User;
import com.nisum.evaluation.dto.ActivateAccountDTO;
import com.nisum.evaluation.dto.UserLoginRequestDTO;
import com.nisum.evaluation.dto.UserProfileRequestDTO;
import com.nisum.evaluation.dto.UserRegisterRequestDTO;
import com.nisum.evaluation.dto.UserResponseDTO;
import com.nisum.evaluation.exception.BadRequestException;
import com.nisum.evaluation.mapper.UserRegisterMapper;
import com.nisum.evaluation.mapper.UserResponseMapper;
import com.nisum.evaluation.repository.UserRepository;
import com.nisum.evaluation.security.JwtToken;
import com.nisum.evaluation.security.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    
    private final AuthenticationManager authenticationManager;
    
    private final JwtProvider jwtProvider;
    
    private final UserRegisterMapper userRegisterMapper;
    
    private final UserResponseMapper userResponseMapper;

    @Value("${user.password.regex}")
    private String passwordPattern;

    
    @Override
	public UserResponseDTO register(UserRegisterRequestDTO userRegisterDTO) {
    	log.info("Start of user registration");
    	
	    Pattern pattern = Pattern.compile(passwordPattern);
	
	    Matcher matcher = pattern.matcher(userRegisterDTO.getPassword());
	    boolean matches = matcher.matches();
	    if (!matches) {
	    	throw new BadRequestException("The password does not comply with the format");
	    }
    
        User user = userRegisterMapper.toEntity(userRegisterDTO);
        user.setActive(false);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        userRepository.save(user);
        
        log.info("Successful user registration");
        return userResponseMapper.toDto(user);
    }

	@Override
	public JwtToken login(UserLoginRequestDTO userLoginDTO) {
		log.info("Authentication start");
		
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtProvider.generateJwtToken(authentication);

        User user = userRepository.findByEmail(userLoginDTO.getEmail()).get();
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        log.info("Successful authentication");
		return new JwtToken(jwt);
	}

	@Override
	public void activateAccount(ActivateAccountDTO activateAccountDTO) {
		log.info("Activating user account");
		
		Optional<User> userOpt = userRepository.findByEmailAndToken(activateAccountDTO.getEmail(), activateAccountDTO.getToken());
		if (!userOpt.isPresent()) {
			log.error("Invalid activation - User and token not found");
			throw new RuntimeException("Invalid activation");
		}
		User user = userOpt.get();
		user.setActive(true);
		user.setModified(LocalDateTime.now());
		userRepository.save(user);
		
		log.info("Activated user account");
	}
	
	@Override
	public UserResponseDTO updateProfile(String email, UserProfileRequestDTO userProfileDTO) {
		log.info("Updating profile");
		
		User user = userRepository.findByEmail(email).get();

		user.setName(userProfileDTO.getName());
		user.setModified(LocalDateTime.now());
		userRepository.save(user);
		
		log.info("Successful profile update");
		return userResponseMapper.toDto(user);
	}
	
    @Override
	public boolean userExists(String email) {
    	return userRepository.findByEmail(email).isPresent();
    }
    
}
