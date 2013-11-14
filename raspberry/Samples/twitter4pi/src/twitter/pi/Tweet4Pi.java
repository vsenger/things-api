package twitter.pi;

import com.lcdfx.io.AdafruitLcdPlate;
import com.lcdfx.io.Lcd;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public final class Tweet4Pi {

    static boolean alternate;
    public static String PICTUREFILENAME = "bikepic";
    static Runtime rt = Runtime.getRuntime();
    static int increment;
    static final int BUS_NO = 1;
    static final int BUS_ADDRESS = 0x20;

    public static void mainLCD(String[] args) throws Exception {
        // UNTESTED
        Lcd lcd = new AdafruitLcdPlate(BUS_NO, BUS_ADDRESS);
        lcd.write("Hello World!");
        Thread.sleep(5000);
        lcd.shutdown();
        /*LiquidCrystal_I2C lcd = new LiquidCrystal_I2C(1, 0x20, 16, 2);
         lcd.init();
         lcd.backlight(true);
         lcd.print("Hello World!");
         Thread.sleep(10000);
         lcd.shutdown();*/
    }

    public static void main(String[] args) {
        final GpioPinDigitalOutput myLed[] = new GpioPinDigitalOutput[3];


        try {
            System.out.println("<--Pi4J--> GPIO Trigger Example ... started.");

            // create gpio controller
            final GpioController gpio = GpioFactory.getInstance();

            // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
            final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07,
                    PinPullResistance.PULL_DOWN);

            System.out.println(" ... complete the GPIO #07 circuit and see the triggers take effect.");

            // setup gpio pins #04, #05, #06 as an output pins and make sure they are all LOW at startup
            myLed[0] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "LED #1", PinState.LOW);
            myLed[1] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "LED #2", PinState.LOW);
            myLed[2] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "LED #3", PinState.LOW);
            /*final GpioPinDigitalOutput myLed[] = {
             gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "LED #1", PinState.LOW),
             gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "LED #2", PinState.LOW),
             gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "LED #3", PinState.LOW)
             };*/

            myLed[0].setState(true);
            Thread.sleep(500);
            myLed[0].setState(false);
            Thread.sleep(500);
            myLed[1].setState(true);
            Thread.sleep(500);
            myLed[1].setState(false);
            Thread.sleep(500);
            myLed[2].setState(true);
            Thread.sleep(500);
            myLed[2].setState(false);
            Thread.sleep(500);
            myLed[0].setState(true);

            // create a gpio control trigger on the input pin ; when the input goes HIGH, also set gpio pin #04 to HIGH
            //myButton.addTrigger(new GpioSetStateTrigger(PinState.HIGH, myLed[0], PinState.HIGH));

            // create a gpio control trigger on the input pin ; when the input goes LOW, also set gpio pin #04 to LOW
            //myButton.addTrigger(new GpioSetStateTrigger(PinState.LOW, myLed[0], PinState.LOW));

            // create a gpio synchronization trigger on the input pin; when the input changes, also set gpio pin #05 to same state
            //myButton.addTrigger(new GpioSyncStateTrigger(myLed[1]));

            // create a gpio pulse trigger on the input pin; when the input goes HIGH, also pulse gpio pin #06 to the HIGH state for 1 second
            //myButton.addTrigger(new GpioPulseStateTrigger(PinState.HIGH, myLed[2], 1000));

            // create a gpio callback trigger on gpio pin#4; when #4 changes state, perform a callback
            // invocation on the user defined 'Callable' class instance
            myButton.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
                public Void call() throws Exception {
                    if (!alternate) {
                        try {
                            myLed[1].setState(true);
                            Process pr1 = rt.exec("raspistill -o /home/pi/linux-head/pictures/" + PICTUREFILENAME + increment++ + ".jpg");
                            pr1.waitFor();
                            myLed[1].setState(false);

                        } catch (IOException ex) {
                            myLed[2].setState(true);
                            Thread.sleep(500);
                            myLed[2].setState(false);
                            Thread.sleep(500);
                            myLed[2].setState(true);
                            Thread.sleep(500);
                            myLed[2].setState(false);
                            Thread.sleep(500);
                            ex.printStackTrace();
                        }
                        alternate = true;

                    } else {
                        alternate = false;
                    }
                    System.out.println(
                            " --> GPIO TRIGGER CALLBACK RECEIVED " + increment);

                    return null;
                }
            }));

            // keep program running until user aborts (CTRL-C)
            for (;;) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
        } finally {
            try {
                myLed[0].setState(false);
                Thread.sleep(500);
                myLed[0].setState(true);
                Thread.sleep(500);
                myLed[0].setState(false);
                Thread.sleep(500);
            } catch (Exception e) {
            }

        }
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller        
    }

    public static void mainTwitter(String[] args) {

        String ultimoTweetLido = ultimoTweetLido();
        String ultimoTweet =
                Tweet4Pi.lerUltimoTweet();
        if (ultimoTweet.equals(ultimoTweetLido)) {
            System.out.println("Nenhum Tweet novo...");
            processar(ultimoTweet);

            return;
        } else {
            System.out.println("Chegou novo Tweet!");
            gravarTweet(ultimoTweet);
            System.out.println(ultimoTweet);
        }
        processar(ultimoTweet);
    }

    public static void mudarPorta(int porta, int estado) {
        try {
            Process pr1 = rt.exec("sudo gpio -g mode " + porta + " output");
            Process pr2 = rt.exec("sudo gpio -g write " + porta + " " + estado);






        } catch (IOException ex) {
            Logger.getLogger(Tweet4Pi.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void processar(String mensagem) {
        System.out.println("Processando " + mensagem);
        if (mensagem.contains("GPIO:")) {
            try {
                int porta = Integer.parseInt(mensagem.substring(
                        mensagem.indexOf("GPIO:") + 5, mensagem.indexOf("GPIO:") + 7));
                int estado = Integer.parseInt(mensagem.substring(
                        mensagem.indexOf("GPIO:") + 8, mensagem.indexOf("GPIO:") + 9));
                mudarPorta(porta, estado);
                System.out.println("Vou mudar a porta " + porta + " para o estado " + (estado == 0 ? "desligado" : "ligado"));
            } catch (NumberFormatException e) {
                System.out.println("Erro no formato da mensagem. Deve ser como GPIO:17:1");
            }
        }
    }

    public static void gravarTweet(String t) {
        try {
            BufferedWriter arq = new BufferedWriter(new FileWriter("tweet.txt"));
            arq.write(t);
            arq.close();










        } catch (IOException ex) {
            Logger.getLogger(Tweet4Pi.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String ultimoTweetLido() {
        String r = "";
        try {
            BufferedReader arq = new BufferedReader(new FileReader("tweet.txt"));
            r = arq.readLine();
            arq.close();










        } catch (IOException ex) {
            Logger.getLogger(Tweet4Pi.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return r;
    }

    public static void enviarTweet(String msg) {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            try {
                // get request token.
                // this will throw IllegalStateException if access token is already available
                RequestToken requestToken = twitter.getOAuthRequestToken();
                System.out.println("Got request token.");
                System.out.println("Request token: " + requestToken.getToken());
                System.out.println("Request token secret: " + requestToken.getTokenSecret());
                AccessToken accessToken = null;

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (null == accessToken) {
                    System.out.println("Open the following URL and grant access to your account:");
                    System.out.println(requestToken.getAuthorizationURL());
                    System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                    String pin = br.readLine();
                    try {
                        if (pin.length() > 0) {
                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                        } else {
                            accessToken = twitter.getOAuthAccessToken(requestToken);
                        }
                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode()) {
                            System.out.println("Unable to get the access token.");
                        } else {
                            te.printStackTrace();
                        }
                    }
                }
                System.out.println("Got access token.");
                System.out.println("Access token: " + accessToken.getToken());
                System.out.println("Access token secret: " + accessToken.getTokenSecret());
            } catch (IllegalStateException ie) {
                // access token is already available, or consumer key/secret is not set.
                if (!twitter.getAuthorization().isEnabled()) {
                    System.out.println("OAuth consumer key/secret is not set.");
                }
            }
            Status status = twitter.updateStatus(msg);
            System.out.println("Successfully updated the status to [" + status.getText() + "].");
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to read the system input.");
        }
    }

    public static String lerUltimoTweet() {
        String ultimoTweet = "";
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            User user = twitter.verifyCredentials();
            System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");
            System.out.println("@" + user.getScreenName() + " Data do tweet: " + user.getStatus().getCreatedAt() + " msg: " + user.getStatus().getText());

            ultimoTweet = user.getStatus().getCreatedAt().getTime() + ";" + user.getStatus().getText();

        } catch (TwitterException te) {
            te.printStackTrace();
            //System.out.println("Failed to get timeline: " + te.getMessage());
            ultimoTweet = "Failed to get timeline: " + te.getMessage();
        }
        return ultimoTweet;
    }
}