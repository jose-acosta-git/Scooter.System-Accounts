package accounts.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User implements UserDetails{

	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@Column
	private String phone;

	@Column
	private String password;

	@Column
	Role role;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "users")
	private Set<Account> accounts;

	public User(String name, String email, String password, String phone, Role role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
		this.accounts = new HashSet<>();
	}
	
	public User() {}
	
	public int getId() {return id;}
	public String getName() {return name;}
	public String getEmail() {return email;}
	public String getPhone() {return phone;}
	public Role getRole() {return role;}
	public Set<Account> getAccounts () {return accounts;}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
