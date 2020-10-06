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
  application app1; //create a new application
  node * DLL = new node; //create a new Doubly Linked List 
  DLL->build(DLL); //built the DLL with the external data files
  
  app1.greeting(DLL); //run the program
  delete DLL; //deallocate the DLL
  return 0;
}
