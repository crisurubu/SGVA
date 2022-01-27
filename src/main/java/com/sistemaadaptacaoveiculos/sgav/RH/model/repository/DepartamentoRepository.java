package com.sistemaadaptacaoveiculos.sgav.RH.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long>{
	
	
	boolean existsByDepartamento(String departamento);

}
