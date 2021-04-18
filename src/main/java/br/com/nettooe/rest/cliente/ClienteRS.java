package br.com.nettooe.rest.cliente;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.nettooe.entity.Cliente;
import br.com.nettooe.repository.ClienteRepository;
import br.com.nettooe.rest.dto.ClienteRequest;
import br.com.nettooe.rest.dto.ClienteResponse;
import io.quarkus.panache.common.Page;

@Path(ClienteRS.PATH_CLIENTE)
@Tag(name = "cliente", description = "Operações relacionadas a cliente.")
public class ClienteRS {

    static final String PATH_CLIENTE = "/cliente";

    @Inject
    ClienteRepository repository;

    @GET
    @Operation(summary = "Pesquisar clientes usando filtro e paginação")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pesquisar(@QueryParam("page.start") Integer pageStart, @QueryParam("page.limit") Integer pageLimit,
	    @QueryParam("cliente.nome") String nome, @QueryParam("cliente.email") String email) {

	if (pageStart == null) {
	    pageStart = 0;
	}
	if (pageLimit == null) {
	    pageLimit = 5;
	}

	ClienteRequest cliente = new ClienteRequest();
	cliente.nome = nome;
	cliente.email = email;

	Page pagina = Page.of(pageStart, pageLimit);

	Optional<List<ClienteResponse>> optional = repository.pesquisar(cliente, pagina);

	if (!optional.isPresent()) {
	    return Response.status(Status.NOT_FOUND).build();
	}

	return Response.ok(optional.get()).build();
    }

    @GET
    @Operation(summary = "Obter cliente por ID.")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClienteById(@PathParam("id") Long id) {
	final Optional<Cliente> optional = repository.findByIdOptional(id);

	if (!optional.isPresent()) {
	    return Response.status(Status.NOT_FOUND).build();
	}

	final Cliente entity = optional.get();
	final ClienteResponse clienteResponse = new ClienteResponse(entity.id, entity.nome, entity.email,
		entity.dataNascimento);

	return Response.ok(clienteResponse).build();
    }

    @POST
    @Operation(summary = "Cadastrar um novo cliente.")
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(
	    @RequestBody(required = true, name = "cliente", description = "Dados do cliente para gravar.") ClienteRequest cliente) {
	try {
	    Cliente saved = repository.persist(cliente);
	    return Response.created(URI.create(PATH_CLIENTE + "/" + saved.getId())).build();
	} catch (BadRequestException e) {
	    return e.getResponse();
	}
    }

    @PUT
    @Operation(summary = "Atualização dos dados de um cliente.")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@PathParam("id") Long id,
	    @RequestBody(required = true, name = "cliente", description = "Dados para atualizar um cliente.") ClienteRequest cliente) {
	Cliente saved = repository.partialUpdate(id, cliente);
	if (saved != null) {
	    return Response.ok().build();
	} else {
	    return Response.status(Status.NOT_FOUND).build();
	}
    }

    @PATCH
    @Operation(summary = "Atualização parcial dos dados de um cliente.")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarParcial(@PathParam("id") Long id,
	    @RequestBody(required = true, name = "cliente", description = "Dados a serem atualizados de um cliente.") ClienteRequest cliente) {
	Cliente saved = repository.partialUpdate(id, cliente);
	if (saved != null) {
	    return Response.ok().build();
	} else {
	    return Response.status(Status.NOT_FOUND).build();
	}
    }

    @DELETE
    @Operation(summary = "Exclusão física de um cliente.")
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") Long id) {
	if (repository.remove(id)) {
	    return Response.noContent().build();
	} else {
	    return Response.status(Status.NOT_FOUND).build();
	}
    }
}