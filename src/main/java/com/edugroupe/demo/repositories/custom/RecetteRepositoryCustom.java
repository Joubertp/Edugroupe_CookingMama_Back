package com.edugroupe.demo.repositories.custom;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.projections.RecetteView;

public interface RecetteRepositoryCustom {

	<T> Page<T> findByCritere(Recette critere, Pageable page, Class<T> type );
	
}
