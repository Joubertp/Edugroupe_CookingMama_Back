package com.edugroupe.demo.metiers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
@Entity 
public class Ingredient {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String cathegorie;
	private String caracteristiqueNutritionnelle;
	private String Descritpion;
	
}
