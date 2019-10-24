package com.edugroupe.demo.repositories;

import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.edugroupe.demo.metiers.IngredientRecette;

@RepositoryRestResource
public interface IngredientRecetteRepository extends PagingAndSortingRepository<IngredientRecette,Integer>{

	Set<IngredientRecette> findByIngredientId(int id);
	
}
