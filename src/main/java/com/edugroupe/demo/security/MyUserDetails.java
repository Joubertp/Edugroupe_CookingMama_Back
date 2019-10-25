package com.edugroupe.demo.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.edugroupe.demo.metiers.Login;

public class MyUserDetails implements UserDetails {

	private Login login;
	
	public MyUserDetails(Login login) {
		super();
		this.login = login;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.login.getRoles()
							.stream()
							.map(role -> role.getRoleName())
							.map(roleName -> new SimpleGrantedAuthority(roleName))
							.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return login.getPassword();
	}

	@Override
	public String getUsername() {
		return login.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return login.isEnabled();
	}

}
