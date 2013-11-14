char comando[30];
char comandoOK[30];
int contador = 0;

void ethernet() {
  EthernetClient client = server.available();

  if (client) {
    //debug Serial.println("comecando...");
    contador =0;
    limpa();
    // an http request ends with a blank line
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        //debug Serial.print(c);
        //Serial.println("aaa");
        comando[contador++]=c;
        if (c == '\n') {
          //debug Serial.println("\nComando recebido");        
          //if(c=='\n') {
          //debug Serial.print("COMANDO ORIGINAL: ");
          //debug Serial.println(comando);
          contador=0;
          //debug Serial.print("Comando OK antes: ");
          //debug Serial.println(comandoOK);

          limpaOK();
          //debug Serial.print("Comando OK depois do limpa: ");
          //debug Serial.println(comandoOK);

          for(int x=5;x<30;x++) {
            if(comando[x]==' ') break;
            comandoOK[contador++]=comando[x];
            //Serial.println(x);
          }
          //debug Serial.println("Comando OK depois: ");
          //debug Serial.println(comandoOK);
          //limpa();      

          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");
          client.println();
          client.println("<!DOCTYPE HTML>");
          client.println("<html>");
          //debug Serial.println("Vai executar...");
          homeDevice.execute(comandoOK);
          //debug Serial.println("Terminou...");

          client.println("</html>");
          client.flush();        

          break;

        }
        if (c == '\n') {
          // you're starting a new line
          currentLineIsBlank = true;
        } 
        else if (c != '\r') {
          // you've gotten a character on the current line
          currentLineIsBlank = false;
        }

      }      
    }
    //debug Serial.println("Finalizou...");

    // give the web browser time to receive the data
    delay(5);
    // close the connection:
    client.stop();
    //Serial.println("client disonnected");

  }
}

void limpa() {
  for(int x=0;x<30;x++) {
    comando[x]='\0';
    comandoOK[x]='\0';
  }
}   
void limpaOK() {
  //Serial.print("dentro do limpa antes: ");
  //Serial.println(comandoOK);

  for(int x=0;x<30;x++) {
    comandoOK[x]='\0';
  }
  //Serial.print("dentro do limpa depois: ");
  //Serial.println(comandoOK);

}   

7
