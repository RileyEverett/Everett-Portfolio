/*
=========================================================================================================
Author: Riley Collins
Date: 02/14/18
Class: CS 202
Description: This file contains the prototypes and definitions for the application, node, and 
input_node classes.
=========================================================================================================
*/

#include "application.h"

node::node() {
  data = NULL;
  next = NULL;
  previous = NULL;
}

node::node(node* obj) {
  if(const formal * F_ptr = dynamic_cast<const formal*>(obj->data)) {
    data = new formal(*F_ptr);
  }
  else if(const casual * C_ptr = dynamic_cast<const casual*>(obj->data)) {
    data = new casual(*C_ptr);
  }
  else if(const texting * T_ptr = dynamic_cast<const texting*>(obj->data)) {
    data = new texting(*T_ptr);
  }
  else
    data = NULL;
  
  next = NULL;
  previous = NULL;
}

node::~node() {
  if(data)
    data->delete_all();
}

int node::node_translate(input_node * head) {
  //if there is another node in the DLL give them a copy of the input before altering it
  if(next) {
    input_node * head_copy = new input_node(head);
    next->node_translate(head_copy);
  }
  //alter and display this nodes translation
  data->translate(head);
  cout << "Altered Input: ";
  head->display();
  cout << endl;
  return 0;
}

int node::build(node * current) {
  //allocate a formal class in this node
  data = new formal;
  previous = NULL;
  //make a node in formals next that is a casual
  next = new node;
  node * second = next;
  second->data = new casual;
  second->previous = current;
  //make a node in casuals next that is a texting
  second->next = new node;
  node * third = second->next;
  third->data = new texting;
  second->next = third;
  third->previous = second;
  third->next = NULL;
  //all nodes linked together and have NULL pointers on either end
  return 0;
}

input_node::input_node(): data(NULL), next(NULL) {}

input_node::input_node(input_node *obj) {
  data = new char[strlen(obj->get_data()) + 1]; //allocate the first node and set pointers to the next in the LLL
  strcpy(data,obj->get_data());
  next = new input_node;
  input_node * local_current = next;
  input_node * obj_current = obj->get_next(); 
  while(obj_current && obj_current->get_next()) { //copy over the data until the last node of the obj is hit
    local_current->get_data() = new char[strlen(obj_current->get_data()) + 1];
    strcpy(local_current->get_data(), obj_current->get_data());
    local_current->get_next() = new input_node;
    local_current = local_current->get_next();
    obj_current = obj_current->get_next();
  }
  //allocate the last node and set next to NULL
  if(obj_current && obj_current->get_data()) {
    local_current->get_data() = new char[strlen(obj_current->get_data()) + 1];
    strcpy(local_current->get_data(), obj_current->get_data());
  }
  local_current->get_next() = NULL;
}

input_node::~input_node() {
  delete_all();
}

input_node*& input_node::get_next(){
    return next;
}

char*& input_node::get_data() {
    return data;
}

int input_node::replace_data(char* new_data) {
  //replace the local data with the passed in char *
  delete data;
  data = new char[strlen(new_data) + 1];
  strcpy(data, new_data);
  return 0;
}

bool input_node::compare(char * to_compare) {
  //if the char*'s are the same return true, false otherwise 
  if(strcmp(data, to_compare) == 0)
    return true;
  else
    return false;
}

int input_node::delete_all() {
  //delete all input nodes
  if(this) {
    if(data)
      delete data;
    if(next)
      next->delete_all();
    data = NULL;
    next = NULL;
  }
  return 0;
}

int input_node::display() {
  //display all data in the input node LLL
  if(data) {
    cout << data << " ";
    if(next)
      next->display();
  }
  return 0;
}

int input_node::set_data(char * new_data) {
  //sets the local data to the passed in char *
  data = new char[strlen(new_data) + 1];
  strcpy(data, new_data);
  return 0;
}

int input_node::set_next(input_node * new_next) {
  //sets local next to the passed in input_node *
  next = new_next;
  return 0;
}


application::application(): head(NULL) {}

application::~application() { 
  head->delete_all();
}

int application::greeting(node* DLL) {
  //main user interface
  int user_choice;
  cout << "Please Choose An Option Below" << endl;
  cout << "Option 1: Translate New Input" << endl;

  cin >> user_choice;
  cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear

  
  //process the users choice
  switch(user_choice) {
  case 1:
    cout << "Please enter the text to be translated" << endl;
    cout << "When all text has been entered add a space and vertical bar e.g. |" << endl;
    cout << "try entering: (be mate laugh joking because what see you boy hello |) to see all translations" << endl;
    new_text_line();
    call_translate(DLL);
    break;
  default: cout << "Error: Invalid Input!" << endl; 
    break;
  } 
  return 0;
}

int application::display() {
  //calls the input_node display function
  head->display();
  return 0;
}

int application::new_text_line() {
  //reads the data the user types into a LLL of input nodes one word per node
  char input[MAX_USER_INPUT];
  while(cin.peek() != '|') {
    cin.getline(input, MAX_USER_INPUT, ' ');
    input_node * temp = head;
    head = new input_node;
    head->set_data(input);
    head->set_next(temp);
  }
  cout << "Input Data: ";
  display();
  cout << endl;
  return 0;
}

int application::call_translate(node * DLL) {
  //calls the node_translate function in the node class
  DLL->node_translate(head);
  return 0;
}
