package com.edugroupe.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.edugroupe.demo.metiers.User;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Integer>{

	User findByUsername(String username);
	
}
