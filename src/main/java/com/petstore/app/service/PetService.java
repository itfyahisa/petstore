package com.petstore.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.petstore.app.dto.PetRequest;
import com.petstore.app.dto.PetResponse;
import com.petstore.app.entity.Pet;
import com.petstore.app.exception.PetNotFoundException;
import com.petstore.app.mapper.PetMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {
	
	private final PetMapper petMapper;
	
	public List<PetResponse> findAll(){
		List<Pet> pets = petMapper.findAll();
		List<PetResponse> petResponses = new ArrayList<>();
		pets.forEach(pet -> {
			PetResponse petResponse = new PetResponse(pet);
			petResponses.add(petResponse);			
		});
		return petResponses;
	}
	
	public PetResponse findById(Long id) {
		Pet pet = petMapper.findById(id).orElseThrow(() -> new PetNotFoundException(id));
		PetResponse petResponse = new PetResponse(pet);
		return petResponse;
	}
	
	public PetResponse addPet(PetRequest petRequest) {
		Pet pet = new Pet();
		BeanUtils.copyProperties(petRequest, pet);
		petMapper.insert(pet);
		return new PetResponse(pet);
	}
	
	public PetResponse updatePet(Long id, PetRequest petRequest) {
		Pet pet = petMapper.findById(id).orElseThrow(() -> new PetNotFoundException(id));
		BeanUtils.copyProperties(petRequest, pet);		
		petMapper.update(pet);
		return new PetResponse(pet);
	}
	
	public void deletePet(Long id) {
		petMapper.findById(id).orElseThrow(() -> new PetNotFoundException(id));
		petMapper.delete(id);
	}
	
	
}
