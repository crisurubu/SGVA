package com.sistemaadaptacaoveiculos.sgav.RH.model.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.AdmissaoFuncionario;
import com.sistemaadaptacaoveiculos.sgav.RH.model.repository.AdmissaoFuncionarioRepository;
import com.sistemaadaptacaoveiculos.sgav.RH.model.service.AdmisssaoFuncionarioService;

@Service
public class AdmissaoFuncionarioServiceImpl implements AdmisssaoFuncionarioService {
	
	AdmissaoFuncionarioRepository repository;
	
	public AdmissaoFuncionarioServiceImpl(AdmissaoFuncionarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	@Transactional
	public AdmissaoFuncionario admitir(AdmissaoFuncionario admissao) {
		return repository.save(admissao);
	}

	

	@Override
	@Transactional
	public AdmissaoFuncionario demitir(AdmissaoFuncionario demissao) {
		Objects.requireNonNull(demissao.getId());
		return repository.save(demissao);
		
	}

	@Override
	public Optional<AdmissaoFuncionario> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<AdmissaoFuncionario> buscar(AdmissaoFuncionario funcionarioAdmitido) {
		Example<AdmissaoFuncionario> example = Example.of(funcionarioAdmitido, ExampleMatcher.matching()
				  .withIgnoreCase()
				  .withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public AdmissaoFuncionario obterPodIdFuncionario(Long id) {
		return null;
		
	}

	

	

	
}
