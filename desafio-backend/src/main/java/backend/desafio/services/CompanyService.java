package backend.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.desafio.models.Company;
import backend.desafio.repositories.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	public List<Company> findAll(){
		return companyRepository.findAll();
	}
	
	public Company save(Company company) {
		return companyRepository.save(company);
	}
	
	public void delete(int id) {
		companyRepository.deleteById(id);
	}
	
//	public void updateCompanyRateById(int id, int rate) {
//		companyRepository.updateCompanyRateById(id, rate);
//	}
	
	public Optional<Company> findById(Integer id) {
		return companyRepository.findById(id);
	}
}