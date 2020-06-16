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
@Table(name = "company")
public class Company {

	public interface CompanyUpdate {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	@NotNull(message = "Id is mandatory", groups = CompanyUpdate.class)
	private Integer id;

	@Column(name = "name")
	@NotBlank(message = "Name is mandatory")
	private String name;
}
