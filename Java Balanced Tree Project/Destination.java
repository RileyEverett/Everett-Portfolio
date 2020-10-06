/*
========================================================================================================================
Author: Riley Collins
Class: CS 202
Date: 03/09/2018
Description: This file contains the definitions and implementation of the Destination class and its methods.
this class is derived from the Airport class and contains a LLL of connections and the methods to with with that LLL.
========================================================================================================================
*/

package Airport.Software;

public class Destination extends Airport {
    protected LLL_Node connections;

    Destination() {
        connections = null;
    }

    public LLL_Node getConnections() {
        return connections;
    }

    public void setConnections(LLL_Node connections) {
        this.connections = connections;
    }

    public int compare(Destination to_compare) {
        return this.getName().compareTo(to_compare.getName()); //compares two strings and return the result
    }

    public void display() {
        //display the name of the airport
        System.out.print(name + " Connections: ");
        //display all connections coming into the airport
        connections.display();
        System.out.println();
    }

    public void insert(String to_add) {
        //add a new connection to the front of the LLL
        LLL_Node temp = connections;
        connections = new LLL_Node();
        connections.setData(to_add);
        connections.setNext(temp);
    }

}
