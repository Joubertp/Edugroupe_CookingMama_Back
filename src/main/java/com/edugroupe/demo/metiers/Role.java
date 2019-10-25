package com.edugroupe.demo.metiers;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString(exclude = {"users"})
public class Role {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, unique = true)
	private String roleName;
	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Set<Login> logins;
	
	public Role(int id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	
}
