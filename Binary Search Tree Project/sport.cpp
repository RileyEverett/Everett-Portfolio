/*
=================================================================================================
Author: Riley Collins
Date: 02/23/18
Class: CS 202
Description: This file contains the funtion implementation for the sport class hierarchy
=================================================================================================
*/

#include "sport.h"
#include "application.h"

athlete::athlete(){ //constructor
  name = NULL;
  event = NULL;
  past_medals = NULL;
  next = NULL;
}
athlete::athlete(athlete& obj){ //copy constructor
  *this = obj; //using operator overloading
}

athlete::~athlete(){ //destructor
  delete [] name;
  delete [] event;
  delete [] past_medals;
  if(next)
    delete next;
}

int athlete::create_athlete(char* name, char* event, char* past_medals){
  //allocate space and copy in the data
  this->name = new char[strlen(name) + 1];
  strcpy(this->name,name);
  this->event = new char[strlen(event) + 1];
  strcpy(this->event,event);
  this->past_medals = new char[strlen(past_medals) + 1];
  strcpy(this->past_medals,past_medals);
  return 0;
}


int athlete::display() {
  //cout the details
  cout << "Athlete Name: " << name << ", ";
  cout << "Athlete's Event: " << event << ", ";
  cout << "Athlete's Past Medals: " << past_medals << endl;
  //display the next node if its not NULL
  if(next && next->name)
    next->display();
  return 0;
}

int athlete::set_next(athlete* to_set) {
  next = to_set; //set next to the passed in valuse and return
  return 0;
}

athlete& athlete::operator = (const athlete& obj) {
  if(this == &obj) //test to see if same object
    return *this;
  else { //delete any allocated memory and copy in the new data
    if(this->name)
      delete [] this->name;
    this->name = new char[strlen(obj.name) + 1];
    strcpy(name,obj.name);
    if(this->event)
      delete [] this->event;
    this->event = new char[strlen(obj.event) + 1];
    strcpy(event,obj.event);
    if(this->past_medals)
      delete [] this->past_medals;
    this->past_medals = new char[strlen(obj.past_medals) + 1];
    strcpy(past_medals,obj.past_medals);
  }
  return *this; //return the modified object
}

int athlete::build_figure_skating() {
  //get the file input
  std::ifstream file;
  file.open("figureskating.txt");
  //build the LLL of athletes
  build_LLL(file);
  file.close();
  return 0;
}

int athlete::build_skeleton() {
  //get the file input
  std::ifstream file;
  file.open("skeleton.txt");
  //build the LLL of athletes
  build_LLL(file);
  file.close();
  return 0;
}

int athlete::build_snowboarding() {
  //get the file input
  std::ifstream file;
  file.open("snowboarding.txt");
  //build the LLL of athletes
  build_LLL(file);
  file.close();
  return 0;
}

int athlete::build_LLL(std::ifstream &file) {
  if(file.is_open()) { //check to see if the file is open
    if(file.eof()) { //and thats is not at the end
      return 0;
    }
    else {
      //set up space to store in file input
      char input_name[MAX_USER_INPUT];
      char input_event[MAX_USER_INPUT];
      char input_past_medals[MAX_USER_INPUT];
      //read in data from the file
      file.getline(input_name, MAX_USER_INPUT, '|');
      file.getline(input_event, MAX_USER_INPUT, '|');
      file.getline(input_past_medals, MAX_USER_INPUT, '|');
      //copy over the file data
      if(strlen(input_name) > 0) {
	name = new char[strlen(input_name) + 1];
	strcpy(name,input_name);
	event = new char[strlen(input_event) + 1];
	strcpy(event,input_event);
	past_medals = new char[strlen(input_past_medals) + 1];
	strcpy(past_medals,input_past_medals);
      }
      //allocate the next node an call again
      next = new athlete;
      if(next->build_LLL(file) == 0) {
	delete next; //if at eof set end of the LLL to NULL
	next = NULL;
      }
    }
  }
  return 1;
}

sport::sport(){ //constructor
  name = NULL;
  details = NULL;
  schedule = NULL;
  num_of_athletes = 0;
  head = NULL;
}

sport::sport(sport& obj){ //copy constructor
  *this = obj; //using operator overloading
}
sport::~sport(){ //destructor
  delete [] name;
  delete [] details;
  delete [] schedule;
  if(head)
    delete head;
}
int sport::display() {
  //cout the details
  cout << "\n\nSport Name: " << name << ", ";
  cout << "Sport Details: " << details << ", ";
  cout << "Sport Schedule: " << schedule << endl;
  //display the next node if its not NULL
  if(head)
    head->display();
  return 0;
}

int sport::create_event(char* name, char* details, char* schedule){
  //allocate space and copy over the data
  this->name = new char[strlen(name) + 1];
  strcpy(this->name, name);
  this->details = new char[strlen(details) + 1];
  strcpy(this->details, details);
  this->schedule = new char[strlen(schedule) + 1];
  strcpy(this->schedule, schedule);
  return 0;
}

int sport::add_athlete(athlete& to_add){
  athlete * temp = head;
  head = new athlete;
  *head = to_add;
  head->set_next(temp);
  return 0;
}

