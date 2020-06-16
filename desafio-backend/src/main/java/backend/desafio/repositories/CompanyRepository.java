package backend.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.desafio.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
}