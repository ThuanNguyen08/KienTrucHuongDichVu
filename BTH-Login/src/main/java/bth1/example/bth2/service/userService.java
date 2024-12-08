package bth1.example.bth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bth1.example.bth2.entities.User;
import bth1.example.bth2.respository.userRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class userService {

	// Lấy SECRET_KEY từ application.properties
	@Value("${jwt.secret.key}")
	private String secretKey;

	@Autowired
	private userRepository repo;

	public User login(String username, String password) {
		return repo.findByUsernameAndPassword(username, password);
	}

	public String getRoleByUserNameAndPassword(String username, String password) {
		User u = repo.findByUsernameAndPassword(username, password);
		if (u != null) {
			return u.getRole();
		}

		return null;
	}

	public String generationToken(String username, String password) {
		
		String role = getRoleByUserNameAndPassword(username, password);
		
		return Jwts.builder()
				.setSubject(username) // Thông tin user (có thể là username)
				.claim("role", role)
				.signWith(SignatureAlgorithm.HS256, secretKey) // Dùng thuật toán HS256 với secret key
				.compact();
	}

	public Claims validateToken(String token) throws Exception {
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7); // Lấy phần token sau Bearer
			try {
				// Giải mã token và trả về các claim (thông tin người dùng)
				return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
			} catch (Exception e) {
				throw new Exception("Invalid or expired token", e);
			}
		}
		throw new Exception("Token is missing or malformed");
	}
}
