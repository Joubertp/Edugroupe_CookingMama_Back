package com.edugroupe.demo.metiers.projections;

import java.util.Set;

import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.Role;

public interface UserView {

	int getId();
	String getUsername();
	boolean isEnabled();
	String getDescription();
	Set<Role> getRoles();
	Set<Recette> getRecettes();
}
