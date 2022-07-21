package com.nisum.evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nisum.evaluation.domain.User;
import com.nisum.evaluation.dto.UserRegisterDTO;
import com.nisum.evaluation.payload.request.LoginRequest;
import com.nisum.evaluation.payload.request.RegisterRequest;
import com.nisum.evaluation.payload.response.JwtResponse;
import com.nisum.evaluation.payload.response.MessageResponse;
import com.nisum.evaluation.security.JwtProvider;
import com.nisum.evaluation.security.UserPrinciple;
import com.nisum.evaluation.service.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

        return ResponseEntity.ok(null);
        
        /*
		new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getEmail(),
        userDetails.ge(),
        userDetails.getLastname(),
        userDetails.getTelephon(),
        roles)*/
    
	}

    /*	private String token;
	private String type;
	private UUID id;
	private Date created;
	private Date modified;
	@JsonProperty("last_login")
	private Date lastLogin;
	@JsonProperty("isactive")
	private boolean active;
*/
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        if (userService.userExists(userRegisterDTO.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("El correo ya registrado"));
        }

        userService.register(userRegisterDTO);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}