package com.edugroupe.demo.metiers.projections;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;

import com.edugroupe.demo.metiers.User;

public interface RecetteView {

	@Value("#{target.id}")
	int getId();
	String getNom();
	LocalDate getDateCreation();
	LocalDate getDateDerniereEdition();
	int getTempsPreparation();
	int getTempsCuisson();
	String getDescription();
	//*****************************
	User getAuteur();
	Set<EtapeRecetteView> getListeEtapes();
	Set<IngredientRecetteView> getIngredients();
	
}
