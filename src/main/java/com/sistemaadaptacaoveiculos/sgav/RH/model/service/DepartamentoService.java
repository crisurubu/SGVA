package com.sistemaadaptacaoveiculos.sgav.RH.model.service;

import java.util.Optional;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Departamento;

public interface DepartamentoService {
	
	Departamento salvarDepartamento(Departamento departamento);
	void validarDepartamento(String departamento);
	Optional<Departamento> obterPorId(Long id);
	/*
	Departamento salvar (Departamento departamento);
	Departamento atualizar(Departamento departamento);
	void deletar(Departamento deprtamento);
	void validar(Departamento departamento);
	Optional<Departamento> obterPorId(Long id);
	List<Departamento> buscar(Departamento departamento);
	*/

}
