package br.com.nettooe.repository;

import static br.com.nettooe.repository.ClienteValidator.ValidationResult.NOT_VALID_DATANASCIMENTO;
import static br.com.nettooe.repository.ClienteValidator.ValidationResult.NOT_VALID_DATANASCIMENTO_OBRIGATORIO;
import static br.com.nettooe.repository.ClienteValidator.ValidationResult.NOT_VALID_EMAIL;
import static br.com.nettooe.repository.ClienteValidator.ValidationResult.NOT_VALID_EMAIL_OBRIGATORIO;
import static br.com.nettooe.repository.ClienteValidator.ValidationResult.NOT_VALID_NOME;
import static br.com.nettooe.repository.ClienteValidator.ValidationResult.NOT_VALID_NOME_OBRIGATORIO;
import static br.com.nettooe.repository.ClienteValidator.ValidationResult.SUCCESS;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.regex.Pattern;

import br.com.nettooe.repository.ClienteValidator.ValidationResult;
import br.com.nettooe.rest.dto.ClienteRequest;

public interface ClienteValidator extends Function<ClienteRequest, ValidationResult> {
    
    public final static Pattern DATE_PATTERN = Pattern.compile(
	      "^\\d{4}-\\d{2}-\\d{2}$");

    static ClienteValidator isValidEmail() {
	return cliente -> {
	    return cliente.getEmail() == null || cliente.getEmail().trim().isEmpty() ? NOT_VALID_EMAIL_OBRIGATORIO :
		    !cliente.getEmail().contains("@") ? NOT_VALID_EMAIL : SUCCESS;
	};
    }

    static ClienteValidator isValidNome() {
	return cliente -> {
	    final String nome = cliente.getNome();
	    return nome == null || nome.trim().isEmpty() ? NOT_VALID_NOME_OBRIGATORIO : 
		nome.length() <= 3 ? NOT_VALID_NOME : SUCCESS;
	};
    }

    static ClienteValidator isValidDataNascimento() {
	return cliente -> {
	    
	    final String dataNascimento = cliente.getDataNascimento();
	    
	    if(dataNascimento == null) {
		return NOT_VALID_DATANASCIMENTO_OBRIGATORIO; 
	    }
	    
	    if(!DATE_PATTERN.matcher(dataNascimento).matches()) {
		return NOT_VALID_DATANASCIMENTO; 
	    }
	    
	    try {
		LocalDate.parse(dataNascimento);
	    } catch (DateTimeException e) {
		return NOT_VALID_DATANASCIMENTO;
	    }
	    
	    return SUCCESS;
	};
    }

    default ClienteValidator and(ClienteValidator other) {
	return emAvaliacao -> {
	    ValidationResult result = this.apply(emAvaliacao);
	    return result.equals(SUCCESS) ? other.apply(emAvaliacao) : result;
	};
    }

    public enum ValidationResult {
	SUCCESS(0, ""),
	
	NOT_VALID_EMAIL_OBRIGATORIO(400010, "Parâmetro 'email' é obrigatório."),
	NOT_VALID_EMAIL(400011, "Parâmetro 'email' não é um tipo de e-mail válido. Exemplo: meuemail@dominio.com"),
	
	NOT_VALID_NOME_OBRIGATORIO(400020, "Parâmetro 'nome' é obrigatório."),
	NOT_VALID_NOME(400021, "Parâmetro 'nome' não é válido. Ele deve contar mais de 3 caracteres Exemplo: Fulano de Tal"),
	
	NOT_VALID_DATANASCIMENTO_OBRIGATORIO(400030, "Parâmetro 'dataNascimento' é obrigatório."),
	NOT_VALID_DATANASCIMENTO(400031, "Parâmetro 'dataNascimento' não é um tipo de data válido esperado. Utilize o padão yyyy-MM-dd. Exemplo: 1980-01-20");

	public final Integer codigo;
	public final String mensagem;

	private ValidationResult(Integer codigo, String mensagem) {
	    this.codigo = codigo;
	    this.mensagem = mensagem;
	}

	public Integer getCodigo() {
	    return codigo;
	}
	public String getMensagem() {
	    return mensagem;
	}
	
	public ErroResult toErroResult() {
	    return new ErroResult(codigo, mensagem);
	}
    }
    
    public class ErroResult {
	public Integer codigo;
	public String mensagem;
	public ErroResult(Integer codigo, String mensagem) {
	    super();
	    this.codigo = codigo;
	    this.mensagem = mensagem;
	}
	
    }
    
}