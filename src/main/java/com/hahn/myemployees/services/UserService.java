package com.hahn.myemployees.services;

import com.hahn.myemployees.models.User;
import com.hahn.myemployees.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {
    private static final String SECRET_KEY = "yourSecretKeyHereMakeItLongAndSecure123!@#";

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean hasPermission(User user, String action) {
        if (user == null) return false;

        switch (user.getRole()) {
            case ADMIN:
                return true;
            case HR:
                return !action.equals("DELETE_USER") && !action.equals("CREATE_USER");
            case MANAGER:
                return action.equals("VIEW_EMPLOYEE") || action.equals("EDIT_TEAM_MEMBER");
            default:
                return false;
        }
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
