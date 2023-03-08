package com.restfulWebServices.in28minutes.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.restfulWebServices.in28minutes.entity.User;
import com.restfulWebServices.in28minutes.exception.UserNotFoundException;
import com.restfulWebServices.in28minutes.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController 
{
	
//	private MessageSource messageSource;
//	
//	public UserController(MessageSource messageSource)
//	{
//		this.messageSource = messageSource;
//	}
	private UserService userService;

	public UserController(UserService userService)
	{
		this.userService = userService;
	}
		
	@GetMapping("/users")
	public List<User> retrieveAllUsers()
	{
		return userService.findAll();
	}
	
//	http://localhost:8080/users
//	Entity Model
//	WebMVCLinkBuilder
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUserById(@PathVariable("id")  Integer id)
	{
		User user =  userService.findById(id);
		if(user == null) {
			throw new UserNotFoundException("id: "+id);
		}
		EntityModel<User> entityModel= EntityModel.of(user);
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers()); 
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user)
	{	
		User savedUser = userService.saveUser(user);
		
		///users/{id} -> newly created user
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedUser.getId())
							.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable("id")  Integer id)
	{
		userService.deleteById(id);		
	}
	

	
	
	//	@GetMapping("/hello-world-i18n")
//	public String helloWorldI18n() {
//		Locale locale = LocaleContextHolder.getLocale();
//		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
//	}
}
