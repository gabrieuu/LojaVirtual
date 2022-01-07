/*
 * Objetivo: Está classe tem o objetivo de cadastrar e editar produto.
 * 
 * @author Nellyson Felipe (nellysonfelipe@gmail.com)
 * 
 * data de criação 22/09/2021
 */

package br.edu.ifrn.trab.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.trab.dominio.Produto;
import br.edu.ifrn.trab.repositorio.ProdutoRepositorio;

/*
 * url responde a anotação de controle da página 
 */
@Controller
@RequestMapping("/produtos")
public class CadastroProdutoController {
	
	//Repositorio de produto
	@Autowired
	private ProdutoRepositorio prodRep;
	
	
	@GetMapping("/cadastro")
	public String entrar(ModelMap model) {
		model.addAttribute("produto", new Produto());
		return "produtos/Produtos";
	}
	
	/*
	 * url responde a o metodo salvar produto
	 */
	@PostMapping("/salvar")
	public String salvar(Produto produto, RedirectAttributes attr, HttpSession sessao, ModelMap model) {
		
		/*
		 * @param validar String  - recebe os dados de validação de produto
		 * @return retorna a url da pagina com a mensagem de erro
		 */
		List<String> validar = validarDados(produto);
		String cont = validar.size()+"";
		if(!validar.isEmpty()){
			model.addAttribute("msgsErro",validar);
			model.addAttribute("tamanho",cont);
			return "produtos/Produtos";
		}
		/*
		 * caso esteja tudo certo com as informações o repositório salva e envia a mensagem de sucesso
		 * @return redire  ciona a pagina de cadastro
		 */
			prodRep.save(produto);
			attr.addFlashAttribute("msgSucesso","Operação realizada com sucesso!");
		return "redirect:/produtos/cadastro";
	}
	
	/*
	 * url responde a o metodo editar produto
	 * @param a url recebe um id de produto
	 */
	@Transactional(readOnly = true)
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(
			@PathVariable("id") Integer idProduto,
			ModelMap model, HttpSession sessao
			) {
		/*
		 * @param u Produto - recebe uma lista de produtos pelo id
		 * @return se encontrar um produto referente ao id recebido retorna a pagina cadastro que fica resposanvel pela edição.
		 */
		Produto u = prodRep.findById(idProduto).get();
		
		model.addAttribute("produto", u);
		
		return "produtos/Produtos";
	}
	/*
	 * @return retorna uma lista de categoria que o usuário seleciona na hora de cadastrar produto
	 */
	@ModelAttribute("categoria")
	public List<String> getCategoria(){
		return Arrays.asList("Vestuário", "Calçados", "Artigos Esportivos", "Decoração", "Informática","SmartPhones", "Outros");
	}
	
	private List<String> validarDados(Produto produto){
		List<String> msg = new ArrayList<>();
		if(produto.getNome() == null || produto.getNome().isEmpty()){
			msg.add("informe o nome do produto!");
		}
		if(produto.getCategoria() == null || produto.getCategoria().isEmpty()){
			msg.add("informe a categoria!");
		}
		if(produto.getPreco() == null || produto.getPreco().isEmpty()){
			msg.add("informe o preco!");
		}
		if(produto.getMarca() == null || produto.getMarca().isEmpty()){
			msg.add("informe a marca!");
		}
		if(produto.getDescricao() == null || produto.getDescricao().isEmpty()){
			msg.add("informe a descricao do seu produto!");
		}
		return msg;
	}
}