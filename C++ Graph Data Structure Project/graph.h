/*
=======================================================================================================
Author: Riley Collins
Date: 01/26/18
Class: CS202
Description: This file contains the class definitions and function prototypes 
for the graph data structure.
=======================================================================================================
*/

#ifndef _GRAPH_
#define _GRAPH_

//preprocessor directives
#include "drone.h"
#include <iostream>
#include <string.h>

//constatnt values
const int TABLE_SIZE = 5;
const int MAX_USER_INPUT = 250;

//prototype
class edge;
class drone;

class vertex {
 public:
  //constructors
  vertex();
  vertex(vertex &);
  int display();
  int drone_type();
  int new_edge(vertex *);
  int new_camera_drone(int,int,int);
  int new_delivery_drone(int,int,int);
  int new_hobby_drone(int,int,int);
  //destructor
  ~vertex();

  drone * vehicle;
  edge * adjacent_vehicle;
};

class edge {
 public:
  //constructors
  edge();
  edge(edge &edge_obj);
  //destructor
  ~edge();

  vertex * drone;
  edge * next;
};

class list {
 public:
  //constructors
  list();
  list(list& list_obj);
  //destructor
  ~list();
  int add_edge(int,int);
  int change_coords(int, int);
  int create_new_drone(int,int,int,int,int);
  int check_if_restricted(int);
  int set_new_restricted(int);
  int list_display();
  int take_photo(int);
  int record_video(int);
  int check_storage(int);
  int pick_up_package(int, char *);
  int deliver_package(int);
  int set_destination(int,int);
  int barrel_roll(int);
  int loop_the_loop(int);
  int fly_by(int);
  
 private:
  int * restricted_indices;
  vertex ** adjacency_list;
};

#endif //_GRAPH_
