package com.edugroupe.demo.metiers.projections;

import java.util.Set;

import com.edugroupe.demo.metiers.Role;

public interface UserWithRoles {

	int getId();
	String getUsername();
	//**************************************
	Set<Role> getRoles();
}
