package backend.desafio.services;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import backend.desafio.models.Company;
import backend.desafio.models.ComputeFile;
import backend.desafio.models.Rate;
import backend.desafio.repositories.RateRepository;
import backend.desafio.services.exceptions.MissingParameterException;

@Service
public class RateService {

	@Autowired
	private RateRepository rateRepository;

	public Page<Rate> getAllByMonth(Integer month, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "score");
		return rateRepository.getAllByMonth(month, pageRequest);
	}
	
	@Transactional
	public void truncateTable() {
		rateRepository.truncateTable();
	}

	public Rate computeFile(Company company, Integer month, MultipartFile file) {
		if (month < 1 || month > 12) {
			throw new MissingParameterException("Month must be between 1(one) and 12(twelve)");
		}

		Optional<Rate> optionalRate = rateRepository.findByCompanyAndMonth(company.getId(), month);
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

		rate.setScore(calculeNewScore((double) rate.getScore(), computeFile));
		Rate savedRate = rateRepository.save(rate);
		return savedRate;
	}

	private Integer calculeNewScore(Double score, ComputeFile computeFile) {
		Double newScore = score;

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
		return newScore.intValue();
	}
}
