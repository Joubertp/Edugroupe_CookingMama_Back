package com.edugroupe.demo.web;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.json.EtapeRecette;
import com.edugroupe.demo.repositories.RecetteRepository;

@Controller
@RequestMapping("recette")
public class RecetteController {

	@Autowired
	private RecetteRepository recetteRep;
	
	@RequestMapping("/greeting")
	public @ResponseBody String gretting() {
		return "Hello World!";
	}

	
	
	@GetMapping(value="/InsertTestBDD", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public Recette InsertTestData() {
		
		
		Recette r1 = new Recette();
		
		r1.setDateCreation(LocalDate.now());
		r1.setNom("Soupe au lardon");
		r1.setTempPreparation(50);
		r1.setTempCuisson(80);
		
		EtapeRecette er1_1 = new EtapeRecette();
		er1_1.setDescription("portez à ébulition l'eau dans un casserol");
		er1_1.setNumero(1);
		EtapeRecette er1_2 = new EtapeRecette();
		er1_2.setDescription("ajoutez les lardon");
		er1_2.setNumero(2);
		EtapeRecette er1_3 = new EtapeRecette();
		er1_3.setDescription("utilisez un mixeur pour broyer les lardon");
		er1_3.setNumero(3);
		
		System.out.println(r1);
		
		r1.setListeEtapes(new HashSet<>());
		r1.getListeEtapes().add(er1_1);
		r1.getListeEtapes().add(er1_2);
		r1.getListeEtapes().add(er1_3);
		
		
		recetteRep.save(r1);
		
		return r1;
	}
	
//	@GetMapping(value="/recettes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseBody
//	@CrossOrigin("http://localhost:4200")
//	public Iterable<Recette> findAll(){
//		return recetteRep.findAll();
//	}
	
	
	
}
