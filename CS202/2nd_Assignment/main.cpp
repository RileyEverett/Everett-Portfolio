/*
=========================================================================================================
Author: Riley Collins
Date: 02/09/18
Class: CS 202
Description: This file contains the client side application to the text class hierarchy.
========================================================================================================= 
*/

#include "text.h"
#include "application.h"

int main() {
  application app1;
  node * DLL = new node;
  DLL->build(DLL);
  
  app1.greeting(DLL);
  delete DLL;
  return 0;
}
