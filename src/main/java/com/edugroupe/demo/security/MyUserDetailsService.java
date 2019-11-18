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

import com.edugroupe.demo.metiers.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TypedQuery<User> q = em.createQuery(" SELECT u FROM User as u "
				+ " LEFT JOIN FETCH u.roles "
				+ " WHERE u.username = :username ", User.class);
		q.setParameter("username", username);
		try {	
			User user = q.getSingleResult();
			return new MyUserDetails(user);
		} catch(NoResultException nre) {
			throw new UsernameNotFoundException("Utilisateur inconnu");
		}
	}

}
