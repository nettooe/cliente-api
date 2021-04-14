package br.com.nettooe.rest.dto;

import java.time.LocalDate;
import java.time.Period;

public class ClienteResponse {
	
	public Long id;

	public String nome;

	public String email;

	public LocalDate dataNascimento;

	public Integer idade;

	/**
	 * @param name
	 * @param cpf
	 * @param address
	 */
	public ClienteResponse(Long id, String nome, String email, LocalDate dataNascimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
	}

	public ClienteResponse() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdade() {
		Period p = Period.between(getDataNascimento(), LocalDate.now());
		return p.getYears();
	}

}
