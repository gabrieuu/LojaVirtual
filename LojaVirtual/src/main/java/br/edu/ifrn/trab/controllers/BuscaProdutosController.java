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

import br.edu.ifrn.trab.dominio.Produto;
import br.edu.ifrn.trab.repositorio.ProdutoRepositorio;

@Controller
@RequestMapping("/produtos")
public class BuscaProdutosController {
	
	@Autowired
	private ProdutoRepositorio prodRep;
	
	@GetMapping("/busca")
	public String entrarBusca() {
		return "produtos/buscaProdutos";
	}
	
	
	@GetMapping("/buscar")
	public String buscar(
			@RequestParam(name="nome", required=false) String nome,
			HttpSession sessao, 
			ModelMap model
			) {
		
		List<Produto> produtosEncontrados = prodRep.FindByName(nome);
		model.addAttribute("produtosEncontrados", produtosEncontrados);
		
		return "produtos/buscaProdutos";
	}
	
	@Transactional(readOnly = false)
	@GetMapping("/remover/{id}")
	public String remover(
			@PathVariable("id") Integer idProduto,
			HttpSession sessao,
			RedirectAttributes attr
			) {
		
		prodRep.deleteById(idProduto);
		attr.addFlashAttribute("msgSucesso", "Produto removido com sucesso!");
		
		return "redirect:/produtos/buscar";
	}
	
}

















