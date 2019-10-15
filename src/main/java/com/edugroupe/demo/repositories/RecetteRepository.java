package com.edugroupe.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.edugroupe.demo.metiers.Recette;

public interface RecetteRepository extends PagingAndSortingRepository<Recette, Integer> {

}
