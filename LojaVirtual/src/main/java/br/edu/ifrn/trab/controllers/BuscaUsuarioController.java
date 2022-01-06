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
	
	@Autowired
	private UsuarioRepositorio usuariorep;
	
	@GetMapping("/busca")
	public String entrarBusca() {
		return "usuario/busca";
	}

	@GetMapping("/buscar")
	public String Busca(@RequestParam(name = "nome", required = false) String nome, HttpSession sessao,
			ModelMap model) {
		
		List<Usuario> usuariosEncontrados = usuariorep.FindByName(nome);
		model.addAttribute("usuariosEncontrados", usuariosEncontrados);

		return "usuario/busca";
	}
	
	@Transactional(readOnly = false)
	@GetMapping("/remover/{id}")
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
