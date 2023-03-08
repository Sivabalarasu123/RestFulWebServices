package com.restfulWebServices.in28minutes.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

import com.restfulWebServices.in28minutes.entity.Post;
import com.restfulWebServices.in28minutes.entity.User;
import com.restfulWebServices.in28minutes.exception.UserNotFoundException;
import com.restfulWebServices.in28minutes.repository.PostRepository;
import com.restfulWebServices.in28minutes.repository.UserRepository;
import com.restfulWebServices.in28minutes.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserResource 
{	
	private UserRepository userRepository;
	
	private PostRepository postRepository;

	public UserResource(UserRepository userRepository,PostRepository postRepository)
	{
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
		
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers()
	{
		return userRepository.findAll();
	}
	
//	http://localhost:8080/users
//	Entity Model
//	WebMVCLinkBuilder
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUserById(@PathVariable("id")  Integer id)
	{
		Optional<User> user =  userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id: "+id);
		}
		EntityModel<User> entityModel= EntityModel.of(user.get());
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers()); 
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user)
	{	
		User savedUser = userRepository.save(user);
		
		///users/{id} -> newly created user
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedUser.getId())
							.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable("id") Integer id)
	{
		userRepository.deleteById(id);		
	}
	

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostForUser(@PathVariable("id") Integer id)
	{
		Optional<User> user =  userRepository.findById(id);
		if(user.isEmpty()) 
			throw new UserNotFoundException("id: "+id);
		
		return user.get().getPosts();
	}
	
	
}
