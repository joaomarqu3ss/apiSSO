package br.com.cotiinformatica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, UUID> {
	
	@Query("""
			SELECT COUNT(u) > 0 
			FROM Usuarios u
			WHERE u.email = :email
			""")
	boolean existsByEmail(
			@Param("email") String email
			);
	
	@Query("""
			SELECT u FROM Usuarios u
			WHERE u.email = :email
			AND u.senha = :senha
			""")
	Usuarios find(
			@Param("email") String email,
			@Param("senha") String senha
			);
	
}
