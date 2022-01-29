package com.sistemaadaptacaoveiculos.sgav.RH.model.service;

import java.util.List;
import java.util.Optional;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.AdmissaoFuncionario;

public interface AdmisssaoFuncionarioService {
	
	AdmissaoFuncionario admitir (AdmissaoFuncionario admissao);
	AdmissaoFuncionario demitir(AdmissaoFuncionario demissao);
	Optional<AdmissaoFuncionario> obterPorId(Long id);
	List<AdmissaoFuncionario> buscar(AdmissaoFuncionario funcionarioAdmitido);
	AdmissaoFuncionario obterPodIdFuncionario(Long id);
	
	
}
