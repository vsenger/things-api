package automationfx;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ReadOnlyFloatProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author bruno.borges@oracle.com
 */
public class HumidityMonitor {

    private static final long DELAY = 15000; // 15s
    private FloatProperty floatValue = new SimpleFloatProperty();
    private StringProperty value = new SimpleStringProperty();
    private final WebTarget target;

    public HumidityMonitor(WebTarget target) {
        this.target = target.path("/humidity");
    }

    public void start() {
        Timer timer = new Timer(true);
        timer.schedule(new MonitorService(), 5000, 5000);
    }

    class MonitorService extends TimerTask {

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(HumidityMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            String sHumidity = target.request().get(String.class);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(HumidityMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            sHumidity = target.request().get(String.class);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(HumidityMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            sHumidity = target.request().get(String.class);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(HumidityMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            final float humidity = new Float(sHumidity);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    floatValue.set(humidity);
                    value.set(Float.toString(humidity));
                }
            });
        }
    }

    public ReadOnlyStringProperty valueProperty() {
        return value;
    }

    public ReadOnlyFloatProperty floatProperty() {
        return floatValue;
    }
}
