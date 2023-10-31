package accounts.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
	
	public Account(LocalDate registrationDate, double balance, String mercadoPagoId) {
		this.registrationDate = LocalDate.now();
		this.balance = balance;
		this.mercadoPagoId = mercadoPagoId;
		this.isActive = true;
	}
	
	public Account() {}
	
	public int getId() {return id;}
	public LocalDate getRegistrationDate() {return registrationDate;}
	public double getBalance() {return balance;}
	public String getMercadoPagoId() {return mercadoPagoId;}
	public boolean isActive() {return isActive;}
}
