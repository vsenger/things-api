#define MUITO_PERTO_VAL 350
#define PERTO_VAL 300
#define APROXIMANDO_VAL 200
#define MUITO_LONGE_VAL 100

void setup() {
  Serial.begin(9600);
}

void loop() {
  int val = analogRead(0);
  Serial.print("Valor analogico: ");
  Serial.println(val);
    
  float volts = (5.0/1024.0) * val;
  //Serial.print("Valor Volts na mo: ");  
  Serial.println(volts);  
  if(volts>0 && volts<0.6) Serial.println("25cm");
  if(volts>0.6 && volts<1.0) Serial.println("20cm");
  if(volts>1.0 && volts<1.5) Serial.println("15cm");
  if(volts>1.5 && volts<2.0) Serial.println("10cm");
  if(volts>2.0) Serial.println("5cm");
  
  delay(500);
}


