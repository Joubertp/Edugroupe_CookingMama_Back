package com.edugroupe.demo.metiers.projections;

import org.springframework.beans.factory.annotation.Value;

public interface CathegorieIngredientView {

	@Value("#{target.id}")
	int getId();
	String getNom();
}
