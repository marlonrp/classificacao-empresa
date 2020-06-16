package backend.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.desafio.models.Company;
import backend.desafio.repositories.CompanyRepository;
import backend.desafio.services.exceptions.ObjectNotFoundException;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public List<Company> findAll() {
		return companyRepository.findAll();
	}

	public Optional<Company> findById(Integer id) {
		Optional<Company> company = companyRepository.findById(id);
		if (!company.isPresent()) {
			throw new ObjectNotFoundException("Company with id " + id + " has not exist!");
		}
		return company;
	}
}