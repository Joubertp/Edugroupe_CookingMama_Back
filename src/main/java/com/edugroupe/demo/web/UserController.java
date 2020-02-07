package com.edugroupe.demo.web;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edugroupe.demo.metiers.User;
import com.edugroupe.demo.metiers.projections.RecetteView;
import com.edugroupe.demo.repositories.UserRepository;

@Controller
@RequestMapping("users")
@CrossOrigin("http://localhost:4200")
public class UserController {

	@Autowired	private UserRepository userRep;

	@GetMapping
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<Page<User>> listUsers(@PageableDefault(
												page = 0, size = 10) 
												Pageable page){
		
		Page<User> users = userRep.findAll(page);
		
		if (users.isEmpty())
			System.err.println("Rien ne vas plus ! La BDD est vide !");
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

//	@GetMapping
//	public ResponseEntity<User> findById(@PathVariable("id") int id) {
//		Optional<User> opUser = userRep.findById(id);
//		
//		if(!opUser.isPresent())
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//		
//		return new ResponseEntity<User>(opUser.get(),HttpStatus.OK);
//	}

}
