package com.everis.d4i.tutorial.controller.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.everis.d4i.tutorial.controller.mapper.FilmRestMapper;
import com.everis.d4i.tutorial.controller.rest.FilmRest;
import com.everis.d4i.tutorial.controller.rest.FilteringParameters;
import com.everis.d4i.tutorial.service.FilmService;
import com.everis.d4i.tutorial.service.dto.FilmDto;

@RunWith(SpringRunner.class)
@WebMvcTest(FilmControllerImpl.class)
public class FilmControllerImplTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FilmService filmService;
	@MockBean
	private FilmRestMapper filmRestMapper;
	
	@Test
	public void getFilms_test() throws Exception {
		
		final FilmDto film = new FilmDto(1L, "Ford v Ferrari", 2019, "United States", "English", 152, "sports drama film"
				, "American car designer Carroll Shelby and driver Ken Miles battle corporate interference and the "
						+ "laws of physics to build a revolutionary race car for Ford in order to defeat Ferrari at "
						+ "the 24 Hours of Le Mans in 1966.");

		final List<FilmDto> filmList = List.of(film);
		
		final Page<FilmDto> page = new PageImpl<>(filmList);
		
		Mockito.when(filmService.getPageOfFilms(Mockito.any(Pageable.class))).thenReturn(page);
		
		final FilmRest filmRest = new FilmRest(1L, "Ford v Ferrari", 2019, "United States", "English", 152, "sports drama film"
				, "American car designer Carroll Shelby and driver Ken Miles battle corporate interference and the "
						+ "laws of physics to build a revolutionary race car for Ford in order to defeat Ferrari at "
						+ "the 24 Hours of Le Mans in 1966.");
		
		Mockito.when(filmRestMapper.mapToRest(film)).thenReturn(filmRest);

		final RequestBuilder request = MockMvcRequestBuilders
				.get("/netflix2/v1/films?page=0&size=1")
				.accept(MediaType.APPLICATION_JSON);
						
		mockMvc.perform(request)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Success"))).andExpect(jsonPath("$.code", is("200 OK")))
                .andExpect(jsonPath("$.data.content.[0].id", is(1)))
                .andExpect(jsonPath("$.data.content.[0].name", is("Ford v Ferrari")))
                .andExpect(jsonPath("$.data.content.[0].year", is(2019)))
                .andExpect(jsonPath("$.data.content.[0].country", is("United States")))
                .andExpect(jsonPath("$.data.content.[0].language", is("English")))
                .andExpect(jsonPath("$.data.content.[0].duration", is(152)))
                .andExpect(jsonPath("$.data.content.[0].shortDescription", is("sports drama film")))
                .andExpect(jsonPath("$.data.content.[0].longDescription", is("American car designer Carroll Shelby "
                		+ "and driver Ken Miles battle corporate interference and the laws of physics to build a "
                		+ "revolutionary race car for Ford in order to defeat Ferrari at the 24 Hours of Le Mans in 1966.")))
                .andExpect(jsonPath("$.data.content.[0].duration", is(152)));
	}
	
	@Test
	public void getFilmsFilteredStatic_test() throws Exception {
		
		final int minimumDuration = 120;
		
		final FilmDto film = new FilmDto(1L, "Ford v Ferrari", 2019, "United States", "English", 152, "sports drama film"
				, "American car designer Carroll Shelby and driver Ken Miles battle corporate interference and the "
						+ "laws of physics to build a revolutionary race car for Ford in order to defeat Ferrari at "
						+ "the 24 Hours of Le Mans in 1966.");
		
		Mockito.when(filmService.getFilmsFilteredByMinimumDuration(minimumDuration)).thenReturn(List.of(film));

		final FilmRest filmRest = new FilmRest(1L, "Ford v Ferrari", 2019, "United States", "English", 152, "sports drama film"
				, "American car designer Carroll Shelby and driver Ken Miles battle corporate interference and the "
						+ "laws of physics to build a revolutionary race car for Ford in order to defeat Ferrari at "
						+ "the 24 Hours of Le Mans in 1966.");
		
		Mockito.when(filmRestMapper.mapToRest(film)).thenReturn(filmRest);
		
		final RequestBuilder request = MockMvcRequestBuilders
				.get("/netflix2/v1/films-filtered-static?minimumDuration=120")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(content()
		.json("{\"status\":\"Success\",\"code\":\"200 OK\",\"message\":\"OK\",\"data\":[{\"id\":1,\"name\":\"Ford v Ferrari\""
				+ ",\"year\":2019,\"country\":\"United States\",\"language\":\"English\",\"duration\":152,\"shortDescription\":"
				+ "\"sports drama film\",\"longDescription\":\"American car designer Carroll Shelby and driver Ken Miles battle"
				+ " corporate interference and the laws of physics to build a revolutionary race car for Ford in order to defeat"
				+ " Ferrari at the 24 Hours of Le Mans in 1966.\"}]}"))
		.andReturn();
	}
	
	
	@Test
	public void getFilmsFilteredDynamic_test() throws Exception {
		
		final FilmDto film = new FilmDto(1L, "Ford v Ferrari", 2019, "United States", "English", 152, "sports drama film"
				, "American car designer Carroll Shelby and driver Ken Miles battle corporate interference and the "
						+ "laws of physics to build a revolutionary race car for Ford in order to defeat Ferrari at "
						+ "the 24 Hours of Le Mans in 1966.");
		
		final String name = "Ford";
		final FilteringParameters filters = FilteringParameters.builder()
							                .name(name)
							                .build();
		
		Mockito.when(filmService.getDynamicallyFiltered(filters)).thenReturn(List.of(film));
		
		final FilmRest filmRest = new FilmRest(1L, "Ford v Ferrari", 2019, "United States", "English", 152, "sports drama film"
				, "American car designer Carroll Shelby and driver Ken Miles battle corporate interference and the "
						+ "laws of physics to build a revolutionary race car for Ford in order to defeat Ferrari at "
						+ "the 24 Hours of Le Mans in 1966.");
		
		Mockito.when(filmRestMapper.mapToRest(film)).thenReturn(filmRest);

		
		final RequestBuilder request = MockMvcRequestBuilders
				.get("/netflix2/v1/films-filtered-dinamically?name=Ford")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(content()
		.json("{\"status\":\"Success\",\"code\":\"200 OK\",\"message\":\"OK\",\"data\":[{\"id\":1,\"name\":\"Ford v Ferrari\""
				+ ",\"year\":2019,\"country\":\"United States\",\"language\":\"English\",\"duration\":152,\"shortDescription\":"
				+ "\"sports drama film\",\"longDescription\":\"American car designer Carroll Shelby and driver Ken Miles battle"
				+ " corporate interference and the laws of physics to build a revolutionary race car for Ford in order to defeat"
				+ " Ferrari at the 24 Hours of Le Mans in 1966.\"}]}"))
		.andReturn();
	}
}
