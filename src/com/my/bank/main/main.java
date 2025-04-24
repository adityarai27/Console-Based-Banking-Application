package com.my.bank.main;

import com.my.bank.entity.User;
import com.my.bank.service.UserService;

import java.util.Scanner;

public class main {

    private static Scanner sc = new Scanner(System.in);

    static main Main = new main();
    static UserService userService = new UserService();

    public static void main(String[] args) {


        while (true) {

            System.out.println("Enter Your Name: ");
            String username = sc.next();

            System.out.println("Enter Your Password: ");
            String password = sc.next();

            User user = userService.login(username, password);

            if (user != null && user.getRole().equals("admin")) {
                Main.initAdmin();
            } else if (user != null && user.getRole().equals("user")) {
                Main.initCustomer(user);
            } else {
                System.out.println("Login Failed.");
            }

        }
    }


    private void initAdmin() {

        boolean flag = true;

        while (flag) {

            System.out.println("1. Exit/Logout");
            System.out.println("2. Create a customer account.");

            int selectedOption = sc.nextInt();

            switch (selectedOption) {
                case 1:
                    flag = false;
                    System.out.println("You have successfully logged out...");
                    break;
                case 2:
                    Main.addNewCustomer();
                    break;
                default:
                    System.out.println("Wrong Choice");
            }
        }
    }

    private void addNewCustomer() {

        System.out.println("Enter username: ");
        String username = sc.next();

        System.out.println("Enter password: ");
        String password = sc.next();

        System.out.println("Enter contact number: ");
        String contact = sc.next();

        boolean result = userService.addNewCustomer(username, password, contact);

        if (result) {
            System.out.println("Customer account is created...");
        } else {
            System.out.println("Customer account creation is failed...");
        }

    }

    private void initCustomer(User user) {

        boolean flag = true;

        while (flag) {

            System.out.println("1. Exit/Logout");
            System.out.println("2. Check bank balance.");
            System.out.println("3. Transfer fund.");

            int selectedOption = sc.nextInt();

            switch (selectedOption) {
                case 1:
                    flag = false;
                    System.out.println("You have successfully logged out...");
                    break;
                case 2:
                   Double balance = Main.checkBankBalance(user.getUsername());
                   if (balance != null){
                       System.out.println("Your bank balance is "+balance);
                   }else {
                       System.out.println("Check your username.");
                   }
                   break;
                case 3:
                    Main.fundTransfer(user);
                    break;
                default:
                    System.out.println("Wrong Choice");
            }
        }
    }

    private void fundTransfer(User userDetails){
        System.out.println("Enter payee account user id: ");
        String payeeAccountId = sc.next();

        User user = getUser(payeeAccountId);

        if(user != null){
            System.out.println("Enter amount to transfer");
            Double amount = sc.nextDouble();

            Double userAccountBalance = checkBankBalance(userDetails.getUsername());

            if(userAccountBalance >= amount){
                boolean result = userService.transferAmount(userDetails.getUsername(), payeeAccountId, amount);

                if(result){
                    System.out.println("Amount transfer successfully...");
                }else {
                    System.out.println("Transfer failed...");
                }
            }else {
                System.out.println("You have insufficient: " +userAccountBalance);
            }


        }else {
            System.out.println("Please enter valid username...");
        }


    }

    private User getUser(String userId){
        return userService.getUser(userId);
    }

    private Double checkBankBalance(String userId){
        return userService.checkBankBalance(userId);
    }
}