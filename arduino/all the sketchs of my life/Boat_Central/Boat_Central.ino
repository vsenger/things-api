#include <LiquidCrystal.h>
#include <MeetAndroid.h>

#define MODE_GPS_LATLONG 0
#define MODE_GPS_SPEED 1
#define MODE_GPS_HEADING 2
#define MODE_GPS_MANE 3
#define MODE_AUTOPILOT_COMPASS 4
#define MODE_AUTOPILOT_ACELEROMETER 5
#define MODE_DEBUG 6
#define MODE_MANUALPILOT 7
#define MOTOR_A 12
#define MOTOR_B 13
#define MOTOR_PWM 11
MeetAndroid meetAndroid;

String modes[] = {
  "Google Earth", "Speedy", "Heading", "Piloto Mane'", "Auto Compass", "Auto Aceler.", "Debug", "Manual Pilot" };

LiquidCrystal lcd(4, 9, 57, 56, 55, 54);
char line_gps[300] = "";

int autopilot=0;
int contaplot=0;
int byteGPS=-1;
char comandoGPR[7] = "$GPRMC";
int indices[13];
int cont=0;
int bien=0;
int conta=0;
char latitude[17];
char longitude[17];
char velocidade[17];
char heading[17];
int cvelocidade=0, clatitude=0, clongitude=0, cheading=0;
volatile long targetHeading;
volatile int mode;

long lastDebounce=0;
long debounceDelay=50;
long lcdDelay=1500;
long lcdNextUpdate=0;
long lcdLastUpdate=0;

String lcdLine1;
String lcdLine2;

void setup()
{
  //LoL
  Serial.begin(9600);
  Serial2.begin(115200);
  Serial1.begin(4800);
  meetAndroid.setSerial(&Serial2);
  attachInterrupt(3, buttonPressed, LOW); //PORTA 20, quando estiver subindo
  attachInterrupt(2, changeMode, LOW); //PORTA 21, quando estiver subindo
  initLCD();
  meetAndroid.registerFunction(autoCompass, 'B');   
  meetAndroid.registerFunction(ghostPilot, 'C');   
}

void setupMotor() {
  pinMode(MOTOR_A, OUTPUT);
  pinMode(MOTOR_B, OUTPUT);
}

void loop()
{
  if(mode>=0 && mode<=3)  savelocation();
  if(mode==MODE_GPS_MANE) pilotoMane();
  if(mode==MODE_AUTOPILOT_ACELEROMETER) android();
  if(mode==MODE_AUTOPILOT_COMPASS) android();
  if(mode==MODE_DEBUG) debug();
  if(mode==MODE_MANUALPILOT) manualPilot(); 
  lcdRestore();
}

long headings[6];
int headCount=0; //ahahahah headCount eh bizarro
void pilotoMane() {
  //headings[headCount++]=atoi(heading);
  //if(headCount==5) headCount=0;
  float h = atof(heading);
  char c1[10];
  itoa(targetHeading,c1,10);
  char c2[10];
  itoa(h,c2,10);
  lcdPrint(c1, c2,0);
  if(targetHeading<h) {
    digitalWrite(MOTOR_A, 1);
    digitalWrite(MOTOR_B, 0);
    analogWrite(MOTOR_PWM, 250);
    delay(700);
    analogWrite(MOTOR_PWM, 0);
    delay(2000);
  }
  else {
    digitalWrite(MOTOR_A, 0);
    digitalWrite(MOTOR_B, 1);
    analogWrite(MOTOR_PWM, 250);
    delay(700);
    analogWrite(MOTOR_PWM, 0);
    delay(2000);
  }
}

long headingsAvg() {
  long resultado = 0;
  for(int x=0;x<6;x++) {
    resultado+=headings[x];
  }
  return resultado/5;
}



