package com.petstore.app.dto;

import java.util.List;

import com.petstore.app.entity.Category;
import com.petstore.app.entity.Pet;
import com.petstore.app.entity.Tag;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PetRequest {
	
	private Category category;
	
	@NotEmpty(message="名前を入力してください。")
	private String name;
	
	private List<String> photoUrls;
	
	private List<Tag> tags;
	
	@NotNull(message="ステータスは 'available', 'pending', 'sold' です。")
	private Pet.Status Status;
	
}
