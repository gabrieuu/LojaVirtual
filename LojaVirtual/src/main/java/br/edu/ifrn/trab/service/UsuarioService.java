package br.edu.ifrn.trab.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.trab.dominio.Usuario;
import br.edu.ifrn.trab.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService implements UserDetailsService{

	@Autowired
	private UsuarioRepositorio repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

		return new User(
				
		usuario.getEmail(),
		usuario.getSenha(),
		AuthorityUtils.createAuthorityList(usuario.getPerfil())
		
		);		
	}
}