void manualPilot() {
  //waiting for confirmation
  if(autopilot==1) return;
  long time = millis() + 7000;
  char c1[10];
  while(time>millis() && mode==MODE_MANUALPILOT) {
    itoa(time-millis(),c1,10);
    lcdPrint("Manual Pilot in",c1 , 0);    
    delay(100);
  }
  if(mode!=MODE_MANUALPILOT) return;
  lcdPrint("Manual Pilot On", "Take care!", 0);      
  autopilot=1;
  detachInterrupt(3); //PORTA 18, quando estiver subindo
  detachInterrupt(2); //PORTA 18, quando estiver subindo
  attachInterrupt(3, motorForward, LOW); //PORTA 20, quando estiver subindo
  attachInterrupt(2, motorBackward, LOW); //PORTA 21, quando estiver subindo

}
void motorForward() {
  digitalWrite(MOTOR_A, 0);
  digitalWrite(MOTOR_B, 1);
  analogWrite(MOTOR_PWM, 250);
  while(!digitalRead(20));
  analogWrite(MOTOR_PWM,0);
}
void motorBackward() {
  digitalWrite(MOTOR_A, 1);
  digitalWrite(MOTOR_B, 0);
  analogWrite(MOTOR_PWM, 250);
  while(!digitalRead(21));
  analogWrite(MOTOR_PWM,0);
}

void changeMode() {
  if(millis()-lastDebounce>debounceDelay) {
    mode = mode== 7 ? 0 : mode + 1;
    lastDebounce=millis();
    resetMode();
    lcdPrint("Mode", modes[mode], 2000);

  }

}

void buttonPressed() {
  char c1[10];
  if(millis()-lastDebounce>debounceDelay) {
    targetHeading = targetHeading==360 ? 1 : targetHeading + 5;
    lastDebounce=millis();
    itoa(targetHeading,c1,10);
    lcdPrint("Target Heading",c1, 500);

  }
}

void android() {
  meetAndroid.receive();  
}


void savelocation ()
{
  while(Serial1.available()>0) {     

    byteGPS=Serial1.read();	   // Read a byte of the serial port
    line_gps[conta]=byteGPS;	  // If there is serial port data, it is put in the buffer
    conta++;
    if (byteGPS==13)
    {		// If the received byte is = to 13, end of transmission
      //Serial.print(line_gps);
      cont=0;
      bien=0;
      for (int i=1;i<7;i++)
      {     // Verifies if the received command starts with $GPR
        if (line_gps[i]==comandoGPR[i-1])
        {
          bien++;
        }
      }
      if(bien==6)
      {		   // If yes, continue and process the data
        for (int i=0;i<300;i++)
        {
          if (line_gps[i]==','){    // check for the position of the  "," separator
            indices[cont]=i;
            cont++;
          }
          if (line_gps[i]=='*')
          {    // ... and the "*"
            indices[12]=i;
            cont++;
          }
        }
        Serial2.println(line_gps);
        Serial.println("");	// ... and write to the serial port
        Serial.println("");
        Serial.println("---------------");
        resetDados();
        for (int i=0;i<12;i++)
        {
          switch(i){
          case 0 :
            Serial.print("Time in UTC (HhMmSs): ");
            break;
          case 1 :
            Serial.print("Status (A=OK,V=KO): ");
            break;
          case 2 :
            Serial.print("Latitude: ");
            break;
          case 3 :
            Serial.print("Direction (N/S): ");
            break;
          case 4 :
            Serial.print("Longitude: ");
            break;
          case 5 :
            Serial.print("Direction (E/W): ");
            break;
          case 6 :
            Serial.print("Velocity in knots: ");
            break;
          case 7 :
            Serial.print("Heading in degrees: ");
            break;
          case 8 :
            Serial.print("Date UTC (DdMmAa): ");
            break;
          case 9 :
            Serial.print("Magnetic degrees: ");
            break;
          case 10 :
            Serial.print("(E/W): ");
            break;
          case 11 :
            Serial.print("Mode: ");
            break;
          case 12 :
            Serial.print("Checksum: ");
            break;
          }
          for (int j=indices[i];j<(indices[i+1]-1);j++)
          {
            Serial.print(line_gps[j+1]);
            if(i==2) {
              latitude[clatitude++]=line_gps[j+1];
            }
            else if(i==3) {
              latitude[clatitude++]=line_gps[j+1];
            }
            else if(i==4) {
              longitude[clongitude++]=line_gps[j+1];
            }
            else if(i==5) {
              longitude[clongitude++]=line_gps[j+1];
            }
            else if(i==6) {
              velocidade[cvelocidade++]=line_gps[j+1];
            }
            else if(i==7) {
              heading[cheading++]=line_gps[j+1];
            }
          }
          Serial.println("");
          if(mode==MODE_GPS_LATLONG) lcdPrint(latitude, longitude,0);
          else if(mode==MODE_GPS_SPEED) lcdPrint("Speed", velocidade, 0);
          else if(mode==MODE_GPS_HEADING) lcdPrint("Heading", heading, 0);
        }
      }
      cvelocidade=0; 
      clatitude=0; 
      clongitude=0;
      cheading=0;
      conta=0;			  // Reset the buffer
      for (int i=0;i<300;i++)
      {
        line_gps[i]=' ';
      }
    }
  }
} 

