package com.edugroupe.demo.web;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import com.edugroupe.demo.metiers.Recette;
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
		Optional<Recette> recette = recetteRepository.findById(recetteId);
		if(!recette.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ImageRecette image = new ImageRecette(0,
										file.getOriginalFilename(),
										file.getContentType(),
										"");
		
		try {
			imageRecetteRepository.saveImageFile(image, file.getInputStream());
			image.setRecette(recette.get());
			image = imageRecetteRepository.save(image);
			return new ResponseEntity<ImageRecette>(image, HttpStatus.CREATED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping(value = "/{id:[0-9]+}/data")
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<FileSystemResource> imageData(@PathVariable("id") int id) {
		
		Optional<ImageRecette> oimage = imageRecetteRepository.findById(id);
		if(!oimage.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Optional<File> ofile = imageRecetteRepository.getImageFile(oimage.get().getStorageId());
		if(!ofile.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(oimage.get().getContentType()));
		headers.setContentLength(ofile.get().length());
		headers.setContentDispositionFormData("attachment", oimage.get().getFilename());
		
		ResponseEntity<FileSystemResource> re = new ResponseEntity<FileSystemResource>(
					new FileSystemResource(ofile.get()),
					headers,
					HttpStatus.OK
				);
		
		return re;		
	}
}
