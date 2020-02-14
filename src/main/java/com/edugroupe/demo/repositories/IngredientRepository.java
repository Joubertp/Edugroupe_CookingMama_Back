package com.edugroupe.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.edugroupe.demo.metiers.Ingredient;

@RepositoryRestResource
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient,Integer> {

	@Query("SELECT i FROM Ingredient i")
	<T>List<T> findAll(Class<T> type, Sort by);

	
}
