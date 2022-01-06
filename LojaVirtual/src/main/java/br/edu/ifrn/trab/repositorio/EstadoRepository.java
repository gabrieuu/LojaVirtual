package br.edu.ifrn.trab.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.trab.dominio.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	
	@Query("select e from Estado e where e.nome like %:nome%")
	List<Estado> findByNome(@Param("nome") String nome);
}
