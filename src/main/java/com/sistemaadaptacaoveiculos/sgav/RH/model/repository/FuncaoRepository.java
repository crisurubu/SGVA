package com.sistemaadaptacaoveiculos.sgav.RH.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Funcao;

public interface FuncaoRepository extends JpaRepository<Funcao, Long> {
	
	Optional<Funcao> findByFuncao(String funcao);
	boolean existsByfuncao(String funcao);

}
