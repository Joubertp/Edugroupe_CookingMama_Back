package com.edugroupe.demo.metiers;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
@Entity
public class CommentaireRecette {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String contenue;
	private LocalDate dateCreation;
	
	@JsonIgnore
	@ManyToOne
	private Recette recette;
}
