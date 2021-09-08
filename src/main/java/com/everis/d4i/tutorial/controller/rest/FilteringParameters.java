package com.everis.d4i.tutorial.controller.rest;

import lombok.Builder;
import lombok.Data;

import java.time.Year;
import java.util.List;

@Data
@Builder
public class FilteringParameters {

    String name;

    Year year;

    String country;

    List<String> languages;

    Integer category;

    List<String> subcategories;

    Integer minimumDuration;
}
