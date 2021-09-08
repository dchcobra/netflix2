package com.everis.d4i.tutorial.persistence;

import com.everis.d4i.tutorial.persistence.entity.FilmEntity;

import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<FilmEntity, Long>, JpaSpecificationExecutor<FilmEntity> {

List<FilmEntity> findAllByOrderByYearDesc();  
	
    //PAGINATION
	List<FilmEntity> findAllByCategory_Id(Integer category, Sort sort);

    Slice<FilmEntity> findAllByCategory_Id(Integer category, Pageable pageable);

    List<FilmEntity> findAllByDurationGreaterThan(Integer duration, Pageable pageable);

    //FILTERING STATIC
    List<FilmEntity> findAllByDurationGreaterThan(Integer duration);

    List<FilmEntity> findAllByCategory_IdAndShortDescriptionContaining(Integer categoryId, String secondaryCategory);

    List<FilmEntity> findTop10ByLanguageInOrderByLanguageDesc(Collection<String> possibleLanguages);

    Optional<FilmEntity> findFirstByYearBeforeAndDurationIsNotNullAndCountry(Year year, String country);

    @Query(nativeQuery = true,
            value = "select * " +
                           "from films " +
                           "where year = :year " +
                           "    and category_id = (select id from categories where name = :name);")
    List<FilmEntity> findFilmByYearAndCategory(
            @Param("year") Integer year,
            @Param("name") String name);


}
