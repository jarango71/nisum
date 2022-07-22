package com.nisum.evaluation.controller

import java.security.Principal

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import com.nisum.evaluation.controller.AuthController
import com.nisum.evaluation.dto.ActivateAccountDTO
import com.nisum.evaluation.dto.UserLoginRequestDTO
import com.nisum.evaluation.dto.UserProfileRequestDTO
import com.nisum.evaluation.dto.UserRegisterRequestDTO
import com.nisum.evaluation.dto.UserResponseDTO
import com.nisum.evaluation.security.JWTToken
import com.nisum.evaluation.service.UserService

import spock.lang.Specification
import spock.lang.Unroll

class AuthControllerSpec extends Specification {
	
	private AuthController authController
	private UserService userService = Stub()
	
	def setup() {
		authController = new AuthController(userService)
	}
	
	@Unroll
	def "se registra un usuario"() {
		given: "dada una peticion de registro de usuario"
		def request = new UserRegisterRequestDTO()
		userService.register(_) >> new UserResponseDTO()
		userService.userExists(_) >> exists
		when: "se invoca el endpoint de registro"
		def response = authController.registerUser(request)
		then: "se espera un el resultado, #statusCodeExpected"
		response
		response.statusCode == statusCodeExpected
		where: 
		statusCodeExpected << [HttpStatus.OK, HttpStatus.BAD_REQUEST]
		exists << [false, true]
	}

	def "se autentica un usuario"() {
		given: "dada una peticion de autenticacion"
		def request = new UserLoginRequestDTO()
		userService.login(_) >> new JWTToken('token')
		when: "se invoca el endpoint de autenticacion"
		def response = authController.authenticateUser(request)
		then: "se espera auth exitosa"
		response
		response.statusCode == HttpStatus.OK
		response.body.token == 'token'
	}

	def "se activa la cuenta del usuario"() {
		given: "dada una peticion de activacion de cuenta"
		def request = new ActivateAccountDTO()
		when: "se invoca el endpoint de activacion de cuenta de usuario"
		def response = authController.activateAccount(request)
		then: "La cuenta se ha activado con exito"
		response
		response.statusCode == HttpStatus.OK
		response.body.message == 'La cuenta se ha activado con exito'
	}
	
	def "se modifica el perfil del usuario"() {
		given: "dada una peticion de modificacion del perfil del usuario"
		def request = new UserProfileRequestDTO()
		userService.updateProfile(_, _) >> new UserResponseDTO()
		when: "se invoca el endpoint de modificacion de perfil"
		Principal principal = {'aa@bb.com'}
		def response = authController.updateProfile(principal, request)
		then: "La cuenta se ha activado con exito"
		response
		response.statusCode == HttpStatus.OK
		response.body
	}
}