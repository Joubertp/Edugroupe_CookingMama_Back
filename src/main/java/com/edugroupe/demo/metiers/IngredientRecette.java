package com.edugroupe.demo.metiers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
@Entity
public class IngredientRecette {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String valeur;
	
	@JsonBackReference
	@ManyToOne
	private Recette recette;
	@JsonBackReference
	@ManyToOne
	private Ingredient ingredient;
	
}
