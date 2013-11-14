void calibrarSensorDeLinha() {
  int i;
  // Auto-calibration: turn right and left while calibrating the sensors.
  for (i = 0; i < 155; i++) { // make the calibration take about 10 seconds
    if (i < 40 || i > 110)
      setSpeeds(60, -60);
    else
      setSpeeds(-60, 60);
    qtrrc.calibrate();       // reads all sensors 10 times at 2500 us per read (i.e. ~25 ms per call)
  }
  setSpeeds(0, 0);
  for (i = 0; i < NUM_SENSORS; i++){
    //Serial.print(qtrrc.calibratedMinimumOn[i]);
    //Serial.print(' ');
  }
  //Serial.println();
  for (i = 0; i < NUM_SENSORS; i++){
    //Serial.print(qtrrc.calibratedMaximumOn[i]);
    //Serial.print(' ');
  }
  //Serial.println();
  //Serial.println();
  calibrado=true;
}

void seguirLinha() {
  if(!calibrado) calibrarSensorDeLinha();
  if(modo!=9) return;
  unsigned int position = qtrrc.readLine(sensorValues);  // Get the position of the line. 
  int proportional = (int)position - 2000;// The "proportional" term should be 0 when we are on the line.
  //Serial.print("proportional: ");
  //Serial.println(proportional);
  int derivative = proportional - last_proportional;  // Compute the derivative (change) of the position.
  //Serial.print("derivative: ");
  //Serial.println(derivative);
  integral += proportional;  // Compute the integral (sum) of the position.
  //Serial.print("integral: ");
  //Serial.println(integral);
  last_proportional = proportional;  // Remember the last position.

  // Compute the difference between the two motor power settings,
  // m1 - m2.  If this is a positive number the robot will turn
  // to the right.  If it is a negative number, the robot will
  // turn to the left, and the magnitude of the number determines
  // the sharpness of the turn.  You can adjust the constants by which
  // the proportional, integral, and derivative terms are multiplied to
  // improve performance.
  int power_difference = proportional/20 + integral/10000 + derivative*3/2;

  // Compute the actual motor settings.  We never set either motor
  // to a negative value.
  const int maximum = 120;
  if (power_difference > maximum)
    power_difference = maximum;
  if (power_difference < -maximum)
    power_difference = -maximum;
  //   Serial.print("power diference: ");
  //   Serial.println(power_difference);
  //setSpeeds(70,70);
  if (power_difference < 0){
    setSpeeds(maximum + power_difference, maximum);
    //   Serial.print("motores: ");
    //   Serial.println(maximum + power_difference);
    //   Serial.print(" : ");
    //   Serial.println(maximum);
  }
  else{
    setSpeeds(maximum, maximum - power_difference);
    //  Serial.print("motores: ");
    //  Serial.print(maximum);
    //  Serial.print(" : ");
    //  Serial.println(maximum - power_difference);
    //  Serial.println(" ");
  }
  //Ler sensor distancia
  //Se tiver objeto na frente, parar
  //Andar um pouco pra traz
  //Programacao do desvio de pista

  //delay(1000);
  Serial.print("position: ");
  
  Serial.println(position);
  Serial.println("sensor values: ");
  for(int x=0;x<NUM_SENSORS;x++) {
    Serial.print(sensorValues[x]);
    Serial.print(" ");
  }
  delay(10);
  //Serial.println("");
  
}

void setSpeeds(int M1 , int M2) {
  if (M1 > 0){
    digitalWrite(MOTOR1_control_A,HIGH);
    digitalWrite(MOTOR1_control_B,LOW);
  }
  else {
    digitalWrite(MOTOR1_control_A,LOW);
    digitalWrite(MOTOR1_control_B,HIGH);
  }
  if (M2 > 0){
    digitalWrite(MOTOR2_control_A,HIGH);
    digitalWrite(MOTOR2_control_B,LOW);
  }
  else {
    digitalWrite(MOTOR2_control_A,LOW);
    digitalWrite(MOTOR2_control_B,HIGH);
  }
  analogWrite(MOTOR1_PWM,abs(M1) );
  analogWrite(MOTOR2_PWM,abs(M2) );
}




