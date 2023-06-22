package com.joseph.RevPay.Service;

import com.joseph.RevPay.Model.User;
import com.joseph.RevPay.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(User user){
        user.setBalance(10000);
        return userRepository.save(user);
    }

    public User login(String username, String password){
        User user = userRepository.findByUsername(username);

        if(user != null && password.equals(user.getPassword())){
            return user;
        } else{
            return null;
        }
    }

//    public void sendMoney(String senderUsername, String receiverUsername, double amount) throws Exception {
//        User sender = userRepository.findByUsername(senderUsername);
//        User receiver = userRepository.findByUsername(receiverUsername);
//
//        if(sender == null || receiver == null) {
//            throw new Exception("One or more users not found.");
//        }
//
//        if(sender.getBalance() < amount) {
//            throw new Exception("Sender does not have enough balance.");
//        }
//
//        if(amount <= 0) {
//            throw new Exception("Invalid amount.");
//        }
//
//        sender.setBalance(sender.getBalance() - amount);
//        receiver.setBalance(receiver.getBalance() + amount);
//
//        userRepository.save(sender);
//        userRepository.save(receiver);
//    }

    public void sendMoney(Long senderId, Long receiverId, double amount) throws Exception {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new Exception("Sender not found."));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new Exception("Receiver not found."));

        if(sender.getBalance() < amount) {
            throw new Exception("Sender does not have enough balance.");
        }

        if(amount <= 0) {
            throw new Exception("Invalid amount.");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);
    }


    public void requestMoney(String requesterUsername, String giverUsername, double amount) throws Exception {
        User requester = userRepository.findByUsername(requesterUsername);
        User giver = userRepository.findByUsername(giverUsername);

        if(requester == null || giver == null) {
            throw new Exception("One or more users not found.");
        }

        if(giver.getBalance() < amount) {
            throw new Exception("The giver does not have enough balance.");
        }

        if(amount <= 0) {
            throw new Exception("Invalid amount.");
        }

        giver.setBalance(giver.getBalance() - amount);
        requester.setBalance(requester.getBalance() + amount);

        userRepository.save(requester);
        userRepository.save(giver);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findUserById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if(existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setPassword(user.getPassword());
            existingUser.setBalance(user.getBalance());
            userRepository.save(existingUser);
        }
        return existingUser;
    }
}



