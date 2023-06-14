package com.joseph.RevPay.Service;

import com.joseph.RevPay.Model.User;
import com.joseph.RevPay.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User register(User user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

    public User login(String username, String password){
        User user = userRepository.findByUsername(username);

        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            return user;
        } else{
            return null;
        }
    }
    public void sendMoney(User sender, User receiver, double amount){
        sender.setBalance(sender.getBalance() - amount);

        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);
    }

    public void requestMoney(User requester, User giver, double amount){
        giver.setBalance(giver.getBalance() - amount);

        requester.setBalance(requester.getBalance() + amount);

        userRepository.save(requester);
        userRepository.save(giver);
    }
}
