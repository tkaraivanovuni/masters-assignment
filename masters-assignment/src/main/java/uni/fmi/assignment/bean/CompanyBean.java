package uni.fmi.assignment.bean;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "company")
@JsonIgnoreProperties({"transactions"})
public class CompanyBean{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "ticker", nullable = false, unique = true)
	private String ticker;
	
	@Column(name = "name", nullable = false)
	private String companyName;
	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	private List<TransactionBean> transactions;
	
	public CompanyBean() {
		
	}

	public CompanyBean(String ticker, String companyName) {
		this.ticker = ticker;
		this.companyName = companyName;
	}

	public List<TransactionBean> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionBean> transactions) {
		this.transactions = transactions;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
