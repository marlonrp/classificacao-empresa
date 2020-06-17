package backend.desafio.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Entity
@Table(name = "rate")
public class Rate {
	
	public Rate() {}

	@Tolerate
	public Rate(Integer month, Integer score, Company company) {
		this.month = month;
		this.score = score;
		this.company = company;
	}

	public interface RateUpdate {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	@NotNull(message = "Id is mandatory", groups = RateUpdate.class)
	private Integer id;

	@Column(name = "month")
	@Min(value = 1, message = "Month must be equal or greatter than 1(one)")
	@Max(value = 12, message = "Month must be equal or lower than 12(twelve)")
	@NotNull(message = "Month is mandatory", groups = RateUpdate.class)
	private Integer month;

	@Column(name = "score")
	@Min(value = 1, message = "Rate must be equal or greatter than 1(one)")
	@Max(value = 100, message = "Rate must be equal or lower than 100(hundred)")
	@NotNull(message = "Rate is mandatory")
	private Integer score;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id")
	private Company company;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
