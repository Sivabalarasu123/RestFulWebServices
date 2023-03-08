package com.restfulWebServices.in28minutes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.restfulWebServices.in28minutes.entity.User;

@Component
public class UserService 
{
	private static List<User> users = new ArrayList<>();
	
	private static int usersCount = 0;
	
	static 
	{
		users.add(new User(++usersCount,"Adam",LocalDate.now().minusYears(30)));
		users.add(new User(++usersCount,"Eve",LocalDate.now().minusYears(25)));
		users.add(new User(++usersCount,"Jim",LocalDate.now().minusYears(20)));	
	}
	
	public List<User> findAll()
	{
		return users;
	}
	
	public User findById(Integer id)
	{
		Predicate<? super User> predicate = (user) -> user.getId().equals(id);
		
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public User saveUser(User user)
	{
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
	
	public void deleteById(Integer id)
	{
		Predicate<? super User> predicate = (user) -> user.getId().equals(id);
		users.remove(predicate);
		
	}
}

