/*
========================================================================================================================
Author: Riley Collins
Class: CS 202
Date: 03/09/2018
Description: This file contains the definitions and implementation of the Airport class and its methods.
this class acts as the parent to the Destination class and contains a String name it inherits. this
class is an abstract base class with some abstract functions.
========================================================================================================================
*/

package Airport.Software;

public abstract class Airport {

    protected String name; //name of the airport

    Airport() {
        name = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void display(); //so that the display in the Destination class gets called


}
