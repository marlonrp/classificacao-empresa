package backend.desafio.models;

public class ComputeFile {

	public ComputeFile(Integer invoices, Integer debits) {
		this.invoices = invoices;
		this.debits = debits;
	}

	private Integer invoices;

	private Integer debits;

	public Integer getInvoices() {
		return invoices;
	}

	public Integer getDebits() {
		return debits;
	}
}
