package backend.desafio.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import backend.desafio.models.Company;
import backend.desafio.services.CompanyService;
import backend.desafio.services.RateService;
import backend.desafio.services.exceptions.MissingParameterException;
import backend.desafio.models.ComputeFile;
import backend.desafio.models.Rate;

@RestController
@Validated
@RequestMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private RateService rateService;

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

	@PostMapping(value = "/{id}/computeFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Rate> computeFile(@PathVariable Integer id,
			@RequestParam() Integer month,
			@RequestParam() MultipartFile file) {

		if (month < 1 || month > 12) {
			throw new MissingParameterException("Month must be between 1(one) and 12(twelve)");
		}

		Company company = companyService.findById(id).get();
		Optional<Rate> optionalRate = rateService.findByCompanyAndMonth(id, month);
		Rate rate = null;

		if (optionalRate.isPresent()) {
			rate = optionalRate.get();
		} else {
			rate = new Rate(month, 50, company);
		}
		Map<String, Integer> map = null;

		try {
			String content = new String(file.getBytes(), StandardCharsets.UTF_8);
			map = new ObjectMapper().readValue(content, Map.class);
		} catch (Exception e) {
			throw new MissingParameterException(
					"The file does not containt all the mandatory informations, check the documentation and try again.");
		}

		ComputeFile computeFile = new ComputeFile(map.get("invoices"), map.get("debits"));
		Double newScore = (double) rate.getScore();

		for (int i = 1; i <= computeFile.getInvoices(); i++) {
			newScore = newScore + Math.round(((newScore * 0.02) * 100) / 100);
			;
			if (newScore >= 100) {
				newScore = 100.0;
				break;
			}
		}

		for (int i = 1; i <= computeFile.getDebits(); i++) {
			newScore = newScore - Math.round(((newScore * 0.04) * 100) / 100);
			if (newScore <= 1) {
				newScore = 1.0;
				break;
			}
		}

		rate.setScore(newScore.intValue());
		rate = rateService.save(rate);
		return ResponseEntity.ok().body(rate);
	}
}
