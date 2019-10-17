package com.edugroupe.demo.metiers;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
@Entity 
public class User {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String pseudo;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "auteur")
	private Set<Recette> recettes;
	
}
