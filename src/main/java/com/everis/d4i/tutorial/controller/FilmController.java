package com.everis.d4i.tutorial.controller;


import java.time.Year;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.everis.d4i.tutorial.controller.rest.FilmRest;
import com.everis.d4i.tutorial.controller.rest.response.NetflixResponse;

public interface FilmController {

	//PAGINATION
	NetflixResponse<Slice<FilmRest>> getFilms(Pageable pageable);
	
	//STATIC FILTERING
    NetflixResponse<FilmRest[]> getFilmsFilteredStatic(Integer duration);

    // DINAMIC FILTERING
	NetflixResponse<FilmRest[]> getFilmsFilteredDynamic(String name, Year year);


}
