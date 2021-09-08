package com.everis.d4i.tutorial.service.impl;

import com.everis.d4i.tutorial.persistence.FilmRepository;
import com.everis.d4i.tutorial.persistence.entity.FilmEntity;
import com.everis.d4i.tutorial.persistence.mapper.FilmEntityMapper;
import com.everis.d4i.tutorial.persistence.specification.Specifications;
import com.everis.d4i.tutorial.service.FilmService;
import com.everis.d4i.tutorial.service.dto.FilmDto;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import com.everis.d4i.tutorial.controller.rest.FilteringParameters;



@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

	private final FilmRepository filmRepository;

	private final FilmEntityMapper filmEntityMapper;
	
    @Override
	public Collection<FilmDto> getFilms() {
		return filmRepository.findAll().parallelStream().map(filmEntityMapper::mapToDto).collect(Collectors.toList());
	}

    @Override
    public Page<FilmDto> getPageOfFilms(final Pageable pageable) {
        return filmRepository.findAll(pageable).map(filmEntityMapper::mapToDto);
    }
    
    @Override
    public List<FilmDto> getFilmsFilteredByMinimumDuration(final Integer duration) {
        return filmRepository.findAllByDurationGreaterThan(duration).stream()
                       .map(filmEntityMapper::mapToDto)
                       .collect(Collectors.toList());
    }
        
    @Override
    public List<FilmDto> getDynamicallyFiltered(final FilteringParameters filteringParameters) {

        final List<FilmEntity> filmList = filmRepository.findAll(Specifications.getFilmsByYearOrName(
        		filteringParameters.getName(), filteringParameters.getYear()));
        return filmList.stream()
                       .map(filmEntityMapper::mapToDto)
                       .collect(Collectors.toList());
    }
    
    
}
