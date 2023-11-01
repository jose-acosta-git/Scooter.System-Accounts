package accounts.dtos;

import java.time.LocalDate;

public class AccountDto {

	private LocalDate registrationDate;
	private double balance;
	private String mercadoPagoId;
	
	public AccountDto(LocalDate registrationDate, double balance, String mercadoPagoId) {
		super();
		this.registrationDate = registrationDate;
		this.balance = balance;
		this.mercadoPagoId = mercadoPagoId;
	}

	public AccountDto() {}

	public LocalDate getRegistrationDate() {return registrationDate;}
	public double getBalance() {return balance;}
	public String getMercadoPagoId() {return mercadoPagoId;}
}
