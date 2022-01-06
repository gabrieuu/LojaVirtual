package br.edu.ifrn.trab.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.trab.dominio.Produto;
public interface ProdutoRepositorio extends JpaRepository<Produto, Integer> {

	@Query("select p from Produto p where p.nome like %:nome%")
	List<Produto> FindByName (@Param("nome") String nome);

}
