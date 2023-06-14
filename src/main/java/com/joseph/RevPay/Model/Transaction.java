package com.joseph.RevPay.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Transaction {
    @Id
    @GeneratedValue

    private Long id;
    private Long senderId;
    private Long receiverId;
    private double amount;

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public Long getSenderId(){
        return senderId;
    }
    public void setSenderId(Long senderId){
        this.senderId=senderId;
    }

    public Long getReceiverId(){
        return receiverId;
    }
    public void setReceiverId(Long receiverId){
        this.receiverId=receiverId;
    }
    public double getAmount(){
        return amount;
    }
    public void setAmount(double amount){
        this.amount=amount;
    }
}
