package br.com.nettooe.rest.cliente;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteRSTest {

	@Test
	@Order(1)
	public void test_POST() {
		RestAssured.given()
			.body("{\"email\": \"teste@test.com\", \"dataNascimento\": \"1987-01-20\", \"nome\": \"Leonardo DaVinci\"}")
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.header("accept", "*/*")
				.when()
					.post("/cliente")
				.then()
					.statusCode(201);
		
		RestAssured.given()
		.body("{\"email\": \"teste@gmail.com\", \"dataNascimento\": \"1981-02-10\", \"nome\": \"Albert Einstein\"}")
			.header("Content-Type", MediaType.APPLICATION_JSON)
			.header("accept", "*/*")
			.when()
				.post("/cliente")
			.then()
				.statusCode(201);
		
		RestAssured.given()
		.body("{\"email\": \"teste@test.com\", \"dataNascimento\": \"1987-01-20\", \"nome\": \"Frank Sinatra\"}")
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
	
}