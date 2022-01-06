package br.edu.ifrn.trab.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import  br.edu.ifrn.trab.dominio.Arquivo;

public interface ArquivoRepository  extends JpaRepository<Arquivo, Long>{

}
