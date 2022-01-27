package com.sistemaadaptacaoveiculos.sgav.RH.model.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Funcionario;
import com.sistemaadaptacaoveiculos.sgav.RH.model.exception.RegraNegocioException;
import com.sistemaadaptacaoveiculos.sgav.RH.model.repository.FuncionarioRepository;
import com.sistemaadaptacaoveiculos.sgav.RH.model.service.FuncionarioService;


@Service
public class FuncionarioServiceImpl implements FuncionarioService{
	
	FuncionarioRepository repository;
	
	
	public FuncionarioServiceImpl(FuncionarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	@Transactional
	public Funcionario salvar(Funcionario funcionario) {
		validar(funcionario);		
		return repository.save(funcionario);
	}

	@Override
	@Transactional
	public Funcionario atualizar(Funcionario funcionario) {
		Objects.requireNonNull(funcionario.getId());
		validar(funcionario);
		return repository.save(funcionario);
	}

	@Override
	@Transactional
	public void deletar(Funcionario funcionario) {
		Objects.requireNonNull(funcionario.getId());
		repository.delete(funcionario);
		
	}

	@Override
	public void validar(Funcionario funcionario) {
		if(funcionario.getNome() == null || funcionario.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe um nome válido");
		}
		if(funcionario.getEmail() == null || funcionario.getEmail().trim().equals("")) {
			throw new RegraNegocioException("Informe um email válido");
		}
		if(funcionario.getCelular() == null || funcionario.getCelular().trim().equals("")) {
			throw new RegraNegocioException("Informe um celular válido");
		}
		if(funcionario.getCpf() == null || funcionario.getCpf().trim().equals("")) {
			throw new RegraNegocioException("Informe um cpf válido");
		}
		if(funcionario.getDepartamento() == null || funcionario.getDepartamento().getId() == null) {
			throw new RegraNegocioException("Informe o departamento");
			
		}
		if(funcionario.getFuncao() == null || funcionario.getFuncao().getId() == null) {
			throw new RegraNegocioException("Informe a função");
		}
		
		
		
	}

	@Override
	public Optional<Funcionario> obterPorCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Funcionario> buscar(Funcionario funcionarioFiltro) {
		Example<Funcionario> example = Example.of(funcionarioFiltro, ExampleMatcher.matching()
																	  .withIgnoreCase()
																	  .withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public Optional<Funcionario> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	public void validarCpf(String cpf) {
		boolean existe = repository.existsByCpf(cpf);
		if(existe) {
			throw new RegraNegocioException("Já existe o cpf: "+cpf+" cadastrado no sistema..");
		}
		
	}

	

	
	
	
}
