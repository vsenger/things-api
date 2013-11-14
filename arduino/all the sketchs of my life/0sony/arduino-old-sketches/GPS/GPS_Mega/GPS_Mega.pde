#include <LiquidCrystal.h>

#define MODE_GPS_LATLONG 0
#define MODE_GPS_SPEED 1
#define MODE_GPS_HEADING 2
#define MODE_AUTOPILOT_COMPASS 3
#define MODE_AUTOPILOT_ACELEROMETOR 4
#define MODE_DEBUG 5

#define MOTOR_A 12
#define MOTOR_B 13
#define MOTOR_PWM 11

String modes[] = {
  "Lat. Longe.", "Speedy", "Heading", "Auto Compass", "Auto Aceler.", "Debug" };

LiquidCrystal lcd(4, 9, 57, 56, 55, 54);
char linea[300] = "";

int contaplot=0;
int byteGPS=-1;
//char linha[300];
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
volatile int targetHeading;
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
  attachInterrupt(3, buttonPressed, LOW); //PORTA 18, quando estiver subindo
  attachInterrupt(2, changeMode, LOW); //PORTA 18, quando estiver subindo
  initLCD();
  //  resetLinha();
  //resetDados();
}

void setupMotor() {
  pinMode(MOTOR_A, OUTPUT);
  pinMode(MOTOR_B, OUTPUT);
}
  
void loop()
{
  if(mode>=0 && mode<=2)  savelocation();
  if(mode==3 || mode==4)   android();
  if(mode==MODE_DEBUG) debug();
  lcdRestore();
}

void changeMode() {
  if(millis()-lastDebounce>debounceDelay) {
    mode = mode== 5 ? 0 : mode + 1;
    lastDebounce=millis();
    resetMode();
    lcdPrint("Mode", modes[mode], 2000);

  }

}

void buttonPressed() {
  if(millis()-lastDebounce>debounceDelay) {
    targetHeading = targetHeading==360 ? 1 : targetHeading + 1;
    lastDebounce=millis();
    lcdPrint("Target Heading", targetHeading, 2000);

  }
}

void android() {
  
  if(Serial2.available()>0)
  {     
    char l = Serial2.read();
    //    byteGPS=Serial2.read();	   // Read a byte of the serial port

    linea[conta]=byteGPS;	  // If there is serial port data, it is put in the buffer
    conta++;
    if (byteGPS==13)
    {		// If the received byte is = to 13, end of transmission
      Serial.print(linea);
      conta=0;
    }
  }
  /*else {
    lcdPrint("Android BT", "No data!", 0);    
  }*/

}


void savelocation ()
{
  if(Serial1.available()>0) {     

    byteGPS=Serial1.read();	   // Read a byte of the serial port
    linea[conta]=byteGPS;	  // If there is serial port data, it is put in the buffer
    conta++;
    if (byteGPS==13)
    {		// If the received byte is = to 13, end of transmission
      Serial.print(linea);
      cont=0;
      bien=0;
      for (int i=1;i<7;i++)
      {     // Verifies if the received command starts with $GPR
        if (linea[i]==comandoGPR[i-1])
        {
          bien++;
        }
      }
      if(bien==6)
      {		   // If yes, continue and process the data
        for (int i=0;i<300;i++)
        {
          if (linea[i]==','){    // check for the position of the  "," separator
            indices[cont]=i;
            cont++;
          }
          if (linea[i]=='*')
          {    // ... and the "*"
            indices[12]=i;
            cont++;
          }
        }
        Serial2.println(linea);
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
            Serial.print(linea[j+1]);
            if(i==2) {
              latitude[clatitude++]=linea[j+1];
            }
            else if(i==3) {
              latitude[clatitude++]=linea[j+1];
            }
            else if(i==4) {
              longitude[clongitude++]=linea[j+1];
            }
            else if(i==5) {
              longitude[clongitude++]=linea[j+1];
            }
            else if(i==6) {
              velocidade[cvelocidade++]=linea[j+1];
            }
            else if(i==7) {
              heading[cheading++]=linea[j+1];
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
    linea[i]=' ';
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