int sport::build_figure_skating() {
  //set up space to store in file input
  char input_name[MAX_USER_INPUT];
  char input_details[MAX_USER_INPUT];
  char input_schedule[MAX_USER_INPUT];
  //get the file input
  std::ifstream file;
  file.open("sport.txt");
  file.getline(input_name, MAX_USER_INPUT, '|');
  file.getline(input_details, MAX_USER_INPUT, '|');
  file.getline(input_schedule, MAX_USER_INPUT, '|');
  //copy over the file data
  name = new char[strlen(input_name) + 1];
  strcpy(name,input_name);
  details = new char[strlen(input_details) + 1];
  strcpy(details,input_details);
  schedule = new char[strlen(input_schedule) + 1];
  strcpy(schedule,input_schedule);
  file.close();
  //build the list of athletes
  head = new athlete;
  head->build_figure_skating();
  return 0;
}

int sport::build_skeleton() {
  //set up space to store in file input
  char input_name[MAX_USER_INPUT];
  char input_details[MAX_USER_INPUT];
  char input_schedule[MAX_USER_INPUT];
  //get the file input
  std::ifstream file;
  file.open("sport.txt");
  for(int i = 0; i < 1; ++i) { //get the second data in the file
    file.getline(input_name, MAX_USER_INPUT, '|');
    file.getline(input_details, MAX_USER_INPUT, '|');
    file.getline(input_schedule, MAX_USER_INPUT, '|');
  }
  //copy over the file data
  name = new char[strlen(input_name) + 1];
  strcpy(name,input_name);
  details = new char[strlen(input_details) + 1];
  strcpy(details,input_details);
  schedule = new char[strlen(input_schedule) + 1];
  strcpy(schedule,input_schedule);
  file.close();
  //build the list of athletes
  head = new athlete;
  head->build_skeleton();
  return 0;
}

int sport::build_snowboarding() {
  //set up space to store in file input
  char input_name[MAX_USER_INPUT];
  char input_details[MAX_USER_INPUT];
  char input_schedule[MAX_USER_INPUT];
  //get the file input
  std::ifstream file;
  file.open("sport.txt");
  for(int i = 0; i < 2; ++i) { //get the third data in the file
    file.getline(input_name, MAX_USER_INPUT, '|');
    file.getline(input_details, MAX_USER_INPUT, '|');
    file.getline(input_schedule, MAX_USER_INPUT, '|');
  }
  //copy over the file data
  name = new char[strlen(input_name) + 1];
  strcpy(name,input_name);
  details = new char[strlen(input_details) + 1];
  strcpy(details,input_details);
  schedule = new char[strlen(input_schedule) + 1];
  strcpy(schedule,input_schedule);
  file.close();
  //build the list of athletes
  head = new athlete;
  head->build_snowboarding();
  return 0;
}

sport sport::operator + (const sport& obj) const {
  sport temp;
  temp.num_of_athletes = (this->num_of_athletes + obj.num_of_athletes);
  return temp;
}

sport& sport::operator += (const sport& obj) {
  this->num_of_athletes += obj.num_of_athletes;
  return *this;
}

sport sport::operator - (const sport& obj) const {
  sport temp;
  temp.num_of_athletes = (this->num_of_athletes - obj.num_of_athletes);
  return temp;
}

sport& sport::operator -= (const sport& obj) {
  this->num_of_athletes -= obj.num_of_athletes;
  return *this;
}

bool operator < (const sport& first, char* second) {
  return (strcmp(first.name,second) < 0);
}
bool operator < (char * first, const sport& second){
  return (strcmp(first,second.name) < 0);
}
bool operator < (const sport& first, const sport& second){
  return (strcmp(first.name,second.name) < 0);
}

bool operator <= (const sport& first, char* second) {
  return (strcmp(first.name,second) <= 0);
}
bool operator <= (char * first, const sport& second){
  return (strcmp(first,second.name) <= 0);
}
bool operator <= (const sport& first, const sport& second){
  return (strcmp(first.name,second.name) <= 0);
}
bool operator > (const sport& first, char* second) {
  return (strcmp(first.name,second) > 0);
}
bool operator > (char * first, const sport& second){
  return (strcmp(first,second.name) > 0);
}
bool operator > (const sport& first, const sport& second){
  return (strcmp(first.name,second.name) > 0);
}
bool operator >= (const sport& first, char* second) {
  return (strcmp(first.name,second) >= 0);
}
bool operator >= (char * first, const sport& second){
  return (strcmp(first,second.name) >= 0);
}
bool operator >= (const sport& first, const sport& second){
  return (strcmp(first.name,second.name) >= 0);
}
bool operator != (const sport& first, char* second) {
  return (strcmp(first.name,second) != 0);
}
bool operator != (char * first, const sport& second){
  return (strcmp(first,second.name) != 0);
}
bool operator != (const sport& first, const sport& second){
  return (strcmp(first.name,second.name) != 0);
}
bool operator == (const sport& first, char* second) {
  return (strcmp(first.name,second) == 0);
}
bool operator == (char * first, const sport& second){
  return (strcmp(first,second.name) == 0);
}
bool operator == (const sport& first, const sport& second){
  return (strcmp(first.name,second.name) == 0);
}

sport& sport::operator=(const sport& obj) {
  if(this == &obj)
    return *this;
  else {
    if(this->name)
      delete [] this->name;
    this->name = new char[strlen(obj.name) + 1];
    strcpy(name,obj.name);
    if(this->details)
      delete [] this->details;
    this->details = new char[strlen(obj.details) + 1];
    strcpy(details,obj.details);
    if(this->schedule)
      delete [] this->schedule;
    this->schedule = new char[strlen(obj.schedule) + 1];
    strcpy(schedule,obj.schedule);
  }
  return *this;
}
