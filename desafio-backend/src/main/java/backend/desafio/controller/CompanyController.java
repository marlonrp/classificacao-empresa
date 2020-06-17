package backend.desafio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.desafio.models.Company;
import backend.desafio.services.CompanyService;
import backend.desafio.models.Rate;

@RestController
@Validated
@RequestMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@GetMapping()
	public ResponseEntity<List<Company>> findAll() {
		List<Company> content = companyService.findAll();
		return ResponseEntity.ok().body(content);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Company>> findOneById(@PathVariable Integer id) {
		Optional<Company> company = companyService.findById(id);
		return ResponseEntity.ok().body(company);
	}
}
