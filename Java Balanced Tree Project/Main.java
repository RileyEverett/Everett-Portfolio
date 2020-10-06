/*
========================================================================================================================
Author: Riley Collins
Class: CS 202
Date: 03/09/2018
Description: This file contains the main class and function that crates and call the rest of the program.
========================================================================================================================
*/

package Airport.Software;

public class Main extends Util {

    public static void main(String[] args) {
        Application app1 = new Application(); //create a new application reference
        Tree tree1 = new Tree(); //create a new tree reference
        tree1.build(); //populate the tree from and external data file
        app1.greeting(tree1); //run client-side interface
    }
}
