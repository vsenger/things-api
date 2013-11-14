package automationfx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author bruno.borges@oracle.com
 */final
public class TemperatureMonitor {

    private static final long FIXED_DELAY = 5000; // 5s
    private final XYChart.Series tempExternaData;
    private final XYChart.Series tempInternaData;
    private final WebTarget targetTempIn;
    private WebTarget targetTempOut;
    private StringProperty temperatureOutProperty = new SimpleStringProperty();
    private StringProperty temperatureInProperty = new SimpleStringProperty();

    public StringProperty temperatureOutProperty() {
        return temperatureOutProperty;
    }

    public StringProperty temperatureInProperty() {
        return temperatureInProperty;
    }

    public TemperatureMonitor(WebTarget webTarget, LineChart chart) {
        tempInternaData = new XYChart.Series();
        tempInternaData.setName("Interna");

        tempExternaData = new XYChart.Series();
        tempExternaData.setName("Externa");

        chart.getData().addAll(tempInternaData, tempExternaData);
        targetTempOut = webTarget.path("/temp_out");
        targetTempIn = webTarget.path("/temp_in");

    }

    // calculate out temperature
    private float toCelsius(int value) {
        return (value * 500) / 1024;
    }

    public void start() {
        Timer timer = new Timer(true);
        timer.schedule(new MonitorService(), 5000, FIXED_DELAY);
    }

    class MonitorService extends TimerTask {

        private static final int MAX_ITEMS = 18;
        private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

        @Override
        public void run() {
            // temperatures
            String sTempIn = targetTempIn.request().get(String.class);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(TemperatureMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            String sTempOut = targetTempOut.request().get(String.class);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(TemperatureMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }

            final float tempIn = toCelsius(new Integer(sTempIn));
            final float tempOut = new Float(sTempOut);

            // category time
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            final String category = sdf.format(date);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (tempInternaData.getData().size() == MAX_ITEMS) {
                        tempInternaData.getData().remove(0);
                        tempExternaData.getData().remove(0);
                    }

                    temperatureInProperty.set(Float.toString(tempIn));
                    temperatureOutProperty.set(Float.toString(tempOut));

                    tempInternaData.getData().add(new XYChart.Data(category, tempIn));
                    tempExternaData.getData().add(new XYChart.Data(category, tempOut));
                }
            });
        }
    }
}
