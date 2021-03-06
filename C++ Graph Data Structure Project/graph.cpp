/*
=======================================================================================================
Author: Riley Collins
Date: 01/26/18
Class: CS202
Description: This file contains the class definitions and function prototypes
for the graph data structure.
=======================================================================================================
*/

//preprocessor directives
#include "graph.h"
#include "drone.h"

//prototype
class vertex;

vertex::vertex(): vehicle(NULL), adjacent_vehicle(NULL) {}

vertex::vertex(vertex &vertex_obj): vehicle(vertex_obj.vehicle), adjacent_vehicle(vertex_obj.adjacent_vehicle) {}

vertex::~vertex() {
  delete vehicle;
  vehicle = NULL;
  delete adjacent_vehicle;
  adjacent_vehicle = NULL;
}

int vertex::display() {
  if(vehicle) {
    vehicle->display();
      return 0;
  }
  else
    return -1;
}

int vertex::new_edge(vertex * to_add) {
  if(to_add) {
    edge * temp = adjacent_vehicle;
    adjacent_vehicle->drone = new vertex(*to_add);
    adjacent_vehicle->next = temp;
  }
  else {
    return -1;
  }
  return 0;
}

int vertex::drone_type() {
  /*
  //check if it is a camrea drone, if so return 1
  const camera_drone * C_ptr = dynamic_cast<const camera_drone*>(vehicle);
  if(C_ptr)
    return 1;

  //check if it is a delivery drone, if so return 2
  const delivery_drone * D_ptr = dynamic_cast<const delivery_drone*>(vehicle);
  if(D_ptr)
    return 2;

  //check if it is a hobby drone, if so return 3
  const hobby_drone * H_ptr = dynamic_cast<const hobby_drone*>(vehicle);
  if(H_ptr)
    return 3;
  */
  return 0;
}

int vertex::new_camera_drone(int weight, int battery, int speed) {
  if(!vehicle) {
    vehicle = new camera_drone(weight, battery, speed);
  return 0;
  }
  else
    return -1;
}

int vertex::new_delivery_drone(int weight, int battery, int speed) {
  if(!vehicle) {
    vehicle = new delivery_drone(weight, battery, speed);
    return 0;
  }
  else
    return -1;
}

int vertex::new_hobby_drone(int weight, int battery, int speed) {
  if(!vehicle) {
    vehicle = new hobby_drone(weight, battery, speed);
    return 0;
  }
  else
    return -1;
}


edge::edge(): drone(NULL), next(NULL) {}

edge::edge(edge &edge_obj): drone(edge_obj.drone), next(NULL){
  next = edge_obj.next;
}

edge::~edge() {
  delete drone;
  drone = NULL;
  delete next;
  next = NULL;
}

list::list() {
  //allocate a new list and set all pointers to NULL
  adjacency_list = new vertex*[TABLE_SIZE];
  for(int i = 0; i < TABLE_SIZE; ++i) {
    adjacency_list[i] = NULL;
  }

  //allocate a new restricted list and set all values to -1
  restricted_indices = new int[TABLE_SIZE];
  for(int k = 0; k < TABLE_SIZE; ++k) {
    restricted_indices[k] = -1;
  }
}

list::list(list &list_obj): adjacency_list(list_obj.adjacency_list) {}

list::~list() {
  delete [] adjacency_list;
  adjacency_list = NULL;
}

int list::add_edge(int source_index, int edge_index) {
  if(adjacency_list[source_index]) {
    adjacency_list[source_index]->new_edge(adjacency_list[edge_index]);
  }
  else {
    cout << "There Is No Drone At That Location!" << endl;
    return -1;
  }
  return 0;
}

int list::change_coords(int source_index, int dest_index) {
  //is there a drone at source?
  if(adjacency_list[source_index]) {
    //if the dest restricted?
    if(check_if_restricted(dest_index) == 0) {
      //is there a drone at dest?
      if(!adjacency_list[dest_index]) {
	adjacency_list[dest_index] = new vertex(*adjacency_list[source_index]);
	//delete source to complete the move
	delete [] adjacency_list[source_index];
	adjacency_list[source_index] = NULL;
      }
      else {
	cout << "There Is Already A Drone At Destination!" << endl;
	return -1;
      }
    }
    else {
      cout << "Destination Is A Restricted Area!" << endl;
      return -1;
    }
  }
  else {
    cout << "No Drone Is Located At That Position!" << endl;
    return -1;
  }
  return 0;
}

