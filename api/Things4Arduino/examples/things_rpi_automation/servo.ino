#include <Servo.h> 
 
Servo servo;  


char* servo_read() {
  return "0";
}

char* servo_write(char* args) {
  servo.attach(5);  
  int pos = atoi(args);
  servo.write(pos);
  return "0";

}   


