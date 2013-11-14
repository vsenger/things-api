#include <Servo.h> 
#include <PololuQTRSensors.h>
#include <avr/pgmspace.h>

#define NUM_SENSORS   5     // number of sensors used
#define TIMEOUT       2500  // waits for 2500 us for sensor outputs to go low
#define EMITTER_PIN   QTR_NO_EMITTER_PIN     // emitter is controlled by digital pin 2


unsigned int last_proportional = 0;
long integral = 0;

// sensores 1 a 5 estao conectados nas portas digitais 5, 9, 2, 3 e 4, respectivamente
PololuQTRSensorsRC qtrrc((unsigned char[]) {5, 9, 2, 3, 4}, NUM_SENSORS, TIMEOUT, EMITTER_PIN); 
unsigned int sensorValues[NUM_SENSORS];

#define MOTOR1_control_A 7
#define MOTOR1_control_B 8
#define MOTOR1_PWM 10
#define MOTOR2_control_A 12
#define MOTOR2_control_B 13
#define MOTOR2_PWM 11

int modo=1;
boolean calibrado = false;
int bussola=0;
int state=1;
float acelerometro[3];

int contador=0;
//LiquidCrystal lcd(14, 3, 16, 17, 18, 19);

long DistanciaEsquerda;
long DistanciaDireita;
long distancia;
Servo servoV;
Servo servoH;

void setup(){
  delay(500);
  Serial.begin(115200);
  Serial.println("Heelooo");
  delay(20);
  //meetAndroid.setSerial(&Serial);
  //meetAndroid.registerFunction(dedoDuro, 'A');  
  //meetAndroid.registerFunction(encontraNorte, 'B'); 
  //meetAndroid.registerFunction(acelera, 'C'); 
  //meetAndroid.registerFunction(mudarModo, 'M');  

  pinMode(MOTOR1_control_A, OUTPUT);
  pinMode(MOTOR1_control_B, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_control_A, OUTPUT);
  pinMode(MOTOR2_control_B, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);
  modo=9;
}
  int x=0;
void loop(){
  //meetAndroid.receive();  
  //delay(50);
  //if(modo==9) seguirLinha();
  //seguirLinha();

  Serial.println("Heelooo" + x++);
  delay(2000);
  
  
}



