package lcd;

import com.lcdfx.io.AdafruitLcdPlate;
import com.lcdfx.io.Lcd;

public final class LcdAdafruit {

    static final int BUS_NO = 1;
    static final int BUS_ADDRESS = 0x20;

    public static void main(String[] args) throws Exception {
        Lcd lcd = new AdafruitLcdPlate(BUS_NO, BUS_ADDRESS);
        lcd.write("Hello World!");
        Thread.sleep(5000);
        lcd.shutdown();
    }
}