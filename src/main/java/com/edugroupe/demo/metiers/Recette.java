package com.edugroupe.demo.metiers;

import java.time.LocalDate;
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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
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
	
	@JsonManagedReference
	@OneToMany(mappedBy = "recette")
	private Set<CommentaireRecette> commentaires;
	@JsonManagedReference
	@OneToMany(mappedBy = "recette")
	private Set<IngredientRecette> ingredients;
	@JsonBackReference
	@ManyToOne
	private User auteur;
	
}
