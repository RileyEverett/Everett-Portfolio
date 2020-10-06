/*
=======================================================================================================
Author: Riley Collins
Date: 01/26/18
Class: CS202
Description: This file contains the main client side application for the drone class hierarchy  
and graph data structure.
=======================================================================================================
*/

#include "drone.h"
#include "graph.h"

//prototypes
void process_hobby_choice(list& list1, int& choice);
void process_delivery_choice(list& list1, int& choice);
void process_camera_choice(list& list1, int& choice);
void process_second_choice(list& list1, int& user_choice);

void print_options() {
  cout << "\nPlease Select An Option Below\n" << endl;
  cout << "Option 1: Create A New Drone" << endl;
  cout << "Option 2: Display All Drone's Info & Locations" << endl;
  cout << "Option 3: Fly A Drone To A Location" << endl;
  cout << "Option 4: Add A New Edge" << endl;
  cout << "Option 5: Use Drone Specific Abilities" << endl;
  cout << "Option 6: Exit The Program" << endl;
}

void print_second_menu() {
  cout << "\nPlease Select The Drone Type From The Options List Below\n" << endl;
  cout << "Option 1: Camera Drone" << endl;
  cout << "Option 2: Delivery Drone" << endl;
  cout << "Option 3: Hobby Drone" << endl;
}

void get_input(int& user_choice) {
  //get user input
  cin >> user_choice;
  cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear
}

void process_choice(list& list1, int user_choice, int& user_continue) {
  //user input variables
  int create_type;
  int create_index;
  int create_weight;
  int create_battery;
  int create_speed;
  int move_source;
  int move_dest;
  int edge_add_to;
  int edge_add_from;
  int abilities_choice;
  //process user input
  switch(user_choice) {
    //create a new drone
  case 1:
    do {
    cout << "What Is The Drone Type" << endl;
    cout << "Option 1: Camera Drone" << endl;
    cout << "Option 2: Delivery Drone" << endl;
    cout << "Option 3: Hobby Drone" << endl;
    cin >> create_type;
    cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear
    } while(create_type < 1 || create_type > 3); //loop if input is not a valid option
    
    cout << "What Is the Starting Location Of The Drone? (0 - " << (TABLE_SIZE - 1) << ")" << endl;
    cin >> create_index;
    cin.ignore(MAX_USER_INPUT, '\n');

    cout << "How Much Does The Drone Weigh? (In Lbs)" << endl;
    cin >> create_weight;
    cin.ignore(MAX_USER_INPUT, '\n');
    
    cout << "What Is The Drones Battery Life? (In Hours)" << endl;
    cin >> create_battery;
    cin.ignore(MAX_USER_INPUT, '\n');
    
    cout << "What Is The Drones Max Speed? (In MPH)" << endl;
    cin >> create_speed;
    cin.ignore(MAX_USER_INPUT, '\n');
    //call the funtion to make a new drone
    if(list1.create_new_drone(create_type, create_index, create_weight, create_battery, create_speed) == 0)
      cout << "\nSuccess!" << endl;
    else
      cout << "\nError!" << endl;
    break;
    //Display All Drone's Info & Locations
  case 2:
    if(list1.list_display() != 0)
      cout << "\nError: Nothing To Display!" << endl;
    break;
    //move a drone
  case 3:
    cout << "\nWhere Is The Drone Currently Located?" << endl;
    cin >> move_source;
    cin.ignore(MAX_USER_INPUT, '\n');
    
    cout << "\nWhere Would You Like The Drone To Move?" << endl;
    cin >> move_dest;
    cin.ignore(MAX_USER_INPUT, '\n');
    
    if(list1.change_coords(move_source, move_dest) == 0)
      cout << "\nSuccess!" << endl;
    else
      cout << "\nError!" << endl;    break;
    //add a new edge
  case 4:
    cout << "What Location Would You LIke To Add An Edge To?" << endl;
    cin >> edge_add_to;
    cin.ignore(MAX_USER_INPUT, '\n');
    
    cout << "What Is The Location Of The Edge?" << endl;
    cin >> edge_add_from;
    cin.ignore(MAX_USER_INPUT, '\n');

    if(list1.add_edge(edge_add_to,edge_add_from) == 0)
      cout << "Success!" << endl;
    else
      cout << "Error!" << endl;
    break;
    //Use Drone Specific Abilities
  case 5:
    print_second_menu();
    cin >> abilities_choice;
    cin.ignore(MAX_USER_INPUT, '\n');

    process_second_choice(list1, abilities_choice);
    break;
  case 6:
    //exit the do while loop
    user_continue = 1;
    break;
  default: cout << "\nError: Incorrect Input, Please Select A Valid Option" << endl;
    break;
  }
}

