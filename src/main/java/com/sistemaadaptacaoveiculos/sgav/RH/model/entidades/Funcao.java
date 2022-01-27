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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "funcao" )
public class Funcao {
	
	@Id
	@Column(name = "id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "funcao")
	private String funcao;
	@Column(name = "salario")
	private Double salario;
	
	@ManyToOne
	@JoinColumn(name = "id_departamento")
	private Departamento departamento;
	
	

}
