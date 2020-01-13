package com.edugroupe.demo.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.repositories.custom.RecetteRepositoryCustom;

@RepositoryRestResource
public interface RecetteRepository extends PagingAndSortingRepository<Recette, Integer>, RecetteRepositoryCustom {

	<T>Page<T> findByAuteurId(int userId, Pageable page,Class<T> type);

	<T>Optional<T> findById(int i, Class<T> type);

	@Query("SELECT r FROM Recette r")
	<T>Page<T> findAll(Pageable page, Class<T> type);

}
