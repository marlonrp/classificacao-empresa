package backend.desafio.api;

import java.io.File;

import org.hamcrest.Matchers;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import backend.desafio.services.RateService;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@TestMethodOrder(Alphanumeric.class)
public class RateApiTest {
	
	@Autowired
	private RateService rateService;

	private static final Integer FIRST_ID_COMPANY = 1;
	private static final Integer SECOND_ID_COMPANY = 2;
	private static final Integer MONTH_JANUARY = 1;
	private static final Integer FIRST_TIME_COMPUTED_SCORE = 51;
	private static final Integer SECOND_TIME_COMPUTED_SCORE = 52;
	private static final Integer TOTAL_RATES = 2;
	private static final Integer TOTAL_RATES_BY_PAGE = 1;
	private static final String ACTUAL_PAGE = "0";
	private static final String PAGE_SIZE = "1";

	private static File file;
	
	private static final String filePath = "src/test/resources/json/invoices-debits.json";

	@LocalServerPort
	private int port;

	@BeforeAll
	private void setUp() {
		RestAssured.basePath = "/rate";
		RestAssured.port = this.port;

		file = new File(filePath);
	}

	@Test
	@DisplayName("returningHttpStatusCode200WhenFirstRateComputeFileForFirstCompany")
	public void test01() {
		RestAssured.given()
			.header("Accept", "application/json")
	        .header("Content-type", "multipart/form-data")
	        .formParam("companyId", FIRST_ID_COMPANY)
	        .formParam("month", MONTH_JANUARY)
	        .multiPart("file", file)
		.when()
			.post("/computeFile")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("score", equalTo(FIRST_TIME_COMPUTED_SCORE));
	}
	
	@Test
	@DisplayName("returningHttpStatusCode200WhenSecondRateComputeFileForFirstCompany")
	public void test02() {
		RestAssured.given()
			.header("Accept", "application/json")
	        .header("Content-type", "multipart/form-data")
	        .formParam("companyId", FIRST_ID_COMPANY)
	        .formParam("month", MONTH_JANUARY)
	        .multiPart("file", file)
		.when()
			.post("/computeFile")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("score", equalTo(SECOND_TIME_COMPUTED_SCORE));
	}
	
	@Test
	@DisplayName("returningHttpStatusCode200WhenFirstRateComputeFileForSecondCompany")
	public void test03() {
		RestAssured.given()
	        .formParam("companyId", SECOND_ID_COMPANY)
	        .formParam("month", MONTH_JANUARY)
	        .multiPart("file", file)
		.when()
			.post("/computeFile")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("score", equalTo(FIRST_TIME_COMPUTED_SCORE));
	}
	
	@Test
	@DisplayName("returningHttpStatusCode200WhenFindAllRatesByMonth")
	public void test04() {
		RestAssured.given()
			.pathParam("month", MONTH_JANUARY)
			.accept(ContentType.JSON)
		.when()
			.post("/{month}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("content", Matchers.hasSize(TOTAL_RATES));
	}

	@Test
	@DisplayName("returningHttpStatusCode200WhenFindPageableRatesByMonth")
	public void test05() {
		RestAssured.given()
			.pathParam("month", MONTH_JANUARY)
			.formParam("page", ACTUAL_PAGE)
			.formParam("size", PAGE_SIZE)
			.accept(ContentType.JSON)
		.when()
			.post("/{month}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("content", Matchers.hasSize(TOTAL_RATES_BY_PAGE))
			.body("totalElements", equalTo(TOTAL_RATES));
	}
	
	@Test
	@DisplayName("returningHttpStatusCode405WhenRateComputeFileMissingParamMonth")
	public void test06() {
		RestAssured.given()
			.header("Accept", "application/json")
	        .header("Content-type", "multipart/form-data")
	        .formParam("companyId", FIRST_ID_COMPANY)
	        .multiPart("file", file)
			.accept(ContentType.JSON)
		.when()
			.post("/computeFile")
		.then()
			.statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
			.body("msg", equalTo("Required Integer parameter 'month' is not present"));
	}
	
	@Test
	@DisplayName("returningHttpStatusCode405WhenRateComputeFileMissingParamCompanyId")
	public void test07() {
		RestAssured.given()
			.header("Accept", "application/json")
	        .header("Content-type", "multipart/form-data")
	        .formParam("month", MONTH_JANUARY)
	        .multiPart("file", file)
			.accept(ContentType.JSON)
		.when()
			.post("/computeFile")
		.then()
			.statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
			.body("msg", equalTo("Required Integer parameter 'companyId' is not present"));
	}
	
	@AfterAll
	private void truncateRateTable() {
		rateService.truncateTable();
	}

}
