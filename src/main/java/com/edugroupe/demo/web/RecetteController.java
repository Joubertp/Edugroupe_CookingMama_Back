package com.edugroupe.demo.web;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edugroupe.demo.metiers.Ingredient;
import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.User;
import com.edugroupe.demo.repositories.RecetteRepository;
import com.edugroupe.demo.repositories.UserRepository;


@Controller
@RequestMapping("recettes")
public class RecetteController {

	@Autowired
	private RecetteRepository recetteRep;
	@Autowired
	private UserRepository userRep;

	
	@GetMapping(value = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<Recette> findById(@PathVariable("id") int id) {
		
		Optional<Recette> op = recetteRep.findById(id);
		if(op.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Recette r = op.get();
		r.toEraseInfiniteLoop();
		
		return new ResponseEntity<>(r,HttpStatus.OK);
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<Page<Recette>> find(	@PageableDefault(	page = 0, 
																	size = 10, 
																	sort = "dateDerniereEdition",
																	direction = Direction.DESC) 
																	Pageable page,
													@RequestParam("idIngredients") Optional<int[]> OpIdIngredients) {
		
		Page<Recette> recettes;
		if(OpIdIngredients.isPresent()) {
			int[] idIngredients = OpIdIngredients.get();
			Set<Ingredient> ingredients = Ingredient.creatListWith(idIngredients);
			Recette critere = new Recette(ingredients);			
			recettes = recetteRep.findByCritere(critere,page);			
		} else {			
			recettes = recetteRep.findAll(page);
			if(recettes.isEmpty()) 
				System.err.println("Rien ne vas plus ! La BDD est vide !");
		}		
				
		if(recettes.isEmpty()) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
		
		recettes.forEach(r -> r.toEraseAllDependancy());
		
		return new ResponseEntity<>(recettes,HttpStatus.OK);
	}


	@GetMapping(value = "auteur/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<Page<Recette>> findAllByAuthor(@RequestParam("auteurId") int auteurId,
			@PageableDefault(page = 0, size = 10) Pageable page) {
		User user = userRep.findById(auteurId).orElse(null);
		if (user == null)
			return new ResponseEntity<Page<Recette>>(HttpStatus.NOT_ACCEPTABLE);
		
		Page<Recette> recettes = recetteRep.findByAuteurId(user.getId(), page);
		recettes.forEach(r -> r.toEraseAllDependancy());
		
		return new ResponseEntity<Page<Recette>>(recettes,HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<Recette> creat(@RequestBody Recette r, @RequestParam("auteurId") int auteurId) {
		User user = userRep.findById(auteurId).orElse(null);
		if (user == null)
			return new ResponseEntity<Recette>(HttpStatus.NOT_ACCEPTABLE);
		r.setAuteur(user);
		r = recetteRep.save(r);
		r.toEraseAllDependancy();
		return new ResponseEntity<Recette>(r, HttpStatus.CREATED);
	}

	@PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<Recette> update(@RequestBody Recette r, @RequestParam("auteurId") int auteurId) {
		User user = userRep.findById(auteurId).orElse(null);
		if (user == null)
			return new ResponseEntity<Recette>(HttpStatus.NOT_ACCEPTABLE);
		r.setAuteur(user);
		r = recetteRep.save(r);
		r.toEraseAllDependancy();
		return new ResponseEntity<Recette>(r, HttpStatus.ACCEPTED);
	}

	@DeleteMapping(value = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<Map<String, Object>> deleteLivre(@PathVariable("id") int id) {
		if (recetteRep.existsById(id)) {
			recetteRep.deleteById(id);
			return new ResponseEntity<>(Collections.singletonMap("deletedId", id), HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	

}
