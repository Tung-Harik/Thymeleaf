package com.example.demo.Service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.Entity.Category;

public interface CategoryService {
	Page<Category> search(String keyword, Pageable pageable);

	Page<Category> findAll(Pageable pageable);

	Optional<Category> findById(Integer id);

	Category save(Category c);

	void deleteById(Integer id);
}
