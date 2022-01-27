package com.sistemaadaptacaoveiculos.sgav.RH.model.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Departamento;
import com.sistemaadaptacaoveiculos.sgav.RH.model.exception.RegraNegocioException;
import com.sistemaadaptacaoveiculos.sgav.RH.model.repository.DepartamentoRepository;
import com.sistemaadaptacaoveiculos.sgav.RH.model.service.DepartamentoService;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {
	
	DepartamentoRepository repository;
	
	public DepartamentoServiceImpl(DepartamentoRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Departamento salvarDepartamento(Departamento departamento) {
		validarDepartamento(departamento.getDepartamento());
		return repository.save(departamento);
		
	}

	@Override
	public void validarDepartamento(String departamento) {
		boolean existe = repository.existsByDepartamento(departamento);
		if(existe) {
			throw new RegraNegocioException("Ja existe o departamento cadastrado...");
		}
		
	}

	@Override
	public Optional<Departamento> obterPorId(Long id) {
		return repository.findById(id);
	}
	
	
	
/*
	@Override
	@Transactional
	public Departamento salvar(Departamento departamento) {
		validar(departamento);
		return repository.save(departamento);
		
	}

	@Override
	public Optional<Departamento> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Departamento atualizar(Departamento departamento) {
		Objects.requireNonNull(departamento.getId());
		validar(departamento);
		return repository.save(departamento);
	}

	@Override
	@Transactional
	public void deletar(Departamento departamento) {
		Objects.requireNonNull(departamento.getId());
		repository.delete(departamento);
		
	}

	@Override
	public void validar(Departamento departamento) {
		if(departamento.getDepartamento() == null || departamento.getDepartamento().trim().equals("") ) {
			throw new RegraNegocioException("Informe uma Departamento válido");
		}
		
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Departamento> buscar(Departamento departamentoFiltro) {
		Example<Departamento> example = Example.of(departamentoFiltro, ExampleMatcher.matching()
																					 .withIgnoreCase()
																					 .withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}
/*/
	

}
