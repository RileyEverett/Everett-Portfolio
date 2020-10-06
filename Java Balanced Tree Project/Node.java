/*
========================================================================================================================
Author: Riley Collins
Class: CS 202
Date: 03/09/2018
Description: This file contains the definitions and implementation of the Node class and its methods.
this class acts as the node that makes up the 2-3 tree of the Tree class. this class has the functions
to full manipulate the 2-3 tree referenced by the Tree class.
========================================================================================================================
*/

package Airport.Software;

public class Node {
    private Destination data1;
    private Destination data2;
    private Node left;
    private Node center;
    private Node right;
    private Node parent;

    Node() {
        data1 = null;
        data2 = null;
        left = null;
        center = null;
        right = null;
        parent = null;
    }

    public Node(Destination to_add, Node prev) {
        data1 = to_add;
        /*data2 = null;
        left = null;
        center = null;
        right = null;*/
        parent = prev;
    }

    //returns a reference to data1
    public Destination getData1() {
        return data1;
    }

    //sets the value of data1 then returns true;
    public void setData1(Destination to_add) {
        data1 = to_add;
    }

    //returns a reference to data2
    public Destination getData2() {
        return data2;
    }

    public void setData2(Destination to_add) {
        data2 = to_add;
    }

    //returns a reference to left
    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    //returns a reference to center
    public Node getCenter() {
        return center;
    }

    public void setCenter(Node center) {
        this.center = center;
    }

    //returns a reference to right
    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    //returns a reference to parent
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    //returns true is the Node is a leaf and false if otherwise
    private boolean isLeaf() {
        if (this.left == null && this.center == null && this.right == null)
            return true;
        else
            return false;
    }


    private void splitNode(Destination to_add) {
        if (getData1().compare(to_add) > 0) { //is the new data the smallest?
            setLeft(new Node(to_add, this)); //set the new left to smallest data
            setRight(new Node(getData2(), this)); //set new right as largest data
            setData2(null); //remove data2
        } else if (getData2().compare(to_add) < 0) { //is the new data the largest?
            setLeft(new Node(getData1(), this)); //set the new left to smallest data
            setRight(new Node(to_add, this)); //set new right as largest data
            setData1(getData2()); //remove data2
            setData2(null);
        } else { //new data is the middle value
            setLeft(new Node(getData1(), this)); //set the new left to smallest data
            setRight(new Node(getData2(), this)); //set new right as largest data
            setData1(to_add);
            setData2(null);  //remove data2
        }
    }

    private void pushUp(Node to_add) {
        if (this.getData2() == null) { //check if to_add is a one item node
            if (getData1().compare(to_add.getData1()) > 0) { //to_add's name is less than data1's
                setData2(getData1()); //shuffle the data to the correct place
                setData1(to_add.getData1());
                //link up the nodes and set this as parent
                setLeft(to_add.getLeft());
                getLeft().setParent(this);
                setCenter(to_add.getRight());
                getCenter().setParent(this);
            } else { //to_add's name is greater than data1's
                setData2(to_add.getData1()); //insert to_add's data into data2
                //link up the nodes and set this as parent
                setCenter(to_add.getLeft());
                getCenter().setParent(this);
                setRight(to_add.getRight());
                getRight().setParent(this);
            }
        } else { //to_add is a triple item node and must be split differently
            splitTripleNode(to_add);

            if (getParent() != null) { //if this node is not root
                getParent().pushUp(this); //push it up to it parent and try to insert
            }
        }
    }

