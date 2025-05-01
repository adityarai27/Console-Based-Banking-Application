package com.my.bank.service;

import com.my.bank.entity.User;
import com.my.bank.repo.UserRepo;

import java.util.List;
import java.util.Map;

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

    public void printTransaction(String userId){
        userRepo.printTransaction(userId);
    }

    public void raiseChequeBookRequest(String userId){
        userRepo.raiseChequeBookRequest(userId);
    }

    public Map<String, Boolean> getAllChequeBookRequest(){
        return userRepo.getAllChequeBookRequest();

    }

    public List<String> getUserIdForCheckBookRequest(){
        return userRepo.getUserIdForCheckBookRequest();
    }

    public void approveChequeBookRequest(String userId){
        userRepo.approveChequeBookRequest(userId);
    }
}
