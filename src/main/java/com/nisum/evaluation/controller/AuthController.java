package com.nisum.evaluation.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.evaluation.dto.ActivateAccountDTO;
import com.nisum.evaluation.dto.MessageResponseDTO;
import com.nisum.evaluation.dto.UserLoginRequestDTO;
import com.nisum.evaluation.dto.UserProfileRequestDTO;
import com.nisum.evaluation.dto.UserRegisterRequestDTO;
import com.nisum.evaluation.dto.UserResponseDTO;
import com.nisum.evaluation.security.JwtToken;
import com.nisum.evaluation.service.UserService;

import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterRequestDTO userRegisterDTO) {
        if (userService.userExists(userRegisterDTO.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponseDTO("Email already registered"));
        }

        UserResponseDTO response = userService.register(userRegisterDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> authenticateUser(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) {
       
    	return ResponseEntity.ok(userService.login(userLoginRequestDTO));
	}

    @PostMapping("/activate/account")
    public ResponseEntity<MessageResponseDTO> activateAccount(@Valid @RequestBody ActivateAccountDTO activateAccountDTO) {
    	
    	userService.activateAccount(activateAccountDTO);

    	return ResponseEntity.ok(new MessageResponseDTO("The account has been activated successfully"));
	}

    @PutMapping("/user/profile")
    public ResponseEntity<UserResponseDTO> updateProfile(Principal principal, @Valid @RequestBody UserProfileRequestDTO userProfileDTO) {

    	return ResponseEntity.ok(userService.updateProfile(principal.getName(), userProfileDTO));
	}

}