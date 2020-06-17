package backend.desafio.api;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.Matchers;

import backend.desafio.models.Company;
import backend.desafio.services.CompanyService;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CompanyApiTest {
	
	private static final Integer TOTAL_COMPANIES = 10;
	private static final Integer FIRST_ID_COMPANY = 1;
	private static final Integer NOT_EXISTS_ID_COMPANY = 999;
	private static final String COMPANY_ID_999_NOT_EXIST = "Company with id " + NOT_EXISTS_ID_COMPANY + " has not exist!";

	private static Optional<Company> optionalFirstComapany;
	private static Company firstComapany;
	
	@LocalServerPort
	private int port;

	@Autowired
	private CompanyService companyService;
	
	@BeforeAll
	private void setUp() {
		RestAssured.basePath = "/company";
		RestAssured.port = this.port;
		
		prepareData();
	}
	
	@Test
	@DisplayName("returningHttpStatusCode200WhenFindCompanies")
	public void test01() {				
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("", Matchers.hasSize(TOTAL_COMPANIES));
	}
	
	@Test
	@DisplayName("returningHttpStatusCode200WhenFindCompanyByID")
	public void test02() {				
		RestAssured.given()
			.pathParam("id", FIRST_ID_COMPANY)
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo(firstComapany.getName()));
	}
	
	@Test
	@DisplayName("returningHttpStatusCode404WhenFindCompanyByID")
	public void test03() {				
		RestAssured.given()
			.pathParam("id", NOT_EXISTS_ID_COMPANY)
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.body("status", equalTo(404))
			.body("msg", equalTo(COMPANY_ID_999_NOT_EXIST));
	}
	
	private void prepareData() {
		optionalFirstComapany = companyService.findById(FIRST_ID_COMPANY);
		firstComapany = optionalFirstComapany.get();
	}
}
