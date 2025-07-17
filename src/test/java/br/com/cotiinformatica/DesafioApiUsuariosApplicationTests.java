package br.com.cotiinformatica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequest;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponse;
import br.com.cotiinformatica.dtos.CriarUsuarioRequest;
import br.com.cotiinformatica.dtos.CriarUsuarioResponse;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DesafioApiUsuariosApplicationTests {

	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper mapper;
	
	public static String emailUsuario;
	public static String senhaUsuario;
	
	@Test
	@Order(1)
	void CriarUsuarioComSucesso() {
		try {
			var faker = new Faker();
			var request = new CriarUsuarioRequest();
			request.setNome(faker.name().fullName());
			request.setEmail(faker.internet().emailAddress());
			request.setSenha("@Teste12345");
			
			var result = mockMvc.perform(post("/api/v1/usuarios/criar")
						.contentType("application/json")
						.content(mapper.writeValueAsString(request)))
						.andExpect(status().isOk())
						.andReturn();
			
			var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			var response = mapper.readValue(content, CriarUsuarioResponse.class);
			
			assertNotNull(response.getId());
			assertEquals(response.getNome(), request.getNome());
			assertEquals(response.getEmail(), request.getEmail());
			assertNotNull(response.getDataHoraCriacao());
			
			emailUsuario = request.getEmail();
			senhaUsuario = request.getSenha();
					
		}
		catch (Exception e) {
			fail("Teste falhou: " + e.getMessage());
		}
		
	}
	
	@Test
	@Order(2)
	void AutenticarUsuariocomSucesso() {
		try {
			var faker = new Faker();
			var request = new AutenticarUsuarioRequest();
			request.setEmail(emailUsuario);
			request.setSenha(senhaUsuario);
			
			var result = mockMvc.perform(post("/api/v1/usuarios/autenticar")
					.contentType("application/json")
					.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk())
					.andReturn();
			
			var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			var response  = mapper.readValue(content, AutenticarUsuarioResponse.class);
			
			assertNotNull(response.getId());
			assertNotNull(response.getNome());
			assertEquals(response.getEmail(), request.getEmail());
			assertNotNull(response.getPerfil());
			assertNotNull(response.getDataHoraAcesso());
			assertNotNull(response.getDataHoraExpiracao());
			assertNotNull(response.getAcessToken());			
		}
		catch (Exception e) {
			fail("Teste falhou " + e.getMessage());
		}
		
	}

}
