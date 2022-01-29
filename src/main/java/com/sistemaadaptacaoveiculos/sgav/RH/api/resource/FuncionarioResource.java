package com.sistemaadaptacaoveiculos.sgav.RH.api.resource;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaadaptacaoveiculos.sgav.RH.api.dto.AtualizaStatusDTO;
import com.sistemaadaptacaoveiculos.sgav.RH.api.dto.FuncionarioDTO;
import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.AdmissaoFuncionario;
import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Departamento;
import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Funcao;
import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Funcionario;
import com.sistemaadaptacaoveiculos.sgav.RH.model.enums.StatusFuncionario;
import com.sistemaadaptacaoveiculos.sgav.RH.model.exception.RegraNegocioException;

import com.sistemaadaptacaoveiculos.sgav.RH.model.service.AdmisssaoFuncionarioService;
import com.sistemaadaptacaoveiculos.sgav.RH.model.service.DepartamentoService;
import com.sistemaadaptacaoveiculos.sgav.RH.model.service.FuncaoService;
import com.sistemaadaptacaoveiculos.sgav.RH.model.service.FuncionarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funcionario")
public class FuncionarioResource {
	
	private final FuncionarioService service;
	private final DepartamentoService departamentoService;
	private final FuncaoService funcaoService;
	private final AdmisssaoFuncionarioService admissao;
	
	
	
	@GetMapping
	public ResponseEntity<?> buscar(@RequestParam (value = "nome"    , required = false) String nome,
									 @RequestParam(value = "email"   , required = false) String email,
									 @RequestParam(value = "cpf"     , required = false) String cpf,
									 @RequestParam(value = "celular" , required = false) String celular,
									 @RequestParam("departamento") Long idDepartamento,
									 @RequestParam("funcao") Long idFuncao
								 ){
		
		Funcionario funcionarioFiltro = new Funcionario();
		funcionarioFiltro.setNome(nome);
		funcionarioFiltro.setEmail(email);
		funcionarioFiltro.setCpf(cpf);
		funcionarioFiltro.setCelular(celular);
		
		Optional<Departamento> departamento = departamentoService.obterPorId(idDepartamento);
		if(!departamento.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta, departamento não encontrado.");
		}
		else 
		{
			funcionarioFiltro.setDepartamento(departamento.get());
		}
		
		Optional<Funcao> funcao = funcaoService.obterPorId(idFuncao);
		if(!funcao.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta, função não encontrado.");
		}
		else 
		{
			funcionarioFiltro.setFuncao(funcao.get());
		}
		List<Funcionario> funcionario = service.buscar(funcionarioFiltro);
		return ResponseEntity.ok(funcionario);
		
		
	}
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity<?> atualizaStatus(@PathVariable("id") Long id, AtualizaStatusDTO dto ){
		return service.obterPorId(id).map(entity -> {
			StatusFuncionario statusFuncionario = StatusFuncionario.valueOf(dto.getStatus());
			if(statusFuncionario == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar status do funcionario.");
			}
			try {
				
				entity.setStatus(statusFuncionario);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
				
			} catch(RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>("Funcionário não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> obterFuncionario(@PathVariable("id") Long id){
		return service.obterPorId(id).map(funcionario -> new ResponseEntity<>(converter(funcionario), HttpStatus.OK))
									 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody FuncionarioDTO dto){
		try
			{
				Funcionario entidade = converter(dto);
				service.validarCpf(entidade.getCpf());
				entidade = service.salvar(entidade);
				
				
				AdmissaoFuncionario addFuncionario = AdmissaoFuncionario.builder()
																		.dataAdmissao(new Date())
																		.dataDemissao(null)
																		.funcionario(entidade)
																		.funcao(entidade.getFuncao())
																		.build();
																																				
				admissao.admitir(addFuncionario);
				return new ResponseEntity<>(entidade, HttpStatus.CREATED);
				
			}
		catch (RegraNegocioException e) 
			{
				return ResponseEntity.badRequest().body(e.getMessage());
			}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody FuncionarioDTO dto){
		return service.obterPorId(id).map(entity ->{
			try 
				{
					Funcionario funcionario = converter(dto);
					funcionario.setId(entity.getId());
					service.atualizar(funcionario);
					return ResponseEntity.ok(funcionario);
					
					
				} 
			catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>("Funcionário não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delatar(@PathVariable("id") Long id){
		return service.obterPorId(id).map(entidade -> {
			service.deletar(entidade);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Funcionário não encontrado .", HttpStatus.BAD_REQUEST));
	}
	
	
	
	private FuncionarioDTO converter(Funcionario funcionario) {
		return FuncionarioDTO.builder()
							 .id(funcionario.getId())
							 .nome(funcionario.getNome())
							 .cpf(funcionario.getCpf())
							 .email(funcionario.getEmail())
							 .celular(funcionario.getCelular())
							 .departamento(funcionario.getDepartamento().getId())
							 .funcao(funcionario.getFuncao().getId())
							 .status(funcionario.getStatus().name())
							 .build();
							 
					
	}
	
	private Funcionario converter(FuncionarioDTO dto) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(dto.getNome());
		funcionario.setEmail(dto.getEmail());
		funcionario.setCpf(dto.getCpf());
		funcionario.setCelular(dto.getCelular());
		
		Departamento departamento = departamentoService
				.obterPorId(dto.getDepartamento())
				.orElseThrow(() -> new RegraNegocioException("Departamento não encontrado para Id informado"));
		
		funcionario.setDepartamento(departamento);
		
		Funcao funcao = funcaoService.obterPorId(dto.getFuncao())
									 .orElseThrow(() -> new RegraNegocioException("Função não encontrada para Id Informado"));
		
		funcionario.setFuncao(funcao);
		
				
		if(dto.getStatus() != null) {
			funcionario.setStatus(StatusFuncionario.valueOf(dto.getStatus()));
		}
		
		return funcionario;		
	}
	
	@PutMapping("{cpf}/demitir-funcionario")
	public ResponseEntity<?> demitirFuncionario(@PathVariable("cpf") String cpf){
		
		return service.obterPorCpf(cpf).map(entity -> {
			StatusFuncionario statusFuncionario = StatusFuncionario.INATIVO;
			if(statusFuncionario == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar status do funcionário..");
			}
			try {
				entity.setStatus(statusFuncionario);
				service.atualizar(entity);
				Optional<AdmissaoFuncionario> funcdmitido = admissao.obterPorId(entity.getId());
				AdmissaoFuncionario funcionarioDemitir = funcdmitido.get();
				funcionarioDemitir.setDataDemissao(new Date());
				admissao.demitir(funcionarioDemitir);
				return ResponseEntity.ok(entity);
				
				
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> 
					new ResponseEntity<>("Funcionário não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	

}
