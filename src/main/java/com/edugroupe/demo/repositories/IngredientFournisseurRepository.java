package com.edugroupe.demo.repositories;

import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.edugroupe.demo.metiers.IngredientFournisseur;

public interface IngredientFournisseurRepository extends PagingAndSortingRepository<IngredientFournisseur,Integer> {

	Set<IngredientFournisseur> findByIngredientId(int ingredientId);
	
}
