#include <Servo.h> 
 
Servo arm1a;  
Servo arm1b;  
Servo base;  
Servo arm2;  
Servo topcircle;


char* servo_read() {
  return "0";
}

char* servoarm1_write(char* args) {
  arm1a.attach(5);  
  arm1b.attach(6);  
  int pos = atoi(args);
  arm1a.write(pos);
  arm1b.write(180 - pos);
  return args;
}   
char* servobase_write(char* args) {
  base.attach(9);  
  int pos = atoi(args);
  base.write(pos);
  return args;
}   
char* servoarm2_write(char* args) {
  arm2.attach(10);  
  int pos = atoi(args);
  arm2.write(pos);
  return args;
}  

char* servotopcircle_write(char* args) {
  topcircle.attach(3);  
  int pos = atoi(args);
  topcircle.write(pos);
  return args;
}  

