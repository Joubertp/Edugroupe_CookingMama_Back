package com.edugroupe.demo.metiers.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class IngredientRecetteDTO {

	private int id;
	private double quantite;
	private String text;
	private int idIngredient; 
}
