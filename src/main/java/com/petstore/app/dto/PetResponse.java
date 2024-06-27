package com.petstore.app.dto;

import java.util.List;
import com.petstore.app.entity.Category;
import com.petstore.app.entity.Pet;
import com.petstore.app.entity.Tag;

import lombok.Data;

@Data
public class PetResponse {
	
	private Long id;
	private Category category;
	private String name;
	private List<String> photoUrls;
	private List<Tag> tags;
	private Pet.Status Status;
	
	public PetResponse(Pet pet, Category category, List<String> photoUrls, List<Tag> tags) {
		this.id = pet.getId();
		this.category = category;
		this.name = pet.getName();
		this.photoUrls = photoUrls;
		this.Status = pet.getStatus();
		this.tags = tags;
	}
	
	public enum Status{
		available,
		pending,
		sold
	}
	
}
