package accounts.dtos;


public class RegisterDto {
	private String name;
	private String email;
	private String phone;
	private String password;
    private String role;
    
    public RegisterDto(){}

    public RegisterDto(String name, String email, String phone, String password, String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    
}
