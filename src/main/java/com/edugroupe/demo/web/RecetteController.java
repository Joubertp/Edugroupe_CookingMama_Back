package com.edugroupe.demo.web;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.projection.ProjectionFactory;
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
import com.edugroupe.demo.metiers.projections.RecetteView;
import com.edugroupe.demo.repositories.RecetteRepository;
import com.edugroupe.demo.repositories.UserRepository;

@Controller
@RequestMapping("recettes")
@CrossOrigin("http://localhost:4200")
public class RecetteController {

	@Autowired	private RecetteRepository recetteRep;
	@Autowired	private UserRepository userRep;

	@GetMapping(value = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<RecetteView> findById(@PathVariable("id") int id) {

		Optional<RecetteView> op = recetteRep.findById(id, RecetteView.class);
		if (op.isPresent())
			return new ResponseEntity<>(op.get(), HttpStatus.OK);

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<Page<RecetteView>> findByCritere(
			@PageableDefault(page = 0, size = 10, sort = "dateDerniereEdition", direction = Direction.DESC) Pageable page,
			@RequestParam("idIngredients") Optional<int[]> opIdIngredients,
			@RequestParam("nomRecette") Optional<String> opNomRecette) {

		Page<RecetteView> recettes;
		if (opIdIngredients.isPresent() || opNomRecette.isPresent()) {
			Recette criteres = new Recette();

			if (opIdIngredients.isPresent())
				setCriteresIngredients(criteres, opIdIngredients.get());
			if (opNomRecette.isPresent())
				setCriteresNomRecette(criteres, opNomRecette.get());

			recettes = recetteRep.findByCritere(criteres, page, RecetteView.class);
		} else {
			recettes = recetteRep.findAll(page, RecetteView.class);
			if (recettes.isEmpty())
				System.err.println("Rien ne vas plus ! La BDD est vide !");
		}

		if (recettes.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(recettes, HttpStatus.OK);
	}

	private void setCriteresIngredients(Recette criteres, int[] idIngredients) {
		Set<Ingredient> ingredients = Ingredient.creatListWith(idIngredients);
		criteres.setIngredients(ingredients);
	}

	private void setCriteresNomRecette(Recette criteres, String nomRecette) {
		criteres.setNom(nomRecette);
	}

	@GetMapping(value = "auteur/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<Page<RecetteView>> findAllByAuthor(@RequestParam("auteurId") int auteurId,
			@PageableDefault(page = 0, size = 10) Pageable page) {
		User user = userRep.findById(auteurId).orElse(null);
		if (user == null)
			return new ResponseEntity<Page<RecetteView>>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Page<RecetteView>>(recetteRep.findByAuteurId(user.getId(), page, RecetteView.class),
				HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
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
	public ResponseEntity<Map<String, Object>> deleteLivre(@PathVariable("id") int id) {
		if (recetteRep.existsById(id)) {
			recetteRep.deleteById(id);
			return new ResponseEntity<>(Collections.singletonMap("deletedId", id), HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
