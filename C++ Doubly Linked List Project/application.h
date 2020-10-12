/*
=========================================================================================================
Author: Riley Collins
Date: 02/14/18
Class: CS 202
Description: This file contains the prototypes and definitions for the application classes.
=========================================================================================================
*/

#ifndef _APP_
#define _APP_

#include "text.h"
#include <iostream>
#include <string.h>

//to help with readability
using namespace std;

//to define max input
const int MAX_USER_INPUT = 250;

//prototypes
class text;
class input_node;
class formal;
class casual;
class texting;

//is used to create a DLL of Base class objects
class node {
 public:
  node(); //constructor
  node(node*); //copy constructor
  ~node(); //destructor
  int build(node*); //builds a DLL of the three text classes
  int node_translate(input_node*); //sends a input_node to each node in the DLL to be translated
 protected:
  text * data;
  node * previous;
  node * next;
};

class input_node {
 public:
  input_node(); //constructor
  input_node(input_node*); //copy constructor
  ~input_node(); //destructor
  bool compare(char*); //runs strcmp on the local member and input data
  int delete_all(); //deletes all data in the LLL of nodes
  int set_data(char*); //strcpys input into local member
  int set_next(input_node*); //sets local member to input
  int display(); //display all data in the LLL
  int replace_data(char*); //replaces the old data with the new input
  char*& get_data(); //returns a pointer to the local data's address
  input_node*& get_next(); //returns a pointer to the local next's address

 private:
  char * data;
  input_node * next;
};

//is the clients way of working with the DLL and the text hierarchy
class application {
 public:
  application(); //constructor
  application(input_node*); //copy constructor
  ~application(); //destructor
  
  //functions that set up the data to be translated
  int display(); //displays all data in head
  int greeting(node*); //promts the user for input and processes it
  int new_text_line(); //builds a LLL of input_nodes's out of the users input
  int call_translate(node*); //sends head to the node class to be translated 
 protected:
  input_node * head;
};
#endif //_APP_
