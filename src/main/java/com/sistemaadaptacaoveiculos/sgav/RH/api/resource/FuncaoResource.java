package com.sistemaadaptacaoveiculos.sgav.RH.api.resource;

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

import com.sistemaadaptacaoveiculos.sgav.RH.api.dto.FuncaoDTO;
import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Departamento;
import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Funcao;
import com.sistemaadaptacaoveiculos.sgav.RH.model.exception.RegraNegocioException;
import com.sistemaadaptacaoveiculos.sgav.RH.model.service.DepartamentoService;
import com.sistemaadaptacaoveiculos.sgav.RH.model.service.FuncaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funcao")
public class FuncaoResource {
	
	private final FuncaoService service;
	private final DepartamentoService departamentoService;
	
	
	@GetMapping
	public ResponseEntity<?> busca( @RequestParam(value = "funcao", required = false) String funcao,
								    @RequestParam(value = "salario", required = false) Double salario,
								    @RequestParam("departamento") Long IdDepartamento){
		Funcao funcaoFiltro = new Funcao();
		funcaoFiltro.setFuncao(funcao);
		funcaoFiltro.setSalario(salario);
		
		Optional<Departamento> departamento = departamentoService.obterPorId(IdDepartamento);
		if(!departamento.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Departamento não encontrado.");
		}
		else {
			funcaoFiltro.setDepartamento(departamento.get());
		}
		List<Funcao> funcaoEnc = service.buscar(funcaoFiltro);
		return ResponseEntity.ok(funcaoEnc);
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> obterFuncao(@PathVariable("id")Long id) {
		return service.obterPorId(id).map(funcao -> new ResponseEntity<>(converter(funcao), HttpStatus.OK))
									 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody FuncaoDTO dto){
						
		try
			{
				Funcao entidade = converter(dto);
				service.validarFuncao(entidade.getFuncao());
				entidade = service.salvar(entidade);
				return new ResponseEntity<>(entidade, HttpStatus.CREATED);
				
			}
		catch (RegraNegocioException e) 
			{
				return ResponseEntity.badRequest().body(e.getMessage());
			}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> atualizar( @PathVariable("id") Long id, @RequestBody FuncaoDTO dto){
		return service.obterPorId(id).map(entity -> {
			try
			{
				Funcao funcao = converter(dto);
				funcao.setId(entity.getId());
				service.atualizar(funcao);
				return ResponseEntity.ok(funcao);
				
			}
			catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>("Função não encontrada.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar( @PathVariable("id") Long id){
		return service.obterPorId(id).map(entity -> {
		
			    service.deletar(entity);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		}).orElseGet(() -> new ResponseEntity<>("Função não encontrada.", HttpStatus.BAD_REQUEST));
	}
	
	
	
	
	private FuncaoDTO converter(Funcao funcao) {
		return FuncaoDTO.builder()
						.id(funcao.getId())
						.funcao(funcao.getFuncao())
						.salario(funcao.getSalario())
						.departamento(funcao.getDepartamento().getId())
						.build();
		
						
				
	}
	
	private Funcao converter(FuncaoDTO dto) {
		Funcao funcao = new Funcao();
		funcao.setId(dto.getId());
		funcao.setFuncao(dto.getFuncao());
		funcao.setSalario(dto.getSalario());
		
		Departamento departamento = departamentoService
				.obterPorId(dto.getDepartamento())
				.orElseThrow(() -> new RegraNegocioException("Departamento não encontrado para Id informado"));
		
		funcao.setDepartamento(departamento);
		
		return funcao;
	}

}
