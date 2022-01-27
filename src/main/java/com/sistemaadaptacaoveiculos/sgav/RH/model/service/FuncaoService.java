package com.sistemaadaptacaoveiculos.sgav.RH.model.service;

import java.util.List;
import java.util.Optional;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Funcao;

public interface FuncaoService {
	
	Funcao salvar(Funcao funcao);
	Funcao atualizar(Funcao funcao);
	void deletar(Funcao funcao);
	void validar(Funcao funcao);
	Optional<Funcao> obterPorId(Long id);
	List<Funcao> buscar(Funcao funcao);
	void validarFuncao(String funcao);
	

}