void resetDados() {
  for( int i=0;i<16;i++) {
    latitude[i]=' ';
    longitude[i]=' ';
    velocidade[i]=' ';
    heading[i]=' ';
  }
}

void initLCD()  {
  lcd.begin(16, 2);
  lcd.clear();
  lcdPrint("Eletron Livre", "Tiziu", 0);
  delay(2000);
}





void lcdPrint(String line1, String line2, long time) {
  if(time==0 && lcdNextUpdate>millis()) return;
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(line1);
  lcd.setCursor(0, 1);
  lcd.print(line2);
  if(time > 0) { //user want to keep last lines...
    lcdNextUpdate = millis() + time;
  }  
  else {    
    lcdLine1=line1;
    lcdLine2=line2;
    lcdNextUpdate = 0;
  }
  lcdLastUpdate=millis();
}


void lcdRestore() {
  if(lcdNextUpdate!=0 && lcdNextUpdate<=millis()){ 
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(lcdLine1);
    lcd.setCursor(0, 1);
    lcd.print(lcdLine2);
    lcdNextUpdate=0;
  }
}  

void debug() {
  lcdPrint("Debug", "Avanca", 0);

  digitalWrite(MOTOR_A, 0);
  digitalWrite(MOTOR_B, 1);
  analogWrite(MOTOR_PWM, 250);
  delay(1500);
  if(mode!=MODE_DEBUG) return;

  lcdPrint("Debug", "Para", 0);  
  analogWrite(MOTOR_PWM, 0);
  delay(1500);
  if(mode!=MODE_DEBUG) return;

  lcdPrint("Debug", "Retro", 0);  
  digitalWrite(MOTOR_A, 1);
  digitalWrite(MOTOR_B, 0);
  analogWrite(MOTOR_PWM, 250);
  delay(1500);
  if(mode!=MODE_DEBUG) return;

  lcdPrint("Debug", "Para", 0);  
  analogWrite(MOTOR_PWM, 0);
  delay(1500);

}

void resetMode() {
  analogWrite(MOTOR_PWM,0);
}


void autoCompass(byte flag, byte numOfValues) {
  if(mode!=MODE_AUTOPILOT_COMPASS) return;
  int bussola =  meetAndroid.getInt();
  char c1[10];
  itoa(bussola,c1,10);
  
  lcdPrint("Compass", c1, 0);


}

float acelerometro[3];
void ghostPilot(byte flag, byte numOfValues) {
  if(mode!=MODE_AUTOPILOT_ACELEROMETER) return;
  meetAndroid.getFloatValues(acelerometro);

  if(acelerometro[0]<-3) {  //para esquerda
    lcdPrint("Debug", "Retro", 0);  
    digitalWrite(MOTOR_A, 1);
    digitalWrite(MOTOR_B, 0);
    analogWrite(MOTOR_PWM, 250);

  }
  else if(acelerometro[0]>3) { //para direita
    lcdPrint("Debug", "Avanca", 0);  
    digitalWrite(MOTOR_A, 0);
    digitalWrite(MOTOR_B, 1);
    analogWrite(MOTOR_PWM, 250);

  }
  else {
    analogWrite(MOTOR_PWM, 0);
  }
  //lcdPrint("" + acelerometro[0], "" + acelerometro[1], 0);

}


/*caso 1
 
 
 heading   target  command
 
 10        0       reward
 0         10      forward
 
 
 */







