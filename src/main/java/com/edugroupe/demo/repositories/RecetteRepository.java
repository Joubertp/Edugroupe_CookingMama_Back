package com.edugroupe.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.edugroupe.demo.metiers.Recette;

@RepositoryRestResource
public interface RecetteRepository extends PagingAndSortingRepository<Recette, Integer> {

	Page<Recette> findByAuteurId(int userId, Pageable page);

}
