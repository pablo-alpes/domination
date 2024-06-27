package service;

import java.util.Scanner;

public class UserRegistrationService {
    public static String getUserName() {
    Scanner scan = new Scanner(System.in);  // Create a Scanner object
    System.out.println("Enter your name for the game");

    String userName = scan.nextLine();  // Read user input
    System.out.println("You have chosen: " + userName);  // Output user input
         return userName;
    }
}

