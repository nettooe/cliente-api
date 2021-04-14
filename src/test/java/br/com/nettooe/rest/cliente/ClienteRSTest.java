package br.com.nettooe.rest.cliente;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class ClienteRSTest {

	@Test
	@Order(1)
	public void test_save() {
		RestAssured.given()
			.body("{\"email\": \"teste@test.com\", \"dataNascimento\": \"1987-01-20\", \"nome\": \"Leonardo DaVinci\"}")
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.header("accept", "*/*")
				.when()
					.post("/cliente")
				.then()
					.statusCode(201);
	}

	@Test
	public void test_getClienteById() {
		RestAssured.given().when().get("/cliente/{id}", 1).then().statusCode(200);
	}
}