int list::set_new_restricted(int new_area) {
  //check to see if the index is already in the list
  int index = 0;
  while(restricted_indices[index] != -1) {
    if(restricted_indices[index] == new_area) {
      cout << "\nThat Area Is Already Restricted!" << endl;
      return -1;
    }
    ++index;
  }
  index = 0; //reset the index
  
  //find next open index
  while(index < TABLE_SIZE && restricted_indices[index] != -1)
    ++index;
  //check if array if full
  if(index >= TABLE_SIZE) {
    cout << "\nAll areas Are Already Restricted!" << endl;
      return -1;
  }
  //add the new area to the area
  restricted_indices[index] = new_area;
  return 0;
}

int list::check_if_restricted(int area) {
  int index = 0;
  //check all restricted indices to see if area is among them, if so return -1 
  while(restricted_indices[index] != -1) {
    if(restricted_indices[index] == area)
      return -1;
    ++index;
  }
  return 0;
}

int list::create_new_drone(int type, int index, int weight, int battery, int speed) {
  //check to see if the index is null
  if(!adjacency_list[index]) {
    //check to see if the index is restricted
    if(check_if_restricted(index) == 0) {
      //create a new drone
      adjacency_list[index] = new vertex;
      if(type == 1)
	adjacency_list[index]->new_camera_drone(weight,battery,speed);
      else if(type == 2)
	adjacency_list[index]->new_delivery_drone(weight,battery,speed);
      else
	adjacency_list[index]->new_hobby_drone(weight,battery,speed);
    }
      else {
	cout << "\nThat Is A Restricted No Fly Location" << endl;
	return -1;
      }
  }
  else {
    cout << "\nThere Is Already A Drone In That Location" << endl;
    return -1;
  }
     return 0;
}

int list::list_display() {
  //display all drones in the list
  int empty_indices = 0;
  for(int i = 0; i < TABLE_SIZE; ++i) {
    cout << "\nLocation " << i << " Contains ";
    if(adjacency_list[i]) { //check if NULL
      cout << "A Drone With Stats" << endl;
      adjacency_list[i]->display();
    }
    else {
      cout << "No Drone!" << endl;
      ++empty_indices;
    }
  }
  //check if the list is empty
  if(empty_indices >= TABLE_SIZE)
    return -1;
  else
    return 0;
}

int list::take_photo(int location) {
  if(adjacency_list[location]) { //check if null
    adjacency_list[location]->vehicle->take_photo();
  }
  else {
    cout << "There Is No Drone At That Location" << endl;
    return -1;
  }
  return 0;
}
int list::record_video(int location) {
  if(adjacency_list[location]) { //check if null
    adjacency_list[location]->vehicle->record_video();
  }
  else {
    cout << "There Is No Drone At That Location" << endl;
    return -1;
  }
  return 0;
}
int list::check_storage(int location) {
  if(adjacency_list[location]) { //check if null
    adjacency_list[location]->vehicle->check_storage();
  }
  else {
    cout << "There Is No Drone At That Location" << endl;
    return -1;
  }
  return 0;
}
int list::pick_up_package(int location, char * package_name) {
  if(adjacency_list[location]) { //check if null
    adjacency_list[location]->vehicle->pick_up_package(package_name);
  }
  else {
    cout << "There Is No Drone At That Location" << endl;
    return -1;
  }
  return 0;
}
int list::deliver_package(int location) {
  if(adjacency_list[location]) { //check if null
    adjacency_list[location]->vehicle->deliver_package();
  }
  else {
    cout << "There Is No Drone At That Location" << endl;
    return -1;
  }
    return 0;
}
int list::set_destination(int location, int destination) {
  if(adjacency_list[location]) { //check if null
    adjacency_list[location]->vehicle->set_destination(adjacency_list[destination]);
  }
  else {
    cout << "There Is No Drone At That Location" << endl;
    return -1;
  }
  return 0;
}
int list::barrel_roll(int location) {
  if(adjacency_list[location]) { //check if null
    adjacency_list[location]->vehicle->barrel_roll();
  }
  else {
    cout << "There Is No Drone At That Location" << endl;
    return -1;
  }
  return 0;
}
int list::loop_the_loop(int location) {
  if(adjacency_list[location]) { //check if null
    adjacency_list[location]->vehicle->loop_the_loop();
  }
  else {
    cout << "There Is No Drone At That Location" << endl;
    return -1;
  }
  return 0;
}
int list::fly_by(int location) {
  if(adjacency_list[location]) { //check if null
    adjacency_list[location]->vehicle->fly_by();
  }
  else {
    cout << "There Is No Drone At That Location" << endl;
    return -1;
  }
  return 0;
}
