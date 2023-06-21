package com.joseph.RevPay.Service;

import com.joseph.RevPay.Model.Transaction;
import com.joseph.RevPay.Model.User;
import com.joseph.RevPay.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joseph.RevPay.Repository.UserRepository;



import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public Transaction createTransaction(User sender, User receiver, double amount) throws Exception{
        if(sender.getBalance() < amount){
            throw new Exception("Insufficient funds");
        }

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        //Update the sender and receiver user's account after the transaction
        userService.updateUser(sender);
        userService.updateUser(receiver);

        return transactionRepository.save(transaction);
    }


    public Transaction sendMoney(Long senderId, Long receiverId, double amount) throws Exception {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new Exception("Sender not found."));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new Exception("Receiver not found."));

        if(sender.getBalance() < amount) {
            throw new Exception("Sender does not have enough balance.");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);

        sender.getSentTransactions().add(transaction);
        receiver.getReceivedTransactions().add(transaction);

        userRepository.save(sender);
        userRepository.save(receiver);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }
}
