package com.edugroupe.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.edugroupe.demo.metiers.Ingredient;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient,Integer> {

}
