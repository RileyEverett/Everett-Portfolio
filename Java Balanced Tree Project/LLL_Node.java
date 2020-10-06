/*
========================================================================================================================
Author: Riley Collins
Class: CS 202
Date: 03/09/2018
Description: This file contains the definitions and implementation of the LLL_Node class and its methods.
this class acts as the nodes that makes up the LLL of the Destination class.
========================================================================================================================
*/

package Airport.Software;

public class LLL_Node {
    protected String data; //to store the name of the connection
    protected LLL_Node next; //to reference the next node

    LLL_Node() {
        data = null;
        next = null;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LLL_Node getNext() {
        return next;
    }

    public void setNext(LLL_Node next) {
        this.next = next;
    }

    public void display() {
        //displays all nodes in the LLL
        System.out.print(data + ", ");
        if (getNext() != null) {
            getNext().display();
        }
    }
}
