/*
========================================================================================================================
Author: Riley Collins
Class: CS 202
Date: 03/09/2018
Description: This file contains the definitions and implementation of the application class and its methods.
this class acts as a helpful user interface so the client-side users can work with the data structures and hierarchies.
========================================================================================================================
*/

package Airport.Software;

public class Application extends Util {
    Application() {


    }

    public boolean get_input() {
        int i = input.nextInt();
        return false;
    }

    public void greeting(Tree tree1) {
        int user_input;
        int user_continue = 0;
        do {
            System.out.println("\nPlease Select An Option Below");
            System.out.println("Option 1: Display All Airports And Connections");
            System.out.println("Option 2: Display A Specific Airport And Its Connections");
            System.out.println("Option 3: Remove All Airports And Connections");
            System.out.println("Option 4: Exit The Program");
            user_input = input.nextInt();

            switch (user_input) {
                case 1: tree1.display();
                    break;
                case 2:
                    System.out.println("Please Enter The Airports Three Letter Designation ( e.g. PDX )");
                    String match = input.next(); //get the user input of what destination they are looking for
                    Destination found = tree1.retrieve(match); //return that destination and store it in found
                    if(found != null)
                        found.display(); //display found
                    else
                        System.out.println("Error!");
                    break;
                case 3:
                    if (tree1.remove())
                        System.out.println("Success!");
                    else
                        System.out.println("Error!");
                    break;
                case 4: user_continue = 1; //set user_input to non zero to exit loop
                    break;
                default:
                    System.out.println("Error: Please Choose A Valid Option");
                    break;
            }
        } while (user_continue == 0);
    }
}
