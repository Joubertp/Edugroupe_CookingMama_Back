package com.edugroupe.demo.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import com.edugroupe.demo.metiers.IngredientRecette;
import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.User;
import com.edugroupe.demo.metiers.DTO.IngredientRecetteDTO;
import com.edugroupe.demo.metiers.DTO.RecetteDTO;
import com.edugroupe.demo.metiers.projections.RecetteView;
import com.edugroupe.demo.repositories.IngredientRecetteRepository;
import com.edugroupe.demo.repositories.IngredientRepository;
import com.edugroupe.demo.repositories.RecetteRepository;
import com.edugroupe.demo.repositories.UserRepository;

@Controller
@RequestMapping("recettes")
@CrossOrigin("http://localhost:4200")
public class RecetteController {

	@Autowired	private RecetteRepository recetteRep;
	@Autowired	private UserRepository userRep;
	@Autowired	private IngredientRecetteRepository ingredientRecetteRep;
	@Autowired	private IngredientRepository ingredientRep;

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
		criteres.lazySetIngredients(ingredients);
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

	@PutMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Object> update(@RequestBody RecetteDTO rDTO,
											Authentication auth) {
		Recette newR = rDTO.getRecette();		
		Optional<Recette> recOp = recetteRep.findById(newR.getId());
		
		if (!recOp.isPresent())
			return new ResponseEntity<>("Recette inconnue",HttpStatus.NOT_FOUND);
		
		Recette toUpdateR = recOp.get();
		newR.setAuteur(toUpdateR.getAuteur());
		
		//L'utilisateur doit être admin, ou auteur de la recette, pour être digne de la modifier
		if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) &&
				!toUpdateR.getAuteur().getUsername().equals(auth.getName()))
			return new ResponseEntity<>("Vous n'étes pas digne",HttpStatus.UNAUTHORIZED);
			
		if(rDTO.getIngredients().isPresent()) {	
			//On gardes les ids d'IngrediantRecette, Ils serviront de comparateur avec l'anciene
			//liste d'IngrediantRecette
			List<Integer> idIngRes = new ArrayList<Integer>();
			for(IngredientRecetteDTO newI : rDTO.getIngredients().get() ) {
				Optional<IngredientRecette> ingredientOp = ingredientRecetteRep.findById(newI.getId());
				IngredientRecette toUpdateI;
				if(ingredientOp.isPresent()) toUpdateI = ingredientOp.get();
				else toUpdateI = new IngredientRecette(0,0,null,toUpdateR,null);
				toUpdateI.setQuantite(newI.getQuantite());
				toUpdateI.setText(newI.getText());
				toUpdateI.setIngredient(ingredientRep.findById(newI.getIdIngredient()).orElse(null));
				System.out.println("toUpdateI : "+toUpdateI);
				System.out.println("toUpdateI ingredient: "+toUpdateI.getIngredient());
				System.out.println("new I : "+newI.getText());
				System.out.println("new I : "+newI.getQuantite());
				System.out.println("new I : "+newI.getIdIngredient());
				if(newI.getIdIngredient() == 0)
					return new ResponseEntity<>("Error Ingredient null",HttpStatus.NOT_ACCEPTABLE);
				toUpdateI = ingredientRecetteRep.save(toUpdateI);
				idIngRes.add(toUpdateI.getId());
			}			
			
			//Supression des Ingredient de Recette, ne figurant pas dans la nouvelle liste
			Set<IngredientRecette> listIngredients= toUpdateR.getIngredients();
			for(IngredientRecette  toCheck: listIngredients) {
				if(!idIngRes.contains(toCheck.getId()))
					ingredientRecetteRep.delete(toCheck);
			}
			
		}
					
		newR = recetteRep.save(newR);
		return new ResponseEntity<>(newR, HttpStatus.ACCEPTED);
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
