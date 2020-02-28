package com.edugroupe.demo.metiers;

import java.util.HashSet;
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
	private String caracteristiqueNutritionnelle;
	@Column(columnDefinition="TEXT")
	private String descritpion;
	//**************************************	
	@JsonIgnore
	@OneToMany (mappedBy = "ingredient")
	private Set<IngredientRecette> refsRecette;
	@JsonIgnore
	@OneToMany (mappedBy = "ingredients")
	private Set<CathegorieIngredient> cathegories;
	@JsonIgnore
	@OneToMany(mappedBy = "ingredient")
	private Set<IngredientFournisseur> ingredientsFournisseur;
	
	//* Constructors ***********************
	public Ingredient(int id) {
		this.id = id;
	}
	
	public Ingredient(String nom,String descritpion, String... cathIngres) {
		this();
		this.nom = nom;
		this.cathegories = new HashSet<>();
		for(String cathIngre : cathIngres) {
			this.cathegories.add(new CathegorieIngredient(cathIngre));			
		}
		this.descritpion = descritpion;		
	}
	//** Method ****************************

	public static Set<Ingredient> creatListWith(int[] idList){
		Set<Ingredient> ingredientList = new HashSet<>();
		for(int id : idList) {
			ingredientList.add(new Ingredient(id));
		}
		return ingredientList;
	}

}
