package com.everis.d4i.tutorial.service.impl;

import com.everis.d4i.tutorial.persistence.CategoryRepository;
import com.everis.d4i.tutorial.persistence.mapper.CategoryEntityMapper;
import com.everis.d4i.tutorial.service.CategoryService;
import com.everis.d4i.tutorial.service.dto.CategoryDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	private final CategoryEntityMapper categoryEntityMapper;

	@Override
	public List<CategoryDto> getCategories() {
		return categoryRepository.findAll().stream().map(categoryEntityMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDto createCategory(final CategoryDto categoryDto) {
		return categoryEntityMapper.mapToDto(categoryRepository.save(categoryEntityMapper.mapToEntity(categoryDto)));
	}

}
