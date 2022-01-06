package br.edu.ifrn.trab.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.ifrn.trab.dominio.Arquivo;
import br.edu.ifrn.trab.repositorio.ArquivoRepository;
	
	@Controller
	public class DownloadArquivoController {
		
		@Autowired
		private ArquivoRepository arquivoRep;
		
		@GetMapping("/download/{idArquivo}")
		public ResponseEntity<?> downloadFile (
				@PathVariable Long idArquivo,
				@PathParam("salvar") String salvar
				){
			//Carregando arquivo do BD
			Arquivo arquivoBD = arquivoRep.findById(idArquivo).get();
			
			String texto = (salvar == null || salvar.equals("true")) ?
								"attachment; filename=\"" + arquivoBD.getNomeArquivo() + "\""
								: "inline; filename=\"" + arquivoBD.getNomeArquivo() + "\"";
			
				return ResponseEntity.ok().contentType(MediaType.parseMediaType(arquivoBD.getTipoArquivo()))
						.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, texto)
						.body(new ByteArrayResource(arquivoBD.getDados()));
		
		}
		
	}