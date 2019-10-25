package com.edugroupe.demo.security;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edugroupe.demo.metiers.Login;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TypedQuery<Login> q = em.createQuery(" SELECT l FROM Login as l "
				+ " LEFT JOIN FETCH l.roles "
				+ " WHERE l.username = :username ", Login.class);
		q.setParameter("username", username);
		try {	
			Login login = q.getSingleResult();
			return new MyUserDetails(login);
		} catch(NoResultException nre) {
			throw new UsernameNotFoundException("Utilisateur inconnu");
		}
	}

}
