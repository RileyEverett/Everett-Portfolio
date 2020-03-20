/*
=========================================================================================================
Author: Riley Collins
Date: 02/14/18
Class: CS 202
Description: This file contains function implementation for the text class hierarchy.
=========================================================================================================
*/

#include "text.h"
text::text() {}

text::text(input_node* obj) {
  head = new input_node(obj);
}

text::~text() {
  head->delete_all();
}

int text::translate(input_node * obj) {
  if(!obj || !obj->get_data()) //while there is still data to be translated
    return 0;

  input_node * current = head;
  while(current && current->get_next()) {
    if(current->compare(obj->get_data())){ //if a mtach is found
      input_node * temp = current->get_next(); //get the next node
      obj->replace_data(temp->get_data()); //alter the data in obj to next node
    }
    //move current two nodes forward so that only the correct words are tested
    current = current->get_next();
    current = current->get_next();
  }
  //call the function again to get through all obj nodes
  translate(obj->get_next());
  
  return 0;
}

int text::build(ifstream &file) {
  char input[MAX_USER_INPUT];
  if(file.is_open()) { //check if the file is open
    while(!file.eof()) { //while the file is not eof
      file.getline(input, MAX_USER_INPUT, '|'); //read in the text form the . txt to a LLL of char *'s
      if(strlen(input) != 0) {
	input_node * temp = head;
	head = new input_node;
	head->set_data(input);
	head->set_next(temp);
      }
    }
  }
  else {
    return -1; //fail state
  }
  return 0;
}

int text::delete_all() {
  delete head;
  head = NULL;
  return 0;
}

formal::formal() {
  //sets up the LLL of char*'s
  head = NULL;
  ifstream file;
  file.open ("formal.txt");
  build(file);
  file.close();
}

formal::~formal() {
  
}

int formal::translate(input_node * obj) {return 0;}

casual::casual() {
  //sets up the LLL of char*'s
  head = NULL;
  ifstream file;
  file.open ("casual.txt");
  build(file);
  file.close();
}

casual::~casual() {}

int casual::translate(input_node *obj) {return 0;}

texting::texting() {
  //sets up the LLL of char*'s
  head = NULL;
  ifstream file;
  file.open ("texting.txt");
  build(file);
  file.close();
}

texting::~texting() {}

int texting::translate(input_node *obj) {
  return 0;
}
