package backend.desafio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import backend.desafio.models.Rate;
import backend.desafio.repositories.RateRepository;

@Service
public class RateService {

	@Autowired
	private RateRepository rateRepository;

	public Page<Rate> getAllByMonth(Integer month, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "score");
		return rateRepository.getAllByMonth(month, pageRequest);
	}

	public Rate save(Rate rate) {
		return rateRepository.save(rate);
	}

	public Optional<Rate> findByCompanyAndMonth(Integer companyId, Integer month) {
		return rateRepository.findByCompanyAndMonth(companyId, month);
	}
}
