package backend.desafio.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.desafio.models.Company;
import backend.desafio.models.Rate;
import backend.desafio.services.CompanyService;
import backend.desafio.services.RateService;

@RestController
@RequestMapping(value = "/rate", produces = MediaType.APPLICATION_JSON_VALUE)
public class RateController {

	@Autowired
	private RateService rateService;
	
	@Autowired
	private CompanyService companyService;

	@PostMapping(value = "/{month}")
	public ResponseEntity<Page<Rate>> getAllByMonth(@PathVariable Integer month,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		Page<Rate> content = rateService.getAllByMonth(month, page, size);
		return ResponseEntity.ok().body(content);
	}

	@PostMapping(value = "/computeFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Rate> computeFile(@RequestParam Integer companyId, @RequestParam() Integer month, @RequestParam() MultipartFile file) {
		Optional<Company> company = companyService.findById(companyId);
		Rate rate = rateService.computeFile(company.get(), month, file);
		return ResponseEntity.ok().body(rate);
	}
}
