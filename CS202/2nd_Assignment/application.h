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

const int MAX_USER_INPUT = 250;

//prototype
class text;
class input_node;
class formal;
class casual;
class texting;

//is used to create a DLL of Base class objects
class node {
 public:
  node();
  node(node*);
  ~node();
  int build(node*);
  int node_translate(input_node*);
 protected:
  text * data;
  node * previous;
  node * next;
};

class input_node {
 public:
  input_node();
  input_node(input_node*);
  ~input_node();
  bool compare(char*);
  int delete_all();
  int set_data(char*);
  int set_next(input_node*);
  int display();
  int replace_data(char*);
  char*& get_data();
  input_node*& get_next();
 private:
  char * data;
  input_node * next;
};

//is the clients way of working with the DLL and the text hierarchy
class application {
 public:
  application();
  application(input_node*);
  ~application();
  //functions that set up the data to be translated
  int display();
  int greeting(node*);
  int new_text_line();
  int call_translate(node*);
 protected:
  input_node * head;
};
#endif //_APP_
