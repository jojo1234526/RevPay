package com.joseph.RevPay.Controller;


import com.joseph.RevPay.Model.User;
import com.joseph.RevPay.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        return ResponseEntity.ok(userService.login(user.getUsername(), user.getPassword()));
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMoney(@RequestParam String senderUsername, @RequestParam String receiverUsername, @RequestParam double amount){
        try {
            userService.sendMoney(senderUsername, receiverUsername, amount);
            return ResponseEntity.ok("Money sent successfully");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


// In UserController

    @PostMapping("/request")
    public ResponseEntity<String> requestMoney(@RequestParam String requesterUsername, @RequestParam String giverUsername, @RequestParam double amount){
        try {
            userService.requestMoney(requesterUsername, giverUsername, amount);
            return ResponseEntity.ok("Money request processed successfully");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
