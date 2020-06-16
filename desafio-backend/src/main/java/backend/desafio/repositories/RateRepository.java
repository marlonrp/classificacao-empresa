package backend.desafio.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.desafio.models.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {

	@Query("FROM #{#entityName} WHERE company_id = :companyId and month = :month")
	Optional<Rate> findByCompanyAndMonth(Integer companyId, Integer month);

	@Query(value = "SELECT * FROM #{#entityName} WHERE month = :month", nativeQuery=true)
	Page<Rate> getAllByMonth(Integer month, Pageable pageable);

}