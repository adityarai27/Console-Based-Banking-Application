package com.my.bank.service;

import com.my.bank.entity.User;
import com.my.bank.repo.UserRepo;

public class UserService {

    private UserRepo userRepo = new UserRepo();

    public void printUsers(){
        userRepo.printUsers();
    }

    public User login(String username, String password){
        return userRepo.login(username, password);
    }

    public  boolean  addNewCustomer(String username, String password, String contact){
        return userRepo.addNewCustomer(username, password, contact);
    }

    public Double checkBankBalance(String userId){

        return userRepo.checkBankBalance(userId);
    }

    public User getUser(String userId){
       return userRepo.getUser(userId);
    }

    public boolean transferAmount(String userId, String payeeUserId, Double amount){
        return userRepo.transferAmount(userId, payeeUserId, amount);
    }
}
