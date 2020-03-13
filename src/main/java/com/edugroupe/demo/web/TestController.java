package com.edugroupe.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edugroupe.demo.metiers.Ingredient;
import com.edugroupe.demo.metiers.projections.RecetteView;
import com.edugroupe.demo.repositories.RecetteRepository;
import com.edugroupe.demo.utils.DataGenerator;

@Controller
@RequestMapping("test")
public class TestController {

	@Autowired
	private DataGenerator dataGenerator;
	
	@Autowired
	private RecetteRepository recetteRep;

	
	@RequestMapping("/greeting")
	public @ResponseBody String gretting() {
		return "Hello World!";
	}

	/*
	 * Inser une liste de donnee de test
	 */
	@GetMapping(value = "/insertTestBDD", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public List<Object> insertTestData() {

		System.out.println(recetteRep);
		
		List<Object> toReturn = new ArrayList<>();
		List<Ingredient> ingreList = dataGenerator.creatAndSaveDataIngredient();
		toReturn.add(ingreList);
		toReturn.add(dataGenerator.creatAndSaveDataRecette());
		toReturn.add(dataGenerator.creatAndSaveDataFournisseur(ingreList));
		
		return toReturn;
	}
	

	@GetMapping(value = "/projection", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
    public List<Object> testProjection() {
		List<Object> toReturn = new ArrayList<>();
		
		toReturn.add(recetteRep.findById(1));
		toReturn.add(recetteRep.findById(1,RecetteView.class));
		return toReturn;		
		
	}
}
