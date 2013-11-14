package gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public final class HelloGPIO {
    public static void main(String[] args) throws Exception {
        GpioPinDigitalOutput myLed[] = new GpioPinDigitalOutput[3];
        GpioController gpio = GpioFactory.getInstance();
        myLed[0] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
        myLed[0].setState(true);
        Thread.sleep(500);
        myLed[0].setState(false);
        Thread.sleep(500);
        myLed[0].setState(true);
        Thread.sleep(500);
        myLed[0].setState(false);
        Thread.sleep(500);
        myLed[0].setState(true);
        Thread.sleep(500);
        myLed[0].setState(false);
        Thread.sleep(500);
        myLed[0].setState(true);
        Thread.sleep(500);
        myLed[0].setState(false);
        Thread.sleep(500);
    }

}