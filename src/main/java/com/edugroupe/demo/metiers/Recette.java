package com.edugroupe.demo.metiers;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.edugroupe.demo.metiers.json.BaseEntity;
import com.edugroupe.demo.metiers.json.Etape;
import com.edugroupe.demo.metiers.json.Ingredient;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
@Entity 
public class Recette extends BaseEntity{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate dateCreation;
	private int tempsPreparation;
	private int tempsCuisson;
	
	@Type( type = "json" )
	@Column( columnDefinition = "json" )
	private Set<Ingredient> ingredients;
	@Type( type = "json" )
	@Column( columnDefinition = "json" )
	private Set<Etape> etapes;
	
//	private Set<Commentaire> commentaires;
//	private User auteur;
	
}
