/*
=================================================================================================
Author: Riley Collins
Date: 02/23/18
Class: CS 202
Description: This file contains the funtion implementation for the application class hierarchy
=================================================================================================
*/

#include "application.h"
#include "sport.h"

//prototypes
class sport;
class athlete;

int application::greeting(BST& tree1){
  //values to pass to functions calls.
  char sport_name[MAX_USER_INPUT];
  char sport_details[MAX_USER_INPUT];
  char sport_schedule[MAX_USER_INPUT];
  char athlete_name[MAX_USER_INPUT];
  char athlete_event[MAX_USER_INPUT];
  char athlete_past_medals[MAX_USER_INPUT];
  sport new_sport;
  athlete new_athlete;
  
  //dipslay options to user and get input
  int user_choice;
  cout << "Please Choose An Option Below" << endl;
  cout << "Option 1: Add A Sport" << endl;
  cout << "Option 2: Add An Athlete" << endl;
  cout << "Option 3: Display All Sports And Athlets" << endl;
  cin >> user_choice;
  cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear


  //process the users choice
  switch(user_choice) {
  case 1:
    cout << "What Is The Name Of The Sport" << endl;
    cin.getline(sport_name, MAX_USER_INPUT);

    cout << "What Are The Sport's Details" << endl;
    cin.getline(sport_details, MAX_USER_INPUT);
    
    cout << "What Is The Sport's Schedule" << endl;
    cin.getline(sport_schedule, MAX_USER_INPUT);

    new_sport.create_event(sport_name, sport_details, sport_schedule);
    if(tree1.insert(new_sport) == 0)
      cout << "\nSuccess" << endl;
    else
      cout << "\nError!" << endl;
    break;
  case 2:
    cout << "What Is The Name Of The Athlete" << endl;
    cin.getline(athlete_name, MAX_USER_INPUT);

    cout << "What Is The Name Of The Athlete's Event" << endl;
    cin.getline(athlete_event, MAX_USER_INPUT);
    
    cout << "Has The Athlete Previously Won Any Medals (Medal Type - Year)" << endl;
    cin.getline(athlete_past_medals, MAX_USER_INPUT);
    
    new_athlete.create_athlete(athlete_name, athlete_event, athlete_past_medals);
    if(tree1.insert_athlete(athlete_event, new_athlete) == 0)
      cout << "\nSuccess" << endl;
    else
      cout << "\nError!" << endl;
    break;
  case 3: cout << tree1; //overloaded operator to display function
    break;
  default: cout << "Error: Invalid Input!" << endl;
    break;
  }
  
  return 0;
}

node::node(){ //constructor
  data = NULL;
  left = NULL;
  right = NULL;
}
node::node(node& node){ //copy constructor
  this->data = node.data;
  this->left = node.left;
  this->right = node.right;
}
node::~node(){ //destructor
  delete data;
  if(left)
    delete left;
  if(right)
    delete right;
}

int node::display(node * root) {
  if(!root || !root->data) //base case
    return 0;
  display(root->left); //display smallest
  root->data->display(); //then middle
  display(root->right); //then largest
  return 0;
}

int node::insert(node* &root, sport& to_add) {
  if(!root) { //base case
    //allocate space and copy over to_add
    root = new node;
    root->data = new sport;
    *(root->data) = to_add; //use the overloaded operator;
  }
  else if(*(root->data) > to_add) //if the data (sport name) in root is greater than to_add go left
    insert(root->left, to_add);
  else
    insert(root->right, to_add); //else go right
  return 0;
}

int node::insert_athlete(node* &root, char* match, athlete& to_add) {
  if(!root) //base case
    return 0;
  else if(*(root->data) == match) {
    root->data->add_athlete(to_add);
  }
  else if(*(root->data) > match) //if the data (sport name) in root is greater than to_add go left
    insert_athlete(root->left, match, to_add);
  else
    insert_athlete(root->right, match, to_add); //else go right
  return 0;
}


int node::build_demo0(node* &root) {
  //set up the top three nodes to be demo nodes
  root = new node;
  root->left = new node;
  root->right = new node;
  //insert demo data into the nodes
  root->data = new sport;
  root->data->build_figure_skating();
  root->left->build_demo1(root->left);
  root->right->build_demo2(root->right);
  return 0;
}

int node::build_demo1(node* &root) {
  root->data = new sport; //allocates memory can calls a build function
  return root->data->build_skeleton();
}

int node::build_demo2(node* &root) {
  root->data = new sport; //allocates memory can calls a build function
  return root->data->build_snowboarding();
}

int node::delete_all(node* &root) {
  if(!root) //base case
    return 0;
  
  delete_all(root->left); //remove all to the left
  delete_all(root->right); //remove all to the right
  //remove root
  delete root->data;
  delete root;
  root = NULL;
  //return the number removed
  return 0;
}

BST::BST(){ //constructor
  root = NULL;
  build_basic();
}

BST::~BST(){ //destructor
  root->delete_all(root);
}

int BST::build_basic() {
  return root->build_demo0(root);
}

int BST::insert(sport& to_add){
  return root->insert(root, to_add);
}

int BST::insert_athlete(char* match, athlete& to_add) {
  return root->insert_athlete(root, match, to_add);
}

int BST::remove(){
  return 0;
}
int BST::find_athlete(){
  return 0;
}

int BST::display() {
  return root->display(root); //wrapper
}

ostream& operator << (ostream& out, BST& obj) {
  obj.display(); //displays all nodes in the BST
  return out;
}
