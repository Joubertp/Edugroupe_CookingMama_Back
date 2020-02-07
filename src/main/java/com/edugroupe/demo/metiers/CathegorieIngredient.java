package com.edugroupe.demo.metiers;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
@Entity 
public class CathegorieIngredient {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nom;
	//**************************************
	@JsonIgnore
	@ManyToMany
	private Set<Ingredient> ingredients;
	
	public CathegorieIngredient(String cathIngre) {
		this.nom = cathIngre;
	}
	
}
