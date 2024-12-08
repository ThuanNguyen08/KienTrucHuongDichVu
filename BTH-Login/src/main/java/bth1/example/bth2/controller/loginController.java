package bth1.example.bth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bth1.example.bth2.service.userService;

@RestController
public class loginController {

	private userService userService;

	@Autowired
	private loginController(userService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/")
	public String helloWorld1() {
		return "hello";
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam("userName") String userName , @RequestParam("password") String password ) {
		if (userService.login(userName, password) != null) {
			String token = userService.generationToken(userName, password);
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}

	@GetMapping("/auth")
    public String helloWorld() {
        return "Hello World!";
    }
}
