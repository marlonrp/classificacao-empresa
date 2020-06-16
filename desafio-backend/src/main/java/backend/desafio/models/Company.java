package backend.desafio.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "COMPANY")
public class Company {

	public interface CompanyUpdate {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, nullable = false)
	@NotNull(message = "Id is mandatory", groups = CompanyUpdate.class)
	private Integer id;

	@Column(name = "NAME")
	@NotBlank(message = "Name is mandatory")
	private String name;
	
//	@OneToOne(mappedBy = "company")
//	private Rate rate;
}