    private void splitTripleNode(Node to_add) {
        Node temp = null; //to help with shuffling data
        if (getData1().compare(to_add.getData1()) > 0) { //to_add's name is less than data1's
            temp = new Node(getData2(), this); //allocate a new node to insert
            //insert the nodes into the correct spots and set their parents
            setLeft(to_add); //send new data to the left
            getLeft().setParent(this); //set parent
            setData2(null); //remove data2 as it is now in temp
            temp.setLeft(getCenter()); //set this center to temp left
            temp.getLeft().setParent(temp); //set parent
            temp.setRight(getRight()); //set this right to temp right
            temp.getRight().setParent(temp); //set parent
            setRight(temp); //insert temp into this right
            setCenter(null); //remove center
        } else if (getData2().compare(to_add.getData1()) > 0) { //to_add's name is less than data2's
            temp = new Node(getData1(), this); //allocate a new node to insert
            //insert the new nodes into the correct spots and set their as parents
            temp.setLeft(getLeft()); //set temp left to this left
            temp.getLeft().setParent(temp); //set parent
            temp.setRight(to_add.getLeft()); //set temp right to new data's right
            temp.getRight().setParent(temp); //set parent
            setLeft(temp);  //insert temp into this left
            temp = new Node(getData2(), this); //allocate a new node to insert and set parent
            temp.setLeft(to_add.getRight()); //set temp left to new data's right
            temp.getLeft().setParent(temp); //set parent
            temp.setRight(getRight()); //set temp right to this right
            temp.getRight().setParent(temp); //set parent
            setRight(temp); //insert temp into this right
            setData1(to_add.getData1()); //insert new data's data1 into this' data1
            setData2(null); //remove data2
            setCenter(null); //and center
        } else { //to_add's name is greater than data2's
            //insert the new nodes into the correct spots and set their as parents
            setRight(to_add); //set this right to new node
            getRight().setParent(this); //set parent
            temp = new Node(getData1(), this); //allocate a new node to insert
            temp.setLeft(getLeft()); //set temp left to this left
            temp.getLeft().setParent(temp); //set parent
            temp.setRight(getCenter()); //set temp right to this center
            temp.getRight().setParent(temp); //set parent
            setLeft(temp); //insert new node at this left
            setData1(getData2()); //move data2 to data1
            setData2(null); //remove data2
            setCenter(null); //and center
        }
    }

    public void insert(Destination to_add) {
        //checks to see if the node is a leaf or if it is root
        if (isLeaf()) {
            //check if the node already has two pieces of data
            if (getData2() == null) { //is a single data leaf
                if (getData1().compare(to_add) < 0) //is to_add greater than data1
                    setData2(to_add); //if its already smaller set data2 to the new data
                else { //if its larger shuffle data1 to data2 and enter the new data in data1
                    setData2(getData1());
                    setData1(to_add);
                }
            } else {
                splitNode(to_add); //if the node already has two data items split it into a new node

                if (getParent() != null) { //if this node is not root
                    getParent().pushUp(this); //push it up to it parent and try to insert
                }
            }
        } else { //if we are not at a leaf continue traversing the tree
            if (getData1().compare(to_add) > 0) { //if new data is less than data1
                getLeft().insert(to_add);
            } else if (getData2() == null) { //if new data is greater than data1 and it is a one data node
                getRight().insert(to_add);
            } else { //node is a two data node
                if (getData2().compare(to_add) > 0) //new data is in between data1 and data2
                    getCenter().insert(to_add);
                else { //new item is the greatest
                    getRight().insert(to_add);
                }
            }
        }
    }


    public Destination retrieve(String match) {
        Destination found;
        //check to see if the current node is a match
        if (getData1().getName().equals(match)) {
            found = getData1();
        } else if (getData2().getName().equals(match)) {
            found = getData2();
        } else if (getData1().getName().compareTo(match) > 0) { //if the data we are looking for is smaller than data1
            found = getLeft().retrieve(match);//send the function left
        } else if (getData2() != null && getData2().getName().compareTo(match) > 0) {//if the data we are looking for is smaller than data2
            found = getCenter().retrieve(match);//send the function down the center
        } else {
            if (getRight() != null)
                found = getRight().retrieve(match);
            else
                return null;
        }

        return found;
    }

    public void display() {
        //display all nodes smaller than this one
        if (getLeft() != null)
            getLeft().display();
        //display this nodes data1
        if (getData1() != null)
            data1.display();
        //display all nodes with size between data1 and data2
        if (getCenter() != null)
            getCenter().display();
        //display this nodes data2
        if (getData2() != null)
            data2.display();
        if (getRight() != null)
            getRight().display();
    }
}
