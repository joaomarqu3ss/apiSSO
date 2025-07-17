package br.com.cotiinformatica.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Perfil {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column
	private UUID id;
	
	@Column(length = 25, nullable = false, unique = true)
	private String nome;
	
	@OneToMany(mappedBy = "perfil")
	private List<Usuarios> usuarios;
}
