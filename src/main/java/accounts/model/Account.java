package accounts.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Account {
	
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private LocalDate registrationDate;
	
	@Column
	private double balance;
	
	@Column
	private String mercadoPagoId;
	
	@Column
	private boolean isActive;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "accounts")
	private Set<User> users;
	
	public Account(LocalDate registrationDate, double balance, String mercadoPagoId) {
		this.registrationDate = LocalDate.now();
		this.balance = balance;
		this.mercadoPagoId = mercadoPagoId;
		this.isActive = true;
		this.users = new HashSet<>();
	}
	
	public Account() {}
	
	public int getId() {return id;}
	public LocalDate getRegistrationDate() {return registrationDate;}
	public double getBalance() {return balance;}
	public String getMercadoPagoId() {return mercadoPagoId;}
	public boolean isActive() {return isActive;}
	public Set<User> getUsers() {return users;}
	
	public void addMoney(double money) {
		this.balance += money;
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
    public void removeUser(User user) {
        users.remove(user);
    }

	public void deactivate() {
		isActive = false;
	}
	
	public void activate() {
		isActive = true;
	}

	public void payService(double price) {
		this.balance -= price;
	}
}