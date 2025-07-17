package br.com.cotiinformatica.services;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.components.CryptoComponent;
import br.com.cotiinformatica.components.JwtTokenComponent;
import br.com.cotiinformatica.dtos.AutenticarUsuarioRequest;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponse;
import br.com.cotiinformatica.dtos.CriarUsuarioRequest;
import br.com.cotiinformatica.dtos.CriarUsuarioResponse;
import br.com.cotiinformatica.entities.Usuarios;
import br.com.cotiinformatica.interfaces.UsuarioService;
import br.com.cotiinformatica.repositories.PerfilRepository;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired PerfilRepository perfilRepository;
	@Autowired UsuarioRepository usuarioRepository;
	@Autowired ModelMapper mapper;
	@Autowired JwtTokenComponent jwtTokenComponent;
	@Autowired CryptoComponent cryptoComponent;
	
	@Override
	public CriarUsuarioResponse cadastrar(CriarUsuarioRequest request) {
		
		var usuario = new Usuarios();
		usuario.setNome(request.getNome());
		usuario.setEmail(request.getEmail());
		usuario.setSenha(cryptoComponent.encrypt(request.getSenha()));
		usuario.setPerfil(perfilRepository.findByNome("Usuario"));
		usuarioRepository.save(usuario);
		
		var response = new CriarUsuarioResponse();
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		response.setDataHoraCriacao(LocalDateTime.now());
	
		return response;
	}

	@Override
	public AutenticarUsuarioResponse autenticar(AutenticarUsuarioRequest request) {
		
		var usuario = usuarioRepository.find(request.getEmail(), cryptoComponent.encrypt(request.getSenha()));
		if(usuario == null) {
			throw new IllegalArgumentException("Usuário não encontrado.");
		}
		var response = new AutenticarUsuarioResponse();
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		response.setPerfil(usuario.getPerfil().getNome());
		response.setDataHoraAcesso(LocalDateTime.now());
		response.setAcessToken(jwtTokenComponent.generateToken(usuario.getId(), usuario.getPerfil().getNome()));
		response.setDataHoraExpiracao(jwtTokenComponent.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		
		return response;
	}

}
