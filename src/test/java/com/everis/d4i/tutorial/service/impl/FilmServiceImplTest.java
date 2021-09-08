package com.everis.d4i.tutorial.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.data.domain.Pageable;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.everis.d4i.tutorial.controller.rest.FilteringParameters;
import com.everis.d4i.tutorial.exception.NetflixException;
import com.everis.d4i.tutorial.persistence.FilmRepository;
import com.everis.d4i.tutorial.persistence.entity.FilmEntity;
import com.everis.d4i.tutorial.persistence.mapper.FilmEntityMapper;
import com.everis.d4i.tutorial.persistence.specification.Specifications;
import com.everis.d4i.tutorial.service.dto.FilmDto;

@RunWith(MockitoJUnitRunner.class)
public class FilmServiceImplTest {
	@InjectMocks
	FilmServiceImpl services;

	@Mock
	FilmRepository filmRepository;
	
	@Mock
	FilmEntity filmEntity;
	
	@Mock
	FilmEntityMapper filmEntityMapper;
	Year num = Year.of(2017);

	@Test
	public void getFilms_succesTest() throws NetflixException {

		final FilmEntity test = new FilmEntity(1L, "test", Year.of(2020), "Spain", "Spanish", 120, "short", "long", null, null);
		final FilmEntity test2 = new FilmEntity(2L, "test2", Year.of(2020), "England", "English", 110, "short", "long", null, null);
		final FilmEntity test3 = new FilmEntity(3L, "test3", Year.of(2020), "China", "chinese", 160, "short", "long", null, null);
		
		Mockito.when(filmRepository.findAll()).thenReturn(new ArrayList<FilmEntity>(List.of(test, test2, test3)));
		
		final Collection<FilmDto> responseGet = services.getFilms();
		
		assertNotNull(responseGet);
		assertEquals(3, responseGet.size());
	}
	
	@Test
	public void getPageOfFilms_succesTest() throws NetflixException {
		
		final FilmEntity test = new FilmEntity(1L, "test", Year.of(2020), "Spain", "Spanish", 120, "short", "long", null, null);
		
		final List<FilmEntity> filmList = List.of(test);
		
		final Page<FilmEntity> page = new PageImpl<>(filmList);
		
		Mockito.when(filmRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
		
		final Pageable pageable = PageRequest.of(0, 1);
		
		final Page<FilmDto> responseGetPageFilm = services.getPageOfFilms(pageable);

		assertNotNull(responseGetPageFilm);

	}
	
	@Test
	public void getFilmsFilteredByMinimumDuration_succesTest() throws NetflixException {
		final Integer duration = 2020;
		
		final FilmEntity test = new FilmEntity(1L, "test", Year.of(2020), "Spain", "Spanish", 120, "short", "long", null, null);
		
		Mockito.when(filmRepository.findAllByDurationGreaterThan(duration)).thenReturn(List.of(test));
		
		final List<FilmDto> responseGetMinDuration = services.getFilmsFilteredByMinimumDuration(duration);
		
		assertNotNull(responseGetMinDuration);
		assertEquals(1, responseGetMinDuration.size());
		
	}
	
	@Test
	public void getDynamicallyFiltered_succesTest() throws NetflixException {
		
		final FilmEntity test = new FilmEntity(1L, "test", Year.of(2020), "Spain", "Spanish", 120, "short", "long", null, null);
		
		final Specification<FilmEntity> parameter = Specifications.getFilmsByYearOrName(
        											"test", Year.of(2020));
		
		Mockito.when(filmRepository.findAll(parameter)).thenReturn(List.of(test));
				
		final String name = "test";
		final FilteringParameters filters = FilteringParameters.builder()
							                .name(name)
							                .year(Year.of(2020))
							                .build();
		
		final List<FilmDto> responseGetDinamicFiltered = services.getDynamicallyFiltered(filters);

		assertNotNull(responseGetDinamicFiltered);
	}
}