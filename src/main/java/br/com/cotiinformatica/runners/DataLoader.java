package br.com.cotiinformatica.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.entities.Perfil;
import br.com.cotiinformatica.repositories.PerfilRepository;

@Service
public class DataLoader implements ApplicationRunner {

	@Autowired PerfilRepository perfilRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if(!perfilRepository.existsByNome("Gerente")) {
			var perfil = new Perfil();
			perfil.setNome("Gerente");
			perfilRepository.save(perfil);
		}
		
		if(!perfilRepository.existsByNome("Usuario")) {
			var perfil = new Perfil();
			perfil.setNome("Usuario");
			perfilRepository.save(perfil);
		}

		
	}

}
