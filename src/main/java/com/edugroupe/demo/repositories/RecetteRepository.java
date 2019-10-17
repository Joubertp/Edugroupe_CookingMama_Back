package com.edugroupe.demo.repositories;

import java.awt.print.Pageable;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;

import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.User;

@RepositoryRestResource
public interface RecetteRepository extends PagingAndSortingRepository<Recette, Integer> {

	//ResponseEntity<Recette> findByAuteur(User user, Pageable page);

}
