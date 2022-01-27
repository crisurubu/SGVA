package com.sistemaadaptacaoveiculos.sgav.RH.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
	
	boolean existsByCpf(String cpf);
	Optional<Funcionario> findByCpf(String cpf);
}
