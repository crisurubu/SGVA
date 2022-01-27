package com.sistemaadaptacaoveiculos.sgav.RH.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaadaptacaoveiculos.sgav.RH.api.dto.DepartamentoDTO;
import com.sistemaadaptacaoveiculos.sgav.RH.model.entidades.Departamento;
import com.sistemaadaptacaoveiculos.sgav.RH.model.exception.RegraNegocioException;
import com.sistemaadaptacaoveiculos.sgav.RH.model.service.DepartamentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departamento")
public class DepartamentoResource {
	
	private final DepartamentoService service;
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody DepartamentoDTO dto){
		Departamento departamento = Departamento.builder().departamento(dto.getDepartamento()).build();
		try {
			Departamento departamentoSalvo = service.salvarDepartamento(departamento);
			return new ResponseEntity<>(departamentoSalvo, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	/*
	@GetMapping
	public ResponseEntity<?> buscar(@RequestParam(value = "departamento", required = false) String departamento) {
		Departamento departamentoFiltro = new Departamento();
		departamentoFiltro.setDepartamento(departamento);
		
		List<Departamento> departamentos = service.buscar(departamentoFiltro);
		return ResponseEntity.ok(departamentos);
		
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> obterDepartamento(@PathVariable("id") Long id) {
		return service.obterPorId(id).map( departamento -> new ResponseEntity<>(converter(departamento), HttpStatus.OK))
				 					 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody DepartamentoDTO dto){
							
		try
			{
				Departamento entidade = converter(dto);
				entidade = service.salvar(entidade);
				return new ResponseEntity<>(entidade, HttpStatus.CREATED);
				
			}
		catch (RegraNegocioException e) 
			{
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody DepartamentoDTO dto){
		return service.obterPorId(id).map(entity -> {
			try {
				Departamento departamento = converter(dto);
				departamento.setId(entity.getId());
				service.atualizar(departamento);
				return ResponseEntity.ok(departamento);
				
			}catch(RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>("Departamento não encontrado. ", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id){
		return service.obterPorId(id).map(entidade -> {
			service.deletar(entidade);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Departamento não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	
	private DepartamentoDTO converter(Departamento departamento) {
		return DepartamentoDTO.builder().id(departamento.getId()).departamento(departamento.getDepartamento()).build();
	}
	
	private Departamento converter(DepartamentoDTO dto) {
		Departamento departamento = new Departamento();
		departamento.setId(dto.getId());
		departamento.setDepartamento(dto.getDepartamento());
		return departamento;
	}
	*/

}
