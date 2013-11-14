#include <Servo.h> 
 
Servo servo11;  
Servo servo12;  
Servo servo2;  
Servo servo3;  


char* servo_read() {
  return "0";
}

char* servo_write_head(char* args) {
  servo11.attach(5);  
  int pos = atoi(args);
  servo11.write(pos);
  return args;
}   
char* servo_write_arm(char* args) {
  servo2.attach(11);  
  int pos = atoi(args);
  servo2.write(pos);
  return args;
}   

