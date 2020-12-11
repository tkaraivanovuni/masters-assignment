package uni.fmi.assignment.bean;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"transactions"})
public class UserBean {
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="username", nullable = false, unique = true, length = 40)
	private String username;
	
	@Column(name="password", nullable = false, length = 32)
	private String password;
	
	@Column(name="email", nullable = false, unique = true, length = 256)
	private String email;
	
	@OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER)
	private List<TransactionBean> transactions;
	
	@ManyToMany
	@JoinTable(name = "user_role",
	joinColumns=@JoinColumn(name="user_id"),
	inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<RoleBean> roles;

	public UserBean() {}
	
	public UserBean(String username, String email) {
		this.username = username;
		this.email = email;
	}
	
	public UserBean(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;	
		}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<RoleBean> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleBean> roles) {
		this.roles = roles;
	}

	public List<TransactionBean> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionBean> transactions) {
		this.transactions = transactions;
	}

}
