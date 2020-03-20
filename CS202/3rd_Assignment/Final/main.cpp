/*
=================================================================================================
Author: Riley Collins
Date: 02/23/18
Class: CS 202
Description: This file contains the client side application that works with the sport and 
application class hierarchys.
=================================================================================================
*/

//preprocessor directives
#include "sport.h"
#include "application.h"

int main() {
  application app1;
  BST tree1;
  char user_choice;
  //run the program in a loop
  do {
  app1.greeting(tree1);

  //see if the user wants to continue running the program
  cout << "\nWould You Like To Continue? ( Y / N )" << endl;
  cin >> user_choice;
  cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear
  } while(user_choice == 'y' || user_choice == 'Y');
  
  return 0;
}
