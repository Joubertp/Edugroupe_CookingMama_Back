package com.edugroupe.demo.metiers;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString(exclude = {"roles", "password"}) 
@Entity 
public class User {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String pseudo;
	
	//**************************************
	@Column(nullable = false, unique = true)
	private String username;
	@JsonIgnore
	private String password;
	private boolean enabled;
	@ManyToMany
	private Set<Role> roles;
	//**************************************
	@JsonIgnore
	@OneToMany(mappedBy = "auteur")
	private Set<Recette> recettes;
	
	public User(int id, String username, String password, boolean enabled) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
	
}
