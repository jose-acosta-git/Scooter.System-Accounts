package accounts.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User {

	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column
	private String phone;
	
	@Column
	private String role;
	
	@ManyToMany(mappedBy = "users")
	private Set<Account> accounts;

	public User(String name, String email, String phone, String role) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
		this.accounts = new HashSet<>();
	}
	
	public User() {}
	
	public int getId() {return id;}
	public String getName() {return name;}
	public String getEmail() {return email;}
	public String getPhone() {return phone;}
	public String getRole() {return role;}
	public Set<Account> getAccounts () {return accounts;}
}
