package br.com.nettooe.rest.dto;

public class ClienteRequest {

	public String nome;

	public String email;

	public String dataNascimento;

	/**
	 * @param name
	 * @param cpf
	 * @param address
	 */
	public ClienteRequest(String nome, String email, String dataNascimento) {
		super();
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
	}

	public ClienteRequest() {
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

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
