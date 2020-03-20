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
  text();
  text(input_node*);
  virtual ~text();
  int build(ifstream&);
  int translate(input_node*);
  int delete_all();
  
 protected:
  input_node * head;
};


class formal: public text {
 public:
  formal();
  ~formal();
  int translate(input_node*);
  
 protected:
  
};


class casual: public text {
 public:
  casual();
  ~casual();
  int translate(input_node*);
  
 protected:
  
};


class texting: public text {
 public:
  texting();
  ~texting();
  int translate(input_node*);
   
 protected:
  
};

#endif //_TEXT_
