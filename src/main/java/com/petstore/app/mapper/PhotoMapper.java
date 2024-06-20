package com.petstore.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.petstore.app.entity.Photo;

@Mapper
public interface PhotoMapper {

	List<Photo> findAll();
	List<Photo> findByPetId(Long petId);
	Photo findByUrl(String url);
	int insert(Long petId, String url);
	int deleteByPetId(Long petId);
		
}
