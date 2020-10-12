/*
=======================================================================================================
Author: Riley Collins
Date: 01/26/18
Class: CS202
Description: This file contains the class definitions and function prototypes
for the drone class hierarchy.
=======================================================================================================
*/

#ifndef _DRONE_
#define _DRONE_

#include "graph.h"

//prototype
class vertex;

using namespace std;

class drone {
 public:
  drone();
  drone(drone &obj);
  drone(int,int,int);
  virtual ~drone();
  int display();
  int take_photo();
  int check_storage();
  int record_video();
  int pick_up_package(char *);
  int deliver_package();
  int set_destination(vertex *);
  int barrel_roll();
  int loop_the_loop();
  int fly_by();
  
 protected:
  int weight;
  int batteryLife;
  int maxSpeed;
  
};

class camera_drone: public drone {
 public:
  camera_drone();
  camera_drone(int,int,int);
  camera_drone(camera_drone &obj);
  int take_photo();
  int check_storage();
  int record_video();
  int create_camera_drone(int,int,int);
 protected:
  int media_storage;
};

class delivery_drone: public drone {
 public:
  delivery_drone();
  delivery_drone(delivery_drone &obj);
  delivery_drone(int,int,int);
  ~delivery_drone();

  int create_delivery_drone(int,int,int);
  int pick_up_package(char *);
  int deliver_package();
  int set_destination(vertex *); 
 protected:
  vertex * destination;
  char * package;
};

class hobby_drone: public drone {
 public:
  hobby_drone();
  hobby_drone(int,int,int);
  hobby_drone(hobby_drone &obj);
  int create_hobby_drone(int,int,int);
  int barrel_roll();
  int loop_the_loop();
  int fly_by();
};

#endif //_DRONE_
