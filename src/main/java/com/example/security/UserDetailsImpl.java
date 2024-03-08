package com.example.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 6793590474713548621L;
	
	private String username;
	private String password;
	private String name;
	// Collection<? extends GrantedAuthority> 그하위까지 가질수 있다.
	private Collection<? extends GrantedAuthority> authorities;
	
	// userDetailsServiceImpl 순서가 맞아야한다.!
	public UserDetailsImpl(String username, String password, String name, Collection<? extends GrantedAuthority> authorities ) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.authorities = authorities;
	}

	@Override
	// 고유번호를 반환하는 메소드
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}
	public String getName() {
		return name;
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
		// 계정활성화 여부
		return true;
	}

}
