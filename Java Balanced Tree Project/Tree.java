/*
========================================================================================================================
Author: Riley Collins
Class: CS 202
Date: 03/09/2018
Description: This file contains the definitions and implementation of the Tree class and its methods.
this class is a 2-3 Tree using a containing node class as its root. most of the methods of this class
are wrapper functions for Node class functions.
========================================================================================================================
*/

package Airport.Software;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tree extends Util {
    private Node root;
    private String delimiter;

    Tree() {
        this.root = null;
        //set up a scanner to input data from the airport_data file
        try {
            fileInput = new Scanner(new File("airport_data.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        delimiter = fileInput.next(); //get the delimiter from the file
    }

    public void build() {
        while (fileInput.hasNextLine()) {
            Destination current = new Destination(); //create a ne Destination
            current.name = fileInput.next(); //set the name of the destination
            String loader = fileInput.next(); //get first connections
            while (!loader.equals(delimiter)) { //check to see if all connection have been inserted
                LLL_Node temp = current.connections;
                current.connections = new LLL_Node(); //create a new LLL of connections
                current.connections.setData(loader); //input the read in data
                current.connections.setNext(temp);
                loader = fileInput.next(); //get the next line of data
            }
            this.insert(current); //insert the destination into the 2-3 tree
        }
    }

    private void insert(Destination to_add) {
        //if the tree is empty
        if (root == null) {
            root = new Node(); //allocate a new root
            root.setData1(to_add); //input the data into root
            return; //end of insert
        }
        //function in not a recursive call so flag is set to zero
        root.insert(to_add);
    }

    public boolean remove() {
        if(root != null) { //if root had data
            root = null; //set it no null
            return true; //return true
        }
        else
            return false; //error
    }

    public Destination retrieve(String match) {
        if (root != null)
           return root.retrieve(match); //search the tree for match and return that destination found
        else
            return null; //error no match found
    }

    public boolean display() {
        if (root != null) {
            root.display(); //display all data if root is not null
            return true;
        }
        else
            return false;
    }

}
