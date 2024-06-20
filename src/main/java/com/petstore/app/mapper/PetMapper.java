package com.petstore.app.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.petstore.app.entity.Pet;
import com.petstore.app.entity.Tag;

@Mapper
public interface PetMapper {

	List<Pet> findAll();
	Optional<Pet> findById(Long id);
	Pet findByName(String name);
	List<Pet> findByTag(String tag);
	List<Pet> findByStatus(String status);
	int insert(Pet pet);
	int update(Pet pet);
	int delete(Long id);
	
}
