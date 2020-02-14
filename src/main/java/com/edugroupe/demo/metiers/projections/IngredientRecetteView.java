package com.edugroupe.demo.metiers.projections;

import org.springframework.beans.factory.annotation.Value;

import com.edugroupe.demo.metiers.Ingredient;

public interface IngredientRecetteView {

	@Value("#{target.id}")
	int getId();
	double getQuantite();
	String getText();
	//*****************************************
	Ingredient getIngredient();
	
	
}
