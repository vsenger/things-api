#include <Servo.h> 
Servo servo; 
Servo servo11;  
Servo servo12;  
Servo servo2;  
Servo servo3;  


char* servo_read() {
  return "0";
}

char* servos_write(char* args) {
  servo11.attach(5);  
  servo11.attach(6);  
  int pos = atoi(args);
  servo11.write(pos);
  servo12.write(180 - pos);
  return args;
}   
char* servob_write(char* args) {
  servo2.attach(9);  
  int pos = atoi(args);
  servo2.write(pos);
  return args;
}   
char* servoc_write(char* args) {
  servo3.attach(10);  
  int pos = atoi(args);
  servo3.write(pos);
  return args;
}  

char* servo_write(char* args) {
  servo.attach(5);  
  int pos = atoi(args);
  servo.write(pos);
  return args;
}  

