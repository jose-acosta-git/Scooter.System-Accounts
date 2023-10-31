package accounts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	public User(String name, String email, String phone, String role) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}
	
	public User() {}
	
	public int getId() {return id;}
	public String getName() {return name;}
	public String getEmail() {return email;}
	public String getPhone() {return phone;}
	public String getRole() {return role;}
}
