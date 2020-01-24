package com.edugroupe.demo.security;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edugroupe.demo.metiers.User;
import com.edugroupe.demo.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired private UserRepository userRep;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> u = userRep.findByUsernameWithRole(username);

		if (!u.isPresent())
			throw new UsernameNotFoundException("login/password invalid");

		return new MyUserDetails(u.get());
	}

}
