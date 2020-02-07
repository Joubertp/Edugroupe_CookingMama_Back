package com.edugroupe.demo.config;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edugroupe.demo.metiers.Role;
import com.edugroupe.demo.metiers.User;
import com.edugroupe.demo.repositories.RoleRepository;
import com.edugroupe.demo.repositories.UserRepository;

@Service
public class DatabaseContentInitialiser implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(userRepository.count() == 0) {
			System.out.println("La base est vide, création des utilisateurs et roles par défaut");
			Role admin = roleRepository.save(new Role(0, "ROLE_ADMIN"));
			Role user = roleRepository.save(new Role(0, "ROLE_USER"));
			Role boutique = roleRepository.save(new Role(0, "ROLE_BOUTIQUE"));
			
			User administrator = new User(0, "admin", passwordEncoder.encode("admin"), true);
			administrator.getRoles().add(admin);
			administrator.getRoles().add(user);
			userRepository.save(administrator);
			
			User peon = new User(0,"Peon",passwordEncoder.encode(""), true);
			peon.getRoles().add(user);
			userRepository.save(peon);
			
			User superKiwi = new User(0,"superKiwi",passwordEncoder.encode("superKiwi"), true);
			superKiwi.getRoles().add(user);
			userRepository.save(superKiwi);
			
			User joker = new User(0,"Joker",passwordEncoder.encode("HAHA"), true);
			joker.getRoles().add(user);
			userRepository.save(joker);
			
			User batman = new User(0,"Batman",passwordEncoder.encode("JeSuisBatman"), true);
			batman.getRoles().add(user);
			userRepository.save(batman);
			
			User harlay = new User(0,"Harley Quinn",passwordEncoder.encode("puddin"), true);
			harlay.getRoles().add(user);
			userRepository.save(harlay);
			
		}
		else {
			System.out.println("La base contient déjà des utilisateurs");
		}
		
	}
	
}