void process_second_choice(list& list1, int& user_choice) {
  //user input variable
  int sub_menu_choice;

  //proccess choice
  switch(user_choice) {
    //camera drone
  case 1:
    do {
      cout << "Please Choose An Ablilty" << endl;
      cout << "Option 1: Take A Photo" << endl;
      cout << "Option 2: Record Video" << endl;
      cout << "Option 3: Check Media Storage" << endl;
      cin >> sub_menu_choice;
      cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear
    } while(sub_menu_choice < 1 || sub_menu_choice > 3); //loop if input is not a valid option
    
    process_camera_choice(list1, sub_menu_choice);
    break;
    //delivery drone
  case 2:
    do {
      cout << "Please Choose An Ablilty" << endl;
      cout << "Option 1: Pick Up A Package" << endl;
      cout << "Option 2: Deliver Current Package" << endl;
      cout << "Option 3: Set Destination" << endl;
      cin >> sub_menu_choice;
      cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear
    } while(sub_menu_choice < 1 || sub_menu_choice > 3); //loop if input is not a valid option

    process_delivery_choice(list1, sub_menu_choice);
    break;
    //hobby drone
  case 3:
    do {
      cout << "Please Choose An Ablilty" << endl;
      cout << "Option 1: Do A Barrel Roll" << endl;
      cout << "Option 2: Do A Loop The Loop" << endl;
      cout << "Option 3: Do A Fly By" << endl;
      cin >> sub_menu_choice;
      cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear
    } while(sub_menu_choice < 1 || sub_menu_choice > 3); //loop if input is not a valid option

    process_hobby_choice(list1, sub_menu_choice);
    break;
    //if something goes wrong
  default:
    cout << "Error: Invalid Choice!" << endl;
    break;
  }
}

int get_location(int& location) {
  
  cout << "\nWhat Is The Current Location Of The Drone?" << endl;
  cin >> location;
  cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear

  while(location < 0 || location >= TABLE_SIZE) {
    cout << "\nError! Invalid Location\n" << endl;
    cout << "\nWhat Is The Current Location Of The Drone?" << endl;
    cin >> location;
    cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear
  }
  return 0;
}

void process_camera_choice(list& list1, int& choice) {
  //get the drones location
  int location;
  get_location(location);

  switch(choice) {
    //take photo
  case 1:
    if(list1.take_photo(location) == 0) {
      cout << "Success!" << endl;
    }
    else {
      cout << "Error!" << endl;
    }
    break;
    //record video
  case 2:
    if(list1.record_video(location) == 0) {
      cout << "Success!" << endl;
    }
    else {
      cout << "Error!" << endl;
    }
    break;
    //check media storage
  case 3:
    if(list1.check_storage(location) == 0) {
      cout << "Success!" << endl;
    }
    else {
      cout << "Error!" << endl;
    }
    break;
    //if something goes wrong
  default:
    cout << "Error: Invalid Choice!" << endl;
    break;
  }
}

void process_delivery_choice(list& list1, int& choice) {
  char package_name[MAX_USER_INPUT];
  int delivery_location;
  //get the drones location
  int location;
  get_location(location);
  
  switch(choice) {
    //pick up package
  case 1:
    cout << "What Is The Name Of The Package?" << endl;
    cin.getline(package_name, MAX_USER_INPUT);
    cin.ignore(MAX_USER_INPUT, '\n'); //buffer clear
    
    if(list1.pick_up_package(location, package_name) == 0) {
      cout << "Success!" << endl;
    }
    else {
      cout << "Error!" << endl;
    }
    break;
    //deliver package
  case 2:
    if(list1.deliver_package(location) == 0) {
      cout << "Success!" << endl;
    }
    else {
      cout << "Error!" << endl;
    }
    break;
    //set destination
  case 3:
    cout << "What Is The Destination?" << endl;
    get_location(delivery_location);
    if(list1.set_destination(location, delivery_location) == 0) {
      cout << "Success!" << endl;
    }
    else {
      cout << "Error!" << endl;
    }
    break;
    //if something goes wrong
  default:
    cout << "Error: Invalid Choice!" << endl;
    break;
  }
}

void process_hobby_choice(list& list1, int& choice) {
  //get the drones location
  int location;
  get_location(location);

  switch(choice) {
    //barrel roll
  case 1:
    if(list1.barrel_roll(location) == 0) {
      cout << "Success!" << endl;
    }
    else {
      cout << "Error!" << endl;
    }
    break;
    //loop the loop
  case 2:
    if(list1.loop_the_loop(location) == 0) {
      cout << "Success!" << endl;
    }
    else {
      cout << "Error!" << endl;
    }
    break;
    //fly by
  case 3:
    if(list1.fly_by(location) == 0) {
      cout << "Success!" << endl;
    }
    else {
      cout << "Error!" << endl;
    }
    break;
    //if something goes wrong
  default:
    cout << "Error: Invalid Choice!" << endl;
    break;
  }
}

int main() {
  list list1;
  int user_continue = 0;
  int user_choice = 0;
  
  //program intro
  cout << "Welcome To My Drone Watch Software" << endl;

  do {
    //print user options
    print_options();

    //get user input and store it in userChoice
    get_input(user_choice);
    
    //process the user choice
    process_choice(list1, user_choice, user_continue);
    
  } while(user_continue == 0);
  
  return 0;
}
