package backend.desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.desafio.models.Rate;
import backend.desafio.services.RateService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/rateByMonth", produces = MediaType.APPLICATION_JSON_VALUE)
public class RateController {

	@Autowired
	private RateService rateService;

	@CrossOrigin("http://localhost:4200")
	@PostMapping(value = "/{month}")
	public ResponseEntity<?> getAllByMonth(@PathVariable Integer month,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		Page<Rate> content = rateService.getAllByMonth(month, page, size);
		return ResponseEntity.ok().body(content);
	}
}
