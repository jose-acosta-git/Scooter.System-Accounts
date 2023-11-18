package accounts.dtos;

import java.util.Set;

import accounts.model.Account;

public class UserResponseDto {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;
    private Set<Account> accounts;

    public UserResponseDto(int id, String name, String email, String phone, String password, String role,
            Set<Account> accounts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.accounts = accounts;
    }

    public UserResponseDto() {}

    public int getId() {
        return id;
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

    public Set<Account> getAccounts() {
        return accounts;
    }
    
    
}
