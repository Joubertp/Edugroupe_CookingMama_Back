package com.edugroupe.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.edugroupe.demo.metiers.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {

	Role findByRoleName(String roleName);
	
}
