package com.everis.d4i.tutorial.persistence.mapper;

import java.io.Serializable;

import com.everis.d4i.tutorial.controller.rest.FilmRest;

public interface EntityMapper<E extends Serializable, D extends Serializable> {

	default E mapToEntity(final D dto) {
		throw new UnsupportedOperationException();
	}

	default D mapToDto(final E entity) {
		throw new UnsupportedOperationException();
	}


	default FilmRest mapToRest(final E entity) {
		throw new UnsupportedOperationException();
	}
}
