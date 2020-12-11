package uni.fmi.assignment.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "transaction")
public class TransactionBean implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buyer_id")
	private UserBean buyer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	private CompanyBean company;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@Column(name = "amount", nullable = false, precision=2)
	private double amount;

	public TransactionBean() {
	}

	public TransactionBean(int id, UserBean buyer, CompanyBean company, int quantity, double amount) {
		super();
		this.id = id;
		this.buyer = buyer;
		this.company = company;
		this.quantity = quantity;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserBean getBuyer() {
		return buyer;
	}

	public void setBuyer(UserBean buyer) {
		this.buyer = buyer;
	}

	public CompanyBean getCompany() {
		return company;
	}

	public void setCompany(CompanyBean company) {
		this.company = company;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
