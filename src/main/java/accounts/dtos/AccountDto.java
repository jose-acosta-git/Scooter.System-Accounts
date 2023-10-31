package accounts.dtos;

import java.time.LocalDate;

public class AccountDto {

	private LocalDate registrationDate;
	private double balance;
	private String mercadoPagoId;
	private boolean isActive;
	
	public AccountDto(LocalDate registrationDate, double balance, String mercadoPagoId, boolean isActive) {
		super();
		this.registrationDate = registrationDate;
		this.balance = balance;
		this.mercadoPagoId = mercadoPagoId;
		this.isActive = isActive;
	}

	public AccountDto() {}

	public LocalDate getRegistrationDate() {return registrationDate;}
	public double getBalance() {return balance;}
	public String getMercadoPagoId() {return mercadoPagoId;}
	public boolean isActive() {return isActive;}
}
