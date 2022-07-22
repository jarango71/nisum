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
import com.nisum.evaluation.security.JWTToken;
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
    	log.info("Inicio de registro de usuario");
    	
	    Pattern pattern = Pattern.compile(passwordPattern);
	
	    Matcher matcher = pattern.matcher(userRegisterDTO.getPassword());
	    boolean matches = matcher.matches();
	    if (!matches) {
	    	throw new BadRequestException("La contraseña no cumple con el formato");
	    }
    
        User user = userRegisterMapper.toEntity(userRegisterDTO);
        user.setActive(false);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        userRepository.save(user);
        
        log.info("Registro de usuario exitoso");
        return userResponseMapper.toDto(user);
    }

	@Override
	public JWTToken login(UserLoginRequestDTO userLoginDTO) {
		log.info("Inicio de autenticacion");
		
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtProvider.generateJwtToken(authentication);

        User user = userRepository.findByEmail(userLoginDTO.getEmail()).get();
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        log.info("Autenticacion exitosa");
		return new JWTToken(jwt);
	}

	@Override
	public void activateAccount(ActivateAccountDTO activateAccountDTO) {
		log.info("Activando cuenta de usuario");
		
		Optional<User> userOpt = userRepository.findByEmailAndToken(activateAccountDTO.getEmail(), activateAccountDTO.getToken());
		if (!userOpt.isPresent()) {
			log.error("Activacion invalidad - usuario y token no encontrado");
			throw new RuntimeException("Activacion invalidad");
		}
		User user = userOpt.get();
		user.setActive(true);
		user.setModified(LocalDateTime.now());
		userRepository.save(user);
		
		log.info("Cuenta activada satisfactoriamente");
	}
	
	@Override
	public UserResponseDTO updateProfile(String email, UserProfileRequestDTO userProfileDTO) {
		log.info("Actualizando perfil");
		
		User user = userRepository.findByEmail(email).get();

		user.setName(userProfileDTO.getName());
		user.setModified(LocalDateTime.now());
		userRepository.save(user);
		
		log.info("Actualizacion del perfil exitosa");
		return userResponseMapper.toDto(user);
	}
	
    @Override
	public boolean userExists(String email) {
    	return userRepository.findByEmail(email).isPresent();
    }
    
}
