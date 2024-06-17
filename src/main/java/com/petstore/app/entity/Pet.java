package com.petstore.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

	private Long id;
//	private Category category;
	private String name;
//	private Tag tags;
	private String Status;
	
}
