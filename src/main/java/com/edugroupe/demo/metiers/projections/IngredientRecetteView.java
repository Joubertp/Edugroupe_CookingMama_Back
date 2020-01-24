package com.edugroupe.demo.metiers.projections;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;

public interface IngredientRecetteView {

	@Value("#{target.id}")
	int getId();
	double getQuantite();
	String getText();
	//*****************************************
	@Value("#{target.getIngredient().getNom()}")
	String getNom();
	@Value("#{target.getIngredient().getDescritpion()}")
	String getDescription();
	@Value("#{target.getIngredient().getCathegories()}")
	Set<CathegorieIngredientView> getCathegories();
	
	
}
