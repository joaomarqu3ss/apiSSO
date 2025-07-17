package br.com.cotiinformatica.interfaces;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequest;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponse;
import br.com.cotiinformatica.dtos.CriarUsuarioRequest;
import br.com.cotiinformatica.dtos.CriarUsuarioResponse;

public interface UsuarioService {
	
	CriarUsuarioResponse cadastrar(CriarUsuarioRequest request);
	
	AutenticarUsuarioResponse autenticar(AutenticarUsuarioRequest request);
}
