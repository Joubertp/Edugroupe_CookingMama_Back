package com.edugroupe.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edugroupe.demo.metiers.Ingredient;
import com.edugroupe.demo.metiers.projections.IngredientIdAndName;
import com.edugroupe.demo.repositories.IngredientRepository;


@RestController
@RequestMapping("/ingredients")
@CrossOrigin("http://localhost:4200")
public class IngredientController {

	@Autowired IngredientRepository ingredientRep;
	
	@GetMapping("/nomList")
	public List<IngredientIdAndName> getList(){
		return ingredientRep.findAll(IngredientIdAndName.class,Sort.by("nom"));
	}
	
	@GetMapping("/{id:[0-9]+}")
	public ResponseEntity<Ingredient> getIngredientById(@PathVariable("id") int id) {
		return ingredientRep.findById(id)
							.map(i -> new ResponseEntity<Ingredient>(i, HttpStatus.OK))
							.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
