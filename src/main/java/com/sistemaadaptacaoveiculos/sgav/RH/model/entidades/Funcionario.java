package com.sistemaadaptacaoveiculos.sgav.RH.model.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "funcionario")
public class Funcionario {
	
	@Id
	@Column(name = "id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cpf")
	private String cpf;
	@Column(name = "email")
	private String email;
	@Column(name = "celular")
	private String celular;
	
	@ManyToOne
	@JoinColumn(name = "id_departamento")
	private Departamento departamento;
	
	@ManyToOne
	@JoinColumn(name = "id_funcao")
	private Funcao funcao;
	
	
	
	
	

}
