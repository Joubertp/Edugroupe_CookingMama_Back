package com.edugroupe.demo.metiers.json;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IngredientRecette implements Serializable{

	private int id;
	private String valeur;
	
}
