package com.restfulWebServices.in28minutes.filtering;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController
{
	@GetMapping("/filtering")
	public SomeBean filtering()
	{
		return new SomeBean("value1","value2","value3");
	}
	
	@GetMapping("/filtering/jackson")
	public MappingJacksonValue filteringJacksonValue() 
	{
		SomeBean someBean = new SomeBean("value1","value2","value3");
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter ); 
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
}
