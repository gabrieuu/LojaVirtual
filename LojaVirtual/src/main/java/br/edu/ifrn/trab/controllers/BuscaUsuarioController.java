/*
 * 
 * Objetivo:Essa classe tem como objetico implemtentar as
 * funcionalidades de cadastrar e editar de um usuario.
 * 
 * @author Gabriel Barbosas da Silva (barbosa.silva@escolar.ifrn.edu.br)
 * 
 * Data de criação: 20 de setembro 2021
 * 
 * 
 * */

package br.edu.ifrn.trab.controllers;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.trab.dominio.Usuario;
import br.edu.ifrn.trab.repositorio.UsuarioRepositorio;

@Controller
@RequestMapping("/usuarios")
public class BuscaUsuarioController {
	
	//usuarioRep: repositorio do Banco de dados de usuario
	@Autowired
	private UsuarioRepositorio usuariorep;
	
	
	@GetMapping("/busca")
	/*metodo para entrar na pagina de busca
	 * @return String - retorna a pagina busca
	 * */
	public String entrarBusca() {
		return "usuario/busca";
	}

	@GetMapping("/buscar")
	/*
	 * metodo de busca, recebe um nome e pesquisa no BD todos os usuarios que possuem nome igual ou semalhante
	 * @param nome String  - recebe o valor do nome do usuario que foi escrito no texto
	 * @return String - retorna a pagina de busca 
	 * */
	public String Busca(@RequestParam(name = "nome", required = false) String nome, HttpSession sessao,
			ModelMap model) {
		
		List<Usuario> usuariosEncontrados = usuariorep.FindByName(nome);
		model.addAttribute("usuariosEncontrados", usuariosEncontrados);

		return "usuario/busca";
	}
	
	@Transactional(readOnly = false)
	@GetMapping("/remover/{id}")
	/*
	 * o {id} é o valor identificador que vai ser capturado quando for remover o usuario
	 * esse metodo remove o usuario escolhidos
	 * @return String - retorna um redirecionamento para a propria pagina
	 * */
	public String remover(
			@PathVariable("id") Integer idUsuario,
			HttpSession sessao,
			RedirectAttributes attr
			) {
		
		usuariorep.deleteById(idUsuario);
		
		attr.addFlashAttribute("msgSucesso", "Usuário removido com sucesso!");
		
		return "redirect:/usuarios/buscar";
	}
	
}
