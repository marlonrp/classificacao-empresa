package backend.desafio.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Company implements Serializable {

	public interface CompanyUpdate{}
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "Id is mandatory", groups = CompanyUpdate.class)
	private Integer id;

	@NotBlank(message = "Name is mandatory")
	private String name;

	@Min(value = 1, message = "Rate must be equal or greatter than 1(one)")
	@Max(value = 100, message = "Rate must be equal or lower than 100(hundred)")
	@NotNull(message = "Rate is mandatory")
	private Integer rate;
}
