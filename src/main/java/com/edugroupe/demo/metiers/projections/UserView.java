package com.edugroupe.demo.metiers.projections;

import java.util.Set;

import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.Role;

public interface UserView {

	String getUsername();
	Set<Role> getRoles();
	Set<Recette> getRecettes();
}
