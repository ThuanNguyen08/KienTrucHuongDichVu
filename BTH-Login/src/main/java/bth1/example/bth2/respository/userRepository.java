package bth1.example.bth2.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bth1.example.bth2.entities.User;

@Repository
public interface userRepository extends JpaRepository<User, Integer>{

	User findByUsernameAndPassword(String username, String password);
}
