package br.com.cotiinformatica.components;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

@Component
@Data
public class JwtTokenComponent {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public Date getExpirationDate() {
		var dataAtual = new Date();
		return new Date(dataAtual.getTime() + expiration);
	}
	
	public String generateToken(UUID id, String perfil) {
		
		var dataExpiracao = getExpirationDate();
		return Jwts.builder()
				.setSubject(id.toString())
				.claim("perfil",perfil)
				.setIssuedAt(new Date())
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
				
		
	}
	
}
