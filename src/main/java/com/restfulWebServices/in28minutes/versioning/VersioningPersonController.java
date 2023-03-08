package com.restfulWebServices.in28minutes.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController 
{
	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson()
	{
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOfPerson()
	{
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	@GetMapping(path="/person",params="version1")
	public PersonV1 getFirstVersionOfPersonRequestParam()
	{
		return new PersonV1("Bob Charlie") ;
	}
	
	@GetMapping(path="/person",params="version2")
	public PersonV2 getSecondVersionOfPersonRequestParam()
	{
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	@GetMapping(path="/person/header",headers ="X-API-VERSION=1")
	public PersonV1 getFirstVersionOfPersonRequestHeader()
	{
		return new PersonV1("Bob Charlie") ;
	}
	
}
