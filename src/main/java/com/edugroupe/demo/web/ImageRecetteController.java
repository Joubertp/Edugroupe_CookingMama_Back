package com.edugroupe.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edugroupe.demo.metiers.ImageRecette;
import com.edugroupe.demo.repositories.ImageRecetteRepository;
import com.edugroupe.demo.repositories.RecetteRepository;

@Controller
@RequestMapping("/images_recette")
@CrossOrigin("http://localhost:4200")
public class ImageRecetteController {
	
	@Autowired
	private ImageRecetteRepository imageRecetteRepository;
	
	@Autowired
	private RecetteRepository recetteRepository;
	
	@PostMapping(value="/upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")		
	public ResponseEntity<ImageRecette> upload(@RequestParam("file") MultipartFile file,
											   @RequestParam("recetteId") int recetteId){
		return null;
	}
	
	@GetMapping(value = "/{id:[0-9]+}/data")
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<FileSystemResource> imageData(@PathVariable("id")int id) {
		
		return null;
	}
}
