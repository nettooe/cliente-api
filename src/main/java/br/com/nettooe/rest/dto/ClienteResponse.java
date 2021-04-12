package br.com.nettooe.rest.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;

public class ClienteResponse {

	public String nome;

//	@Email
	public String email;

	public LocalDate dataNascimento;

	/**
	 * @param name
	 * @param cpf
	 * @param address
	 */
	public ClienteResponse(String nome, String email, LocalDate dataNascimento) {
		super();
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

}
