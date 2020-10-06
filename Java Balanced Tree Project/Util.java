/*
========================================================================================================================
Author: Riley Collins
Class: CS 202
Date: 03/09/2018
Description: This file contains the definitions and implementation of the Util class and its methods.
this class acts as a helpful Scanner to allow for user input from the console and from external data files.
========================================================================================================================
*/

package Airport.Software;

import java.util.Scanner;


public class Util {

    protected static Scanner input; //for input from the console
    protected Scanner fileInput; //for input from external files

    Util() {
        input = new Scanner(System.in);
    }
}
