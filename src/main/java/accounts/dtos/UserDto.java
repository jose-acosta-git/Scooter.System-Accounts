package accounts.dtos;

public class UserDto {
	
	private String name;
	private String email;
	private String phone;
	private String role;
	
	public UserDto(String name, String email, String phone, String role) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	public UserDto() {}

	public String getName() {return name;}
	public String getEmail() {return email;}
	public String getPhone() {return phone;}
	public String getRole() {return role;}
}
