package com.sistemaadaptacaoveiculos.sgav.RH.model.service;

import java.util.List;
import java.util.Optional;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Funcionario;

public interface FuncionarioService {
	
	
	Funcionario salvar(Funcionario funcionario);
	Funcionario atualizar(Funcionario funcionario);
	void deletar(Funcionario funcionario);
	void validar(Funcionario funcionario);
	Optional<Funcionario> obterPorCpf(String cpf);
	Optional<Funcionario> obterPorId(Long id);
	List<Funcionario> buscar(Funcionario funcionario);
	void validarCpf(String cpf);

}
