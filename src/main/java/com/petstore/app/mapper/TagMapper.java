package com.petstore.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.petstore.app.entity.Tag;

@Mapper
public interface TagMapper {

	List<Tag> findAll();
	List<Tag> findByPetId(Long id);
	Tag findByName(String name);
	int insertTag(String name);
	int insertPetTag(Long petId, Long tagId);
	int deleteByPetId(Long petId);
	
}
