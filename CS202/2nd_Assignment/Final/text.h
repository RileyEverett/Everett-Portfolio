/*
=========================================================================================================
Author: Riley Collins
Date: 02/14/18
Class: CS 202
Description: This file contains the prototypes and definitions for the text class hierarchy.
=========================================================================================================
*/

#ifndef _TEXT_
#define _TEXT_

#include "application.h"
#include <fstream>

using namespace std;

//prototype
class input_node;

class text {
 public:
  text(); //constructor
  text(input_node*); //copy constructor
  virtual ~text(); //destructor
  int build(ifstream&); //takes in input stream and builds a LLL of char *'s of the text in the .txt
  int translate(input_node*); //translates all of the matching char*'s in the input_node to the classes char*'s 
  int delete_all(); //deletes all input_node's in head
  
 protected:
  input_node * head;
};


class formal: public text {
 public:
  formal(); //constructor
  ~formal(); //destructor
  int translate(input_node*); //translates all of the matching char*'s in the input_node to the classes char*'s
};


class casual: public text {
 public:
  casual(); //constructor
  ~casual(); //destructor
  int translate(input_node*); //translates all of the matching char*'s in the input_node to the classes char*'s
};


class texting: public text {
 public:
  texting(); //constructor
  ~texting(); //destructor
  int translate(input_node*); //translates all of the matching char*'s in the input_node to the classes char*'s
};

#endif //_TEXT_
