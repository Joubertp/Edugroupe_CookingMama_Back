package com.edugroupe.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.repositories.RecetteRepository;

@Controller
public class RecetteController {

	@Autowired
	private RecetteRepository recetteRep;
	
	@RequestMapping("/gretting")
	public @ResponseBody String gretting() {
		return "Hello World!";
	}

	
//	@GetMapping(value="/recettes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseBody
//	@CrossOrigin("http://localhost:4200")
//	public Iterable<Recette> findAll(){
//		return recetteRep.findAll();
//	}
	
	
	
}
