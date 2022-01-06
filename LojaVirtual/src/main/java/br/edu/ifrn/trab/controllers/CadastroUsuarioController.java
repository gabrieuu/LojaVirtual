package br.edu.ifrn.trab.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.trab.dominio.Arquivo;
import br.edu.ifrn.trab.dominio.Estado;
import br.edu.ifrn.trab.dominio.Usuario;
import br.edu.ifrn.trab.repositorio.ArquivoRepository;
import br.edu.ifrn.trab.repositorio.EstadoRepository;
import br.edu.ifrn.trab.repositorio.UsuarioRepositorio;


@Controller
@RequestMapping("/usuarios")
public class CadastroUsuarioController {
	
	@Autowired
	private UsuarioRepositorio usuarioRep;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ArquivoRepository arquivoRepository;
	
	@GetMapping("/cadastro")
	public String entrar(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastro";
	}

	
	@PostMapping("/salvar")
	@Transactional(readOnly = false)
	public String salvar(Usuario usuario, RedirectAttributes attr, BindingResult result,  
			@RequestParam("file") MultipartFile arquivo,
			 HttpSession sessao, ModelMap model) {
		
		List<String> validar = validarDados(usuario);
		if(!validar.isEmpty()){
			model.addAttribute("msgsErro", validar);
			return "usuario/cadastro";
		}
		
		try {
			
			if(arquivo != null && !arquivo.isEmpty()) {
				//Normalizando o nome do arquivo
				String nomeArquivo = org.springframework.util.StringUtils.cleanPath(arquivo.getOriginalFilename());
				
				Arquivo arquivoBD = new Arquivo(null, nomeArquivo, arquivo.getContentType(), arquivo.getBytes());
				
				//salvando foto
				arquivoRepository.save(arquivoBD);
				
				if(usuario.getFoto() != null && usuario.getFoto().getId() != null 
						&& usuario.getFoto().getId() > 0) {
					arquivoRepository.delete(usuario.getFoto());
					
				}
				
				usuario.setFoto(arquivoBD);
				
			}else {
				usuario.setFoto(null);
			}
			
			//Senha criptografada
			String senhaCriptografada = 
					new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhaCriptografada);
			
			
			//cadastro e edição
			usuarioRep.save(usuario);
			attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/usuarios/cadastro";
	
	}

	
	@Transactional(readOnly = true)
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idUsuario, ModelMap model, HttpSession sessao) {

		Usuario u = usuarioRep.findById(idUsuario).get();
		
		model.addAttribute("usuario", u);
		
		return "usuario/cadastro";
	}
	
	@GetMapping("/autocompleteEstados")
	@Transactional(readOnly = true)
	@ResponseBody
	public List<br.edu.ifrn.trab.dto.AutocompleteDTO> autocompleteProfissoes(
			@RequestParam("term") String termo){
		
		List<Estado> estado = 
				estadoRepository.findByNome(termo);
		List<br.edu.ifrn.trab.dto.AutocompleteDTO> resultados = new ArrayList<>();
	
		estado.forEach(p -> resultados.add(
					new br.edu.ifrn.trab.dto.AutocompleteDTO(p.getNome(), p.getId())
				));
		
		return resultados;
	}
	
	
	
	private List<String> validarDados(Usuario usuario){
		List<String> msg = new ArrayList<>();

		if(usuario.getNome() == null || usuario.getNome().isEmpty()){
			msg.add("Preencha o campo nome!");
		}if(usuario.getEmail() == null || usuario.getEmail().isEmpty()){
			msg.add("Preencha o campo email!");
		}if(usuario.getSenha() == null || usuario.getSenha().isEmpty()){
			msg.add("Preencha o campo senha!");
		}
		return msg;
	}
	
}
