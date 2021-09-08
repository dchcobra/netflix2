package com.everis.d4i.tutorial.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.everis.d4i.tutorial.exception.NetflixException;
import com.everis.d4i.tutorial.persistence.CategoryRepository;
import com.everis.d4i.tutorial.persistence.entity.CategoryEntity;
import com.everis.d4i.tutorial.persistence.mapper.CategoryEntityMapper;
import com.everis.d4i.tutorial.service.dto.CategoryDto;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

	@InjectMocks
	private CategoryServiceImpl services;

	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private CategoryEntity categoryEntity;
	
	@Mock
	private CategoryDto categoryDto;
	
	@Mock
	private CategoryEntityMapper categoryEntityMapper;
	
	@Test
	public void getCategories_succesTest() throws NetflixException {
				
		final CategoryEntity test = new CategoryEntity(1 , "test", null);
		final CategoryEntity test2 = new CategoryEntity(2, "test2", null);
		
		Mockito.when(categoryRepository.findAll()).thenReturn(new ArrayList<CategoryEntity>(List.of(test, test2)));
		
		final Collection<CategoryDto> response = services.getCategories();
		
		assertNotNull(response);
		assertEquals(2, response.size());
		//assertEquals(response.containsAll(test, test2)); CONSEGUIR QUE FUNCIONE ESTO
	}
	
	@Test
	public void createCategories_succesTest() throws NetflixException {
		
		final CategoryEntity categoryNewEntity = new CategoryEntity(1, "newTestMock", null);
		final CategoryDto categoryNew = new CategoryDto(1, "newTestMock");

		Mockito.when(categoryEntityMapper.mapToEntity(categoryNew)).thenReturn(categoryNewEntity);
		
		Mockito.when(categoryRepository.save(categoryNewEntity)).thenReturn(categoryNewEntity);
		
		Mockito.when(categoryEntityMapper.mapToDto(categoryNewEntity)).thenReturn(categoryNew);
		
		final CategoryDto responseCreate = services.createCategory(categoryNew);
		
		assertNotNull(responseCreate);
		assertEquals(categoryNew, responseCreate);
	}
	
}
