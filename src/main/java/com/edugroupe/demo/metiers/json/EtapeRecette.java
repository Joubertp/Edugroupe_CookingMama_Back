package com.edugroupe.demo.metiers.json;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class EtapeRecette implements Serializable{

	private int numero;
	private String description;

	public EtapeRecette(int n) {
		this.numero = n;
	}
}
