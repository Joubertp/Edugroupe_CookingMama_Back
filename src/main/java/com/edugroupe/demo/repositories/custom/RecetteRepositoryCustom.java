package com.edugroupe.demo.repositories.custom;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edugroupe.demo.metiers.Recette;

public interface RecetteRepositoryCustom {

	Page<Recette> findByCritere(Recette critere, Pageable page);
	
}
