package com.edugroupe.demo.metiers;

import java.util.Set;

import javax.persistence.Column;
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

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
@Entity 
public class Ingredient {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nom;
	private String cathegorie;
	private String caracteristiqueNutritionnelle;
	@Column(columnDefinition="TEXT")
	private String Descritpion;
		
	@OneToMany (mappedBy = "ingredient")
	private Set<IngredientRecette> refsRecette;
	
	public Ingredient(int id) {
		this.id = id;
	}
	
	public void toEraseInfiniteLoop() {
		this.refsRecette.forEach(ref -> {
			ref.setIngredient(null);
			ref.getRecette().toEraseAllDependancy();
		});	
	}
	
	public void toEraseAllDependancy() {
		this.refsRecette = null;
	}
}
