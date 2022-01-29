package com.sistemaadaptacaoveiculos.sgav.RH.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
	
	private Long id;
	private String nome;
	private String email;
	private String cpf;
	private String celular;
	private Long departamento;
	private Long funcao;
	private String status;
	private Long admissao;

}
