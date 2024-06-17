package com.petstore.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petstore.app.dto.PetRequest;
import com.petstore.app.dto.PetResponse;
import com.petstore.app.service.PetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pets")
public class PetController {

	private final PetService petService;
	
	@GetMapping
	public List<PetResponse> allPet(){
		return petService.findAll();
	}

	@GetMapping("/{id}")
	public PetResponse findPet(@PathVariable("id") Long id) {
		return petService.findById(id);
	}
	
	@PostMapping
	public PetResponse addPet(@RequestBody PetRequest petRequest) {
		return petService.addPet(petRequest);
	}
	
	@PutMapping("/{id}")
	public PetResponse updatePet(@PathVariable Long id, @RequestBody PetRequest petRequest) {
		return petService.updatePet(id, petRequest);
	}
	
	@DeleteMapping("/{id}")
	public void deletePet(@PathVariable("id") Long id) {
		petService.deletePet(id);
	}
	
}
