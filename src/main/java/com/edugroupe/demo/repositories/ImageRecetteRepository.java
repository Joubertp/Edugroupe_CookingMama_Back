package com.edugroupe.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.edugroupe.demo.metiers.ImageRecette;

public interface ImageRecetteRepository
		extends PagingAndSortingRepository<ImageRecette, Integer>,
				ImageRecetteRepositoryComplement {

}
