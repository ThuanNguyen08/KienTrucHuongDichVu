package bth1.example.bth2.entities;

import jakarta.persistence.*;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUser;

	@Column(columnDefinition = "varchar(255)")
	private String username;

	@Column(columnDefinition = "varchar(255)")
	private String password;

	@Column(nullable = true, columnDefinition = "varchar(255)")
	private String token;

	@Column(columnDefinition = "varchar(255)")
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
