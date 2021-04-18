package br.com.nettooe.repository;

import static br.com.nettooe.repository.ClienteValidator.isValidDataNascimento;
import static br.com.nettooe.repository.ClienteValidator.isValidEmail;
import static br.com.nettooe.repository.ClienteValidator.isValidNome;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.nettooe.entity.Cliente;
import br.com.nettooe.repository.ClienteValidator.ValidationResult;
import br.com.nettooe.rest.dto.ClienteRequest;
import br.com.nettooe.rest.dto.ClienteResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    @Transactional
    public Cliente persist(ClienteRequest req) {

	ClienteValidator.ValidationResult resultado = isValidEmail().and(isValidNome()).and(isValidDataNascimento())
		.apply(req);

	if (resultado != ValidationResult.SUCCESS) {
	    throw new BadRequestException(Response.status(Status.BAD_REQUEST).entity(resultado.toErroResult()).build());
	}

	Cliente cliente = new Cliente(req.getNome(), req.getEmail(), LocalDate.parse(req.getDataNascimento()));
	persist(cliente);
	return cliente;
    }

    @Transactional
    public Cliente partialUpdate(Long id, ClienteRequest req) {

	Optional<Cliente> optional = findByIdOptional(id, LockModeType.PESSIMISTIC_WRITE);

	if (!optional.isPresent()) {
	    return null;
	}

	Cliente cliente = optional.get();

	if (req.getNome() != null && !req.getNome().isEmpty()) {
	    cliente.setNome(req.getNome());
	}

	if (req.getEmail() != null && !req.getEmail().isEmpty()) {
	    cliente.setEmail(req.getEmail());
	}

	if (req.getDataNascimento() != null) {
	    cliente.setDataNascimento(LocalDate.parse(req.getDataNascimento()));
	}

	persist(cliente);

	return cliente;
    }

    @Transactional
    public boolean remove(Long id) {
	return deleteById(id);
    }

    public Optional<List<ClienteResponse>> pesquisar(ClienteRequest cliente, Page pagina) {

	PanacheQuery<Cliente> query = find(
		"from Cliente where (?1 is null or lower(nome) like lower(?2) ) and (?3 is null or lower(email) like lower(?4) )",
		cliente.nome, "%" + cliente.nome + "%", cliente.email, "%" + cliente.email + "%");
	query.page(pagina);

	final List<ClienteResponse> clientesResponse = query.list().stream()
		.map(c -> new ClienteResponse(c.id, c.nome, c.email, c.dataNascimento)).collect(Collectors.toList());

	return Optional.of(clientesResponse);
    }

}
