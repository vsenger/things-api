
char* temp_read() {
float tempC = analogRead(2);           //read the value from the sensor
tempC = (5.0 * tempC * 100.0)/1024.0;  //convert the analog data to temperature
int ff = (int) tempC;
Serial.print(tempC);
char c[]={'\t'};//send the data to the computer
return c;
}

char* temp_write(char c[]) {}
