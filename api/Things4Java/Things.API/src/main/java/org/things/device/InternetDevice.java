package org.things.device;

import org.things.Device;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.things.Thing;

/**
 *
 * @author vsenger
 */
public class InternetDevice implements Device {

  final static int DISCOVERY_RETRY = 3;
  String resources;
  String name;
  String description;
  boolean connected;
  Map<String, Thing> components;
  Collection<Thing> componentsList;
  String lastValue;
  String ip;
  public InternetDevice(String ip) {
    this.ip=ip;
  }
  @Override
  public boolean connected() {
    return connected;
  }

  @Override
  public void close() throws IOException {
  }

  public void open() throws IOException {
  }

  @Override
  public void discovery() throws Exception {
    //Those times are totally dependent with the kind of communication...

    for (int x = 0; x < DISCOVERY_RETRY; x++) {
      System.out.println("try no. " + x);
      resources = new String(readFromURL("http://" + getID() + "/discovery"));
      System.out.println("Server " + "http://" + getID() + "/discovery");
      if (resources != null) {
        Logger.getLogger(InternetDevice.class.getName()).log(Level.INFO,
                "jHome Compatible device found! Resource String: {0}", resources);
        components = new Hashtable<String, Thing>();
        componentsList = new ArrayList<Thing>();
        connected = true;
        System.out.println("Resources " + resources);
        try {
          StringTokenizer tokenizer = new StringTokenizer(resources, "|");
          this.name = tokenizer.nextToken();
          int numberOfComponents = Integer.parseInt(tokenizer.nextToken());
          for (int y = 0; y < numberOfComponents; y++) {
            String name = tokenizer.nextToken();
            String type = tokenizer.nextToken();
            String port = tokenizer.nextToken();
            String value = tokenizer.nextToken();
            Thing component = new Thing(this,name,name, port, type, value);
            this.components.put(name, component);
            this.componentsList.add(component);
            //this.components.add(new Co);
          }
          break;
        } catch (Exception e) {
          Logger.getLogger(InternetDevice.class.getName()).log(Level.INFO,
                  "Wrong resource String. Parse error!", e);
        }
      } else {
        Logger.getLogger(InternetDevice.class.getName()).log(Level.INFO,
                "Empty Resource String - Nor a jHome device", resources);
      }
    }
  }

  public static byte[] readFromURL(String url) {
    byte[] responseBody = null;
    try {
      HttpClient client = new HttpClient();
      client.setConnectionTimeout(200);
      HttpMethod method = new GetMethod(url);
     
      client.executeMethod(method);
      responseBody = method.getResponseBody();
    } catch (Exception e) {
      //let's 
      e.printStackTrace();
    }
    return responseBody;
  }

  @Override
  public void send(String s) throws IOException {
    byte b[] =readFromURL("http://" + this.ip + "/" + s);
    if(b!=null && b.length>0) lastValue = new String(b);
  }

  @Override
  public String receive() throws IOException {
    return lastValue;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getResourceString() {
    return resources;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public Map<String, Thing> getThings() {
    return this.components;
  }
  @Override
  public Collection<Thing> getThingsList() {
    return componentsList;
  }

  @Override
  public String getID() {
    return ip;
  }
}
