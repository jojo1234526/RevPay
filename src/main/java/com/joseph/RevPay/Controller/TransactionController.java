package com.joseph.RevPay.Controller;


import com.joseph.RevPay.Model.Transaction;
import com.joseph.RevPay.Model.User;
import com.joseph.RevPay.Service.TransactionService;
import com.joseph.RevPay.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws Exception {
        // You'll need to fetch sender and receiver users by their ids before creating a new transaction.
        User senderUser = userService.findUserById(transaction.getSender().getId());
        User receiverUser = userService.findUserById(transaction.getReceiver().getId());

        if (senderUser == null || receiverUser == null) {
            throw new Exception("Sender or Receiver not found");
        }

        Transaction newTransaction = transactionService.createTransaction(senderUser, receiverUser, transaction.getAmount());
        return ResponseEntity.ok(newTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/send")
    public ResponseEntity<Transaction> sendMoney(@RequestBody Transaction transaction) {
        try {
            Transaction newTransaction = transactionService.sendMoney(transaction.getSender().getId(), transaction.getReceiver().getId(), transaction.getAmount());
            return ResponseEntity.ok(newTransaction);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
