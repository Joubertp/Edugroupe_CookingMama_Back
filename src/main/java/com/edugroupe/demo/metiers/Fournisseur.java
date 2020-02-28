package com.edugroupe.demo.metiers;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(exclude = {"ingredientsFournisseur"})
public class Fournisseur {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String libelle;
	@JsonIgnore
	@OneToMany(mappedBy = "fournisseur")
	private Set<IngredientFournisseur> ingredientsFournisseur;
	
}
