package backend.desafio.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import backend.desafio.models.Company;
import backend.desafio.services.CompanyService;
import backend.desafio.services.exceptions.ObjectNotFoundException;

@CrossOrigin("*")
@RestController
@Validated
@RequestMapping(value="/company")
public class CompanyResource {

	@Autowired
	private CompanyService companyService;
	
	@CrossOrigin("http://localhost:4200")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		List<Company> content = companyService.findAll();		
		return ResponseEntity.ok().body(content);
	}

	@CrossOrigin("http://localhost:4200")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> findOneById(@PathVariable Integer id) {
		Optional<Company> company = companyService.findById(id);
		
		if(!company.isPresent()) {
			throw new ObjectNotFoundException("Company with id " + id + " has not exist!");
		}
		return ResponseEntity.ok().body(company);
	}

	@CrossOrigin("http://localhost:4200")
	@RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Company save(@RequestBody @Valid Company company) {
        return companyService.save(company);
    }
	
	@CrossOrigin("http://localhost:4200")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteById(@PathVariable Integer id){
		companyService.delete(id);
		return ResponseEntity.ok().body("Company deleted!");
	}

	@CrossOrigin("http://localhost:4200")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	@Validated(Company.CompanyUpdate.class) 
	public @ResponseBody Company save(@PathVariable Integer id, @RequestBody @Valid Company company) {
		return companyService.save(company);
	}
}