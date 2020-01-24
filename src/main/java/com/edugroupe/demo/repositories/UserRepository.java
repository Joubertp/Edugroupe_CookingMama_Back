package com.edugroupe.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.edugroupe.demo.metiers.User;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Integer>{

	Optional<User> findByUsername( String username);
	
	//A utiliser au lieu du findByUsername(), si le Lazy Init ne fonctionne pas
	// Utilise pour MyUserDetailsService. 
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username=:username")
	Optional<User> findByUsernameWithRole(@Param("username") String username);
}
