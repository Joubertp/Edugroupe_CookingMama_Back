package com.edugroupe.demo.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.edugroupe.demo.constante.CathIngre;
import com.edugroupe.demo.metiers.Ingredient;
import com.edugroupe.demo.metiers.IngredientRecette;
import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.User;
import com.edugroupe.demo.metiers.json.EtapeRecette;
import com.edugroupe.demo.repositories.IngredientRecetteRepository;
import com.edugroupe.demo.repositories.IngredientRepository;
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
		return recetteRep.findById(id)
				.map( r -> new ResponseEntity<>(r,HttpStatus.OK))
				.orElse( new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
