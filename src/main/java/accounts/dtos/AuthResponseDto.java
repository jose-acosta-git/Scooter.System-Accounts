package accounts.dtos;

public class AuthResponseDto {
    private String token;

    public AuthResponseDto(){}
    public AuthResponseDto(String token) {
        this.token = token;
    }
    
    public String getToken() {return token;}
}