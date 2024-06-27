package com.petstore.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.petstore.app.entity.Category;

@Mapper
public interface CategoryMapper {
	
	List<Category> findAll();
	Category findById(Long categoryId);
	Category findByName(String name);
	int insert(String name);

}
