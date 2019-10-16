package com.edugroupe.demo.web;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edugroupe.demo.metiers.Ingredient;
import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.json.EtapeRecette;
import com.edugroupe.demo.metiers.json.IngredientRecette;
import com.edugroupe.demo.repositories.IngredientRepository;
import com.edugroupe.demo.repositories.RecetteRepository;

@Controller
@RequestMapping("recette")
public class RecetteController {

	@Autowired
	private RecetteRepository recetteRep;
	@Autowired
	private IngredientRepository ingredientRep;
	
	@RequestMapping("/greeting")
	public @ResponseBody String gretting() {
		return "Hello World!";
	}

	
	
	@GetMapping(value="/InsertTestBDD", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public Recette InsertTestData() {
				
		Ingredient i1 = new Ingredient(0, "Lardon","Viande", "C'est pas pour les vegan ", "C'est meilleur avec des pates");
		
		Ingredient savedI = ingredientRep.save(i1);
	
		Recette r1 = new Recette();
		
		r1.setDateCreation(LocalDate.now());
		r1.setNom("Soupe au lardon");
		r1.setTempPreparation(50);
		r1.setTempCuisson(80);
		
		IngredientRecette ir1 = new IngredientRecette();
		ir1.setId(savedI.getId());
		ir1.setValeur("500g");
		
		
		
		Set<IngredientRecette> listeIngredients = Stream.of(ir1).collect(Collectors.toSet());
		r1.setListeIngredients(listeIngredients);
		
		
		EtapeRecette er1_1 = new EtapeRecette(1);
		er1_1.setDescription("portez à ébulition l'eau dans un casserol");
		EtapeRecette er1_2 = new EtapeRecette(2);
		er1_2.setDescription("ajoutez les lardon");
		EtapeRecette er1_3 = new EtapeRecette(3);
		er1_3.setDescription("utilisez un mixeur pour broyer les lardon");
			
		Set<EtapeRecette> listeEtape = Stream.of(er1_1,er1_2,er1_3).collect(Collectors.toSet());		
		r1.setListeEtapes(listeEtape);
		
		
		Recette savedR = recetteRep.save(r1);
				
		return savedR;
	}
	
//	@GetMapping(value="/recettes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseBody
//	@CrossOrigin("http://localhost:4200")
//	public Iterable<Recette> findAll(){
//		return recetteRep.findAll();
//	}
	
	
	
}
