package backend.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.desafio.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
//	@Modifying
//	@Query("UPDATE Company SET rate = ?2 WHERE id = ?1")
//	void updateCompanyRateById(int id, int rate);
}