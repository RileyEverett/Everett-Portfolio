/*
=======================================================================================================
Author: Riley Collins
Date: 01/26/18
Class: CS202
Description: This file contains the implementation of functions in the drone class hierarchy.
=======================================================================================================
*/

#include "drone.h"
#include "graph.h"

//default
drone::drone() {
  weight = 0;
  batteryLife = 0;
  maxSpeed = 0;
}

drone::drone(int drone_weight, int battery, int speed) {
  weight = drone_weight;
  batteryLife = battery;
  maxSpeed = speed;
}

//displays all drone's info
int drone::display() {
  cout << "Weight: " << weight << " Lbs" << endl;
  cout << "Battery Life: " << batteryLife << " Hrs" << endl;
  cout << "Max Speed: " << maxSpeed << " Mph" << endl;
  return 0;
}

//default
camera_drone::camera_drone() {
  media_storage = 1024;
}

camera_drone::camera_drone(int drone_weight, int battery, int speed): drone(drone_weight,battery,speed) {
  media_storage = 1024;
}

//output the ammount of storage left
int camera_drone::check_storage() {
  cout << "\nThere Is Currently " << media_storage << " Mb(s) Of Storage Remaining." << endl;
  return 0;
}

//tries to take a 10Mb photo and outputs success or failure 
int camera_drone::take_photo() {
  if(media_storage >= 10) {
    media_storage = media_storage - 10;
    cout << "\nPhoto Saved" << endl;
  }
  else {
    cout << "\nNot Enough Media Storage Available" << endl;
  }
  return 0;
}

//default
delivery_drone::delivery_drone() {
  destination = NULL;
  package = NULL;
}


delivery_drone::delivery_drone(int drone_weight, int battery, int speed): drone(drone_weight,battery,speed) {
  destination = NULL;
  package = NULL;
}

//calls the base class
delivery_drone::delivery_drone(delivery_drone &obj) {}

//calls the base class
delivery_drone::~delivery_drone() {}


int delivery_drone::pick_up_package(char * new_package) {
  if(new_package) {
    package = new char[strlen(new_package) + 1];
    strcpy(package, new_package);
  }
  else {
    return -1;
  }
  return 0;
}

int delivery_drone::deliver_package() {
  delete package;
  package = NULL;
  return 0;
}
int delivery_drone::set_destination(vertex * dest) {
  return 0;
}

//calls the base class
hobby_drone::hobby_drone() {
  
}

//calls the base class
hobby_drone::hobby_drone(int drone_weight, int battery, int speed): drone(drone_weight,battery,speed){
  
}

int hobby_drone::barrel_roll() {
  cout << "\nThe Drone Does A Barrel Roll In The Sky" << endl;
  return 0;
}
int hobby_drone::loop_the_loop() {
  cout << "\nThe Drone Does A Large Arching Loop In The Sky" << endl;
  return 0;
}

int hobby_drone::fly_by() {
  cout << "\nThe Drone Does A Very Close And Fast Pass Right By You" << endl;
  cout << "\nWooooooooooooosh" << endl;
  return 0;
}

