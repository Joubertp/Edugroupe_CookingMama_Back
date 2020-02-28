package com.edugroupe.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.edugroupe.demo.metiers.ImageRecette;
import com.edugroupe.demo.repositories.custom.ImageRecetteRepositoryComplement;

public interface ImageRecetteRepository
		extends PagingAndSortingRepository<ImageRecette, Integer>,
				ImageRecetteRepositoryComplement {

}
