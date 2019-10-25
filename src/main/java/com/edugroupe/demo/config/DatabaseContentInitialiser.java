package com.edugroupe.demo.config;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edugroupe.demo.metiers.Login;
import com.edugroupe.demo.metiers.Role;
import com.edugroupe.demo.repositories.LoginRepository;
import com.edugroupe.demo.repositories.RoleRepository;

@Service
public class DatabaseContentInitialiser implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(loginRepository.count() == 0) {
			System.out.println("La base est vide, création des utilisateurs et roles par défaut");
			Role admin = roleRepository.save(new Role(0, "ADMIN"));
			Role user = roleRepository.save(new Role(0, "USER"));
			
			Login administrator = new Login(0, "admin", passwordEncoder.encode("admin"), true);
			administrator.setRoles(new HashSet<>());
			administrator.getRoles().add(admin);
			administrator.getRoles().add(user);
			loginRepository.save(administrator);
		}
		else {
			System.out.println("La base contient déjà des utilisateurs");
		}
		
	}
	
}
