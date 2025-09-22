package com.example.demo.Service.Impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Category;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository repo;
	public CategoryServiceImpl(CategoryRepository repo) { this.repo = repo; }
	
	@Override
	public Page<Category> search(String keyword, Pageable pageable) {
		if (keyword == null || keyword.isBlank()) {
			return repo.findAll(pageable);
		}
		return repo.findByNameContainingIgnoreCase(keyword.trim(), pageable);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public Optional<Category> findById(Integer id) {
		return repo.findById(id);
	}

	@Override
	public Category save(Category c) {
		return repo.save(c);
	}

	@Override
	public void deleteById(Integer id) {
		repo.deleteById(id);
	}

}
