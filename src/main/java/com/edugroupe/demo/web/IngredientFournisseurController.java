package com.edugroupe.demo.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edugroupe.demo.metiers.IngredientFournisseur;
import com.edugroupe.demo.repositories.IngredientFournisseurRepository;

@RestController
@RequestMapping("/ingredients_fournisseurs")
@CrossOrigin("http://localhost:4200")
public class IngredientFournisseurController {

	@Autowired
	IngredientFournisseurRepository ingrFournRep;
	
	@GetMapping("/{id:[0-9]+}")
	public Set<IngredientFournisseur> findByIngredientId(@PathVariable("id") int ingredientId) {
		return ingrFournRep.findByIngredientId(ingredientId);
	}
	
}
