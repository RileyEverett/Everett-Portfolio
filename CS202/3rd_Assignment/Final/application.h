/*
=================================================================================================
Author: Riley Collins
Date: 02/23/18
Class: CS 202
Description: This file contains the class and funtion prototypes for the application class hierarchy
=================================================================================================
*/

#ifndef _APP_
#define _APP_

//preprocessor directives
#include "sport.h"

//prototypes
class BST;

//to aid readability
using namespace std;

//constant values
const int MAX_USER_INPUT = 250;

//prototypes
class athlete;
class sport;

//functions as an interface between the client side and the data structure
class application {
 public:
  
  int greeting(BST&);
  
};

class node {
 public:
  node(); //constructor
  node(node&); //copy constructor
  ~node(); //destructor
  int insert(node*&, sport&);
  int insert_athlete(node*&, char*, athlete&);
  int display(node*);
  int delete_all(node*&);
  //set up functions to create demo nodes
  int build_demo0(node* &root);
  int build_demo1(node* &root);
  int build_demo2(node* &root);
  
 protected:
  sport * data;
  node * left;
  node * right;
};

class BST {
 public:
  BST(); //constructor
  ~BST(); //destructor
  
  int build_basic();
  int insert(sport&);
  int insert_athlete(char*, athlete&);
  int remove();
  int find_athlete();
  int display();
  //overloads
  friend ostream& operator << (ostream& out, BST& obj);
  
 protected:
  node * root; //the root node in a binary search tree
};

#endif //_APP_
