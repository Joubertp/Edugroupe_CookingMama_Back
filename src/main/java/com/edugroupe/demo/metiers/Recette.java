package com.edugroupe.demo.metiers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.edugroupe.demo.metiers.json.BaseEntity;
import com.edugroupe.demo.metiers.json.EtapeRecette;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude = {"commentaires","ingredients","auteur"}) @NoArgsConstructor
@Entity 
public class Recette extends BaseEntity{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nom;
	private LocalDate dateCreation;
	private LocalDate dateDerniereEdition;
	private int tempsPreparation;
	private int tempsCuisson;
	@Column(columnDefinition="TEXT")
	private String description;

	@Type( type = "json" )
	@Column( columnDefinition = "json" )
	private Set<EtapeRecette> listeEtapes;
	
	@OneToMany(mappedBy = "recette")
	private Set<CommentaireRecette> commentaires;
	@OneToMany(mappedBy = "recette")
	private Set<IngredientRecette> ingredients;
	@ManyToOne
	private User auteur;
	
	/*
	 * Constructors
	 */
	public Recette(Set<Ingredient> ingredients) {
		this();
		Set<IngredientRecette> ingredientRecettes = new HashSet<>();
		for(Ingredient ingredient : ingredients) {
			ingredientRecettes.add(new IngredientRecette(ingredient));
		}
		this.ingredients = ingredientRecettes;
	}
	
	/*
	 * Methods
	 */
	public void toEraseInfiniteLoop() {
		this.ingredients.forEach(i -> {
			i.setRecette(null);
			i.getIngredient().setRefsRecette(null);
		});	
		this.commentaires = null;
		this.auteur = null;
	}
	
	public void toEraseAllDependancy() {
		this.commentaires = null;
		this.ingredients = null;
		this.auteur = null;
	}

	public Recette(int idRecette) {
		this();
		this.id = idRecette;
	}
}
