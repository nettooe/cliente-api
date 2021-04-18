package br.com.nettooe.rest.cliente;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.MediaType;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import br.com.nettooe.rest.dto.ClienteRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteRSTest {

	@Test
	@Order(1)
	public void test_POST() {
		RestAssured.given()
			.body("{\"email\": \"teste1@test.com\", \"dataNascimento\": \"1987-01-20\", \"nome\": \"Leonardo DaVinci\"}")
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.header("accept", "*/*")
				.when()
					.post("/cliente")
				.then()
					.statusCode(201);
		
		RestAssured.given()
		.body("{\"email\": \"teste2@gmail.com\", \"dataNascimento\": \"1981-02-10\", \"nome\": \"Albert Einstein\"}")
			.header("Content-Type", MediaType.APPLICATION_JSON)
			.header("accept", "*/*")
			.when()
				.post("/cliente")
			.then()
				.statusCode(201);
		
		RestAssured.given()
		.body("{\"email\": \"teste3@test.com\", \"dataNascimento\": \"1987-01-20\", \"nome\": \"Frank Sinatra\"}")
			.header("Content-Type", MediaType.APPLICATION_JSON)
			.header("accept", "*/*")
			.when()
				.post("/cliente")
			.then()
				.statusCode(201);
	}

	@Test
	@Order(2)
	public void test_GET() {
		RestAssured.given()
			.when()
			.get("/cliente")
			.then()
				.statusCode(200);
	}
	
	@Test
	@Order(3)
	public void test_GET_id() {
		RestAssured.given()
			.when()
				.get("/cliente/{id}", 1)
			.then()
				.statusCode(200);
	}
	
	@Test
	@Order(4)
	public void test_PATCH() {
		RestAssured.given()
			.body("{\"email\": \"teste2@test2.com\"}")
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.header("accept", "*/*")
				.when()
					.patch("/cliente/{id}", 1)
				.then()
					.statusCode(200);
	}
	
	@Test
	@Order(5)
	public void test_PUT() {
		RestAssured.given()
			.body("{\"email\": \"testexxx@test.com\", \"dataNascimento\": \"1982-01-20\", \"nome\": \"Leonardo Da Vinci\"}")
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.header("accept", "*/*")
				.when()
					.put("/cliente/{id}", 1)
				.then()
					.statusCode(200);
	}
	
	@Test
	@Order(6)
	public void test_DELETE() {
		RestAssured.given()
			.header("accept", "*/*")
			.when()
				.delete("/cliente/{id}", 1)
				.then()
					.statusCode(204);
		
		RestAssured.given()
			.header("accept", "*/*")
			.when()
				.delete("/cliente/{id}", 2000)
				.then()
					.statusCode(404);
	}
	
	
	@ParameterizedTest
	@Order(7)
	@CsvFileSource(resources = "/parameters_POST_cliente.csv", delimiter = ';')
	public void test_POST_assured(String nome, String email, String dataNascimento, Integer httpStatus, String codigoErro) {
	     
	     final String json = JsonbBuilder.create().toJson(new ClienteRequest(nome, email, dataNascimento));
	     
//	     System.out.println(json);
	     
	     if(httpStatus == 201) {
		 RestAssured.given().body(json)
         	     	.header("Content-Type", MediaType.APPLICATION_JSON)
         		.header("accept", "*/*")
 			.when()
 				.post("/cliente")
 			.then()
 				.statusCode(httpStatus);
	     } else if(httpStatus == 400) {
		 RestAssured.given().body(json)
        	     	.header("Content-Type", MediaType.APPLICATION_JSON)
        		.header("accept", "*/*")
        			.when()
        				.post("/cliente")
        			.then()
        				.statusCode(httpStatus)
        				.and().body(CoreMatchers.containsString(codigoErro));
	     } else {
		 Assertions.fail();
	     }
	     			
	}
	
	
}