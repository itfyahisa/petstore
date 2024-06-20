package com.petstore.app.controller;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petstore.app.dto.PetRequest;
import com.petstore.app.dto.PetResponse;
import com.petstore.app.exception.BadRequestException;
import com.petstore.app.service.PetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
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
	
	@GetMapping("/findByStatus")
	public List<PetResponse> findByStatuses(@RequestParam List<String> statuses){
		return petService.findByStatus(statuses);
	}
	
	@GetMapping("/findByTags")
	public List<PetResponse> findByTags(@RequestParam List<String> tags){
		return petService.findByTags(tags);
	}
	
	
	@PostMapping
	public PetResponse addPet(@Valid @RequestBody PetRequest petRequest, BindingResult bindingResult) {
		validatePetRequest(bindingResult);
		return petService.addPet(petRequest);
	}
	
	@PutMapping("/{id}")
	public PetResponse updatePet(@PathVariable Long id, @Valid @RequestBody PetRequest petRequest, BindingResult bindingResult) {
		validatePetRequest(bindingResult);
		return petService.updatePet(id, petRequest);
	}

	@DeleteMapping("/{id}")
	public void deletePet(@PathVariable("id") Long id) {
		petService.deletePet(id);
	}
	
	public void validatePetRequest(BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new BadRequestException();
		}
	}
	
}
