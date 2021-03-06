/*
=================================================================================================
Author: Riley Collins
Date: 02/23/18
Class: CS 202
Description: This file contains the class and funtion prototypes for the sport class hierarchy
=================================================================================================
*/

#ifndef _SPORT_
#define _SPORT_

#include <iostream>
#include <fstream>
#include <string.h>

//prototype
class ifstream;

class athlete {
 public:
  athlete(); //constructor
  athlete(athlete&); //copy constructor
  ~athlete(); //destructor
  //creates a new object and copys passed in name, event, and past_medals into it
  int create_athlete(char*,char*,char*);
  int display();
  int set_next(athlete*);
  //overloaded operators
  athlete& operator = (const athlete&);
  //functions to set up demo nodes
  int build_figure_skating();
  int build_skeleton();
  int build_snowboarding();
  int build_LLL(std::ifstream&);
  
 protected:
  char * name;
  char * event;
  char * past_medals;
  athlete * next;
};

class sport {
 public:
  sport(); //constructor
  sport(sport&); //copy constructor
  ~sport(); //destructor
  //displays the data in sport
  int display();
  //creates a new sport object with the name, details, and schedule passed in
  int create_event(char*, char*,char*);
  int add_athlete(athlete&);
  
  //overloaded operators
  sport& operator = (const sport&);
  sport operator + (const sport&) const;
  sport& operator += (const sport& obj);
  sport operator - (const sport&) const;
  sport& operator -= (const sport& obj);
  friend bool operator < (const sport& first, char* second);
  friend bool operator < (char * first, const sport& second);
  friend bool operator < (const sport& first, const sport& second);
  friend bool operator <= (const sport& first, char* second);
  friend bool operator <= (char * first, const sport& second);
  friend bool operator <= (const sport& first, const sport& second);
  friend bool operator > (const sport& first, char* second);
  friend bool operator > (char * first, const sport& second);
  friend bool operator > (const sport& first, const sport& second);
  friend bool operator >= (const sport& first, char* second);
  friend bool operator >= (char * first, const sport& second);
  friend bool operator >= (const sport& first, const sport& second);
  friend bool operator != (const sport& first, char* second);
  friend bool operator != (char * first, const sport& second);
  friend bool operator != (const sport& first, const sport& second);
  friend bool operator == (const sport& first, char* second);
  friend bool operator == (char * first, const sport& second);
  friend bool operator == (const sport& first, const sport& second);

  //functions to set up demo nodes
  int build_figure_skating();
  int build_skeleton();
  int build_snowboarding();
  
 protected:
  char * name;
  char * details;
  char * schedule;
  int num_of_athletes;
  athlete * head; //pointer to a LLL of athletes
};

#endif //_SPORT_
