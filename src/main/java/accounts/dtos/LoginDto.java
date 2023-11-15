package accounts.dtos;

public class LoginDto {
    private String email;
    private String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public LoginDto(){}

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
