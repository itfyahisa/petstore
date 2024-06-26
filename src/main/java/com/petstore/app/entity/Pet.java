package com.petstore.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

	private Long id;
	private Long categoryId;
	private String name;
	private Status status;
	
	public enum Status{
		available,
		pending,
		sold
	}
	
}
