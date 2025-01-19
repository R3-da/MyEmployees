package com.hahn.myemployees.services;

import com.hahn.myemployees.models.User;
import com.hahn.myemployees.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
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
