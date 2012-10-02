package org.things.me.app;

/*
 * x = velocidade "frente?x re?x gh?x ga?x" gh= girar horário 
 * ga=girar anti-horário 
 * "servo?angulo"
 * "sl" sensor luz 
 * "st" temp
 */
import com.sun.lwuit.Display;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import javax.microedition.midlet.*;

/**
 * @author triangulum
 */
public class ThingsMidlet extends MIDlet {

  private static ThingsMidlet instance;

  public void startApp() {
    if (instance == null) {
      instance = this;
    }
    Display.init(this);
    try {
      Resources r = Resources.open("/devbot.res");
      UIManager.getInstance().setThemeProps(
              r.getTheme(r.getThemeResourceNames()[0]));
    } catch (java.io.IOException e) {
      //sYSTEM? 
      System.out.println("Theme not found");
    }
    ThingsMenu menu = new ThingsMenu(this);
    menu.show();
    /*try {
      Things.bluetooth(RelayForm.deviceURL);
    } catch (SomethingException ex) {
      ex.printStackTrace();
    }*/
  }

  public void pauseApp() {
  }

  public void destroyApp(boolean unconditional) {
    notifyDestroyed();
  }

  public static ThingsMidlet getInstance() {
    return instance;
  }

}