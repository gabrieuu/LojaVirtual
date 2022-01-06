package br.edu.ifrn.trab.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.trab.dominio.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
	
	@Query("select u from Usuario u where u.nome like %:nome%")
	List<Usuario> FindByName (@Param("nome") String nome);

	@Query("select u from Usuario u where u.email like %:email%")
	Optional<Usuario> findByEmail(@Param("email") String email);
	

}
