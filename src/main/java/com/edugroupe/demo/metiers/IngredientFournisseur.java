package com.edugroupe.demo.metiers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(exclude = {"fournisseur","ingredient"})
public class IngredientFournisseur {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String libelle;
	private double prix;
	@Column(columnDefinition="TEXT")
	private String infos;
	@ManyToOne
	private Fournisseur fournisseur;
	@ManyToOne
	private Ingredient ingredient;
	
}
