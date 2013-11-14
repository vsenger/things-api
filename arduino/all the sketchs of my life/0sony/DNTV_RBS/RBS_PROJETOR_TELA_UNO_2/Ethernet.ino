char comando[30];
char comandoOK[30];
int contador = 0;

void ethernet() {
  EthernetClient client = server.available();
  if (client) {
    contador =0;
    // an http request ends with a blank line
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        //Serial.println("aaa");
        comando[contador++]=c;
        if(c=='\n') {
          //Serial.print("COMANDO: ");
          //Serial.println(comando);
        
          
          contador=0;
          for(int x=5;x<30;x++) {
            if(comando[x]==' ') break;
            comandoOK[contador++]=comando[x];
            //Serial.println(x);
          }
          limpa();      
          
          //Serial.print("COMANDO OK: ");
          //Serial.println(comandoOK);
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");
          client.println();
          client.println(homeDevice.execute(comandoOK));
          client.flush();
          break;
        
        }

      }
      
    }
    // give the web browser time to receive the data
    delay(5);
    // close the connection:
    client.stop();
    //Serial.println("client disonnected");

  }
}

void limpa() {
  for(int x=0;x<30;x++) {
    comando[x]=='\0';
    comandoOK[x]=='\0';
  }
}   
