package com.petstore.app.dto;

import com.petstore.app.entity.Pet;

import lombok.Data;

@Data
public class PetResponse {
	
	private Long id;
//	private Category category;
	private String name;
//	private Tag tags;
	private String Status;
	
	public PetResponse(Pet pet) {
		this.id = pet.getId();
		this.name = pet.getName();
		Status = pet.getStatus();
	}
	
}
