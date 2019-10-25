package com.edugroupe.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.edugroupe.demo.metiers.Login;

public interface LoginRepository extends PagingAndSortingRepository<Login, Integer> {

	Login findByUsername(String username);
	
}
