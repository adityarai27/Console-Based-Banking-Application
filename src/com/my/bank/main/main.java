package com.my.bank.main;

import com.my.bank.entity.User;
import com.my.bank.service.UserService;

import java.util.List;
import java.util.Map;
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
        String userId = "";

        while (flag) {

            System.out.println("1. Exit/Logout");
            System.out.println("2. Create a customer account.");
            System.out.println("3. See all transactions.");
            System.out.println("4. Check user account Balance.");
            System.out.println("5. Approve cheque book request. ");

            int selectedOption = sc.nextInt();

            switch (selectedOption) {
                case 1:
                    flag = false;
                    System.out.println("You have successfully logged out...");
                    break;
                case 2:
                    Main.addNewCustomer();
                    break;
                case 3:
                    System.out.println("Enter userId: ");
                    userId = sc.next();
                    Main.printTransaction(userId);
                    break;
                case 4:
                    System.out.println("Enter userId: ");
                    userId = sc.next();
                    Double accountBalance = Main.checkBankBalance(userId);
                    System.out.println(userId+ " account balance is "+accountBalance+"Rs");
                    break;
                case  5:
                    List<String> userIds = getUserIdForCheckBookRequest();
                    System.out.println("Please select userId from below...");
                    System.out.println(userIds);

                     userId = sc.next();

                     approveChequeBookRequest(userId);
                    System.out.println("Chequebook request is approved...");
                    break;
                default:
                    System.out.println("Wrong Choice");
            }
        }
    }



    private void approveChequeBookRequest(String userId){
        userService.approveChequeBookRequest(userId);
    }
    private List<String> getUserIdForCheckBookRequest(){
        return userService.getUserIdForCheckBookRequest();
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
            System.out.println("3. Transfer Fund.");
            System.out.println("4. See all transaction.");
            System.out.println("5. Raise chequebook request.");

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
                case 4:
                    Main.printTransaction(user.getUsername());
                    break;
                case 5:
                    String userId = user.getUsername();
                    Map<String, Boolean> map = getAllChequeBookRequest();

                    if (map.containsKey(userId) && map.get(userId)){
                        System.out.println("You hava already raised a request and it is already approved.");
                    } else if (map.containsKey(userId) && !map.get(userId)) {
                        System.out.println("You have already raised a request and it is pending for approval.");
                    }else {
                        raiseChequeBookRequest(userId);
                        System.out.println("Request raise successfully...");
                    }
                    break;
                default:
                    System.out.println("Wrong Choice");
            }
        }
    }


    private Map<String, Boolean> getAllChequeBookRequest(){
        return userService.getAllChequeBookRequest();
    }
    private void raiseChequeBookRequest(String userId){
            userService.raiseChequeBookRequest(userId);
    }


    private void printTransaction(String userId){
        userService.printTransaction(userId);
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