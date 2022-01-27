package com.sistemaadaptacaoveiculos.sgav.RH.model.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Funcao;
import com.sistemaadaptacaoveiculos.sgav.RH.model.exception.RegraNegocioException;
import com.sistemaadaptacaoveiculos.sgav.RH.model.repository.FuncaoRepository;
import com.sistemaadaptacaoveiculos.sgav.RH.model.service.FuncaoService;

@Service
public class FuncaoServiceImpl implements FuncaoService{
	
	FuncaoRepository repository;
	
	public FuncaoServiceImpl(FuncaoRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	@Transactional
	public Funcao salvar(Funcao funcao) {
		validar(funcao);
		return repository.save(funcao);
	}

	@Override
	@Transactional
	public Funcao atualizar(Funcao funcao) {
		Objects.requireNonNull(funcao.getId());
		validar(funcao);
		return repository.save(funcao);
	}

	@Override
	@Transactional
	public void deletar(Funcao funcao) {
		Objects.requireNonNull(funcao.getId());
		repository.delete(funcao);
		
	}

	@Override
	public void validar(Funcao funcao) {
		if(funcao.getFuncao() == null || funcao.getFuncao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma Função válida");
		}
		if(funcao.getSalario() == null || funcao.getSalario() < 0) {
			throw new RegraNegocioException("Informe uma Salário válido");
		}
		if(funcao.getDepartamento() == null || funcao.getDepartamento().getId() == null) {
			throw new RegraNegocioException("Informe um departamento");
		}
		
	}

	@Override
	public Optional<Funcao> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Funcao> buscar(Funcao funcaoFiltro) {
		
			Example<Funcao> example = Example.of(funcaoFiltro, ExampleMatcher.matching()
																		  .withIgnoreCase()
																		  .withStringMatcher(StringMatcher.CONTAINING));
			return repository.findAll(example);
	}

	@Override
	public void validarFuncao(String funcao) {
		boolean existe = repository.existsByfuncao(funcao);
		if(existe) {
			throw new RegraNegocioException("Já Existe a função cadastrado na base de dados...");
		}
		
	}

	
		
	

	
	
}
