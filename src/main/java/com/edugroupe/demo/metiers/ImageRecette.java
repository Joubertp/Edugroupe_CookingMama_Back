package com.edugroupe.demo.metiers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class ImageRecette {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String filename;
	private String contentType;
	@JsonIgnore
	private String storageId;
	@JsonIgnore
	@ManyToOne
	private Recette recette;
	
	public ImageRecette(int id, String filename, String contentType, String storageId) {
		super();
		this.id = id;
		this.filename = filename;
		this.contentType = contentType;
		this.storageId = storageId;
	}

}
