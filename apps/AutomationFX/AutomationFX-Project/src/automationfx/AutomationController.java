package automationfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author bruno.borges@oracle.com
 */
public class AutomationController implements Initializable {
    
    @FXML
    private ImageView imgHumidity;
    @FXML
    private ToggleButton toggleButton1;
    @FXML
    private ToggleButton toggleButton2;
    @FXML
    private ToggleButton toggleButton3;
    @FXML
    private ToggleButton toggleButton4;
    @FXML
    private ToggleButton toggleButton5;
    @FXML
    private ToggleButton toggleButton6;
    @FXML
    private LineChart temperaturaChart;
    @FXML
    private Slider servoMotorSlider;
    @FXML
    private Button botaoGirarServoMotor;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label labelTempInterna;
    @FXML
    private Label labelTempExterna;
    @FXML
    private Button btnRed;
    @FXML
    private Button btnGreen;
    @FXML
    private Button btnBlue;
    @FXML
    private TextField textColorValue;
    private RelayController rele1;
    private RelayController rele2;
    private RelayController rele3;
    private RelayController rele4;
    private RelayController rele5;
    private RelayController rele6;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ThingsClientREST thingsClient = new ThingsClientREST();

        // TEMPERATURA MONITOR
        TemperatureMonitor tempMonitor = new TemperatureMonitor(thingsClient.createTarget(), temperaturaChart);
        labelTempExterna.textProperty().bind(tempMonitor.temperatureOutProperty());
        labelTempInterna.textProperty().bind(tempMonitor.temperatureInProperty());
        tempMonitor.start();

        // HUMIDITY MONITOR
        HumidityMonitor humidityMonitor = new HumidityMonitor(thingsClient.createTarget());
        humidityLabel.textProperty().bind(humidityMonitor.valueProperty());
        humidityMonitor.start();

        // SERVO MOTOR CONTROLLER
        final ServoMotorController servoMotorController = new ServoMotorController(thingsClient.createTarget());
        servoMotorController.valueProperty().bind(servoMotorSlider.valueProperty());
        botaoGirarServoMotor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                servoMotorController.girar();
            }
        });

        // RELAYS
        WebTarget relayInitTarget = thingsClient.createTarget();
        rele1 = new RelayController(relayInitTarget, 1);
        rele2 = new RelayController(relayInitTarget, 2);
        rele3 = new RelayController(relayInitTarget, 3);
        rele4 = new RelayController(relayInitTarget, 4);
        rele5 = new RelayController(relayInitTarget, 5);
        rele6 = new RelayController(relayInitTarget, 6);

        // State-ish Bindings
        rele1.stateProperty().bindBidirectional(toggleButton1.selectedProperty());
        rele2.stateProperty().bindBidirectional(toggleButton2.selectedProperty());
        rele3.stateProperty().bindBidirectional(toggleButton3.selectedProperty());
        rele4.stateProperty().bindBidirectional(toggleButton4.selectedProperty());
        rele5.stateProperty().bindBidirectional(toggleButton5.selectedProperty());
        rele6.stateProperty().bindBidirectional(toggleButton6.selectedProperty());

        // Disable-ish Bindings
        toggleButton1.disableProperty().bind(rele1.runningProperty());
        toggleButton2.disableProperty().bind(rele2.runningProperty());
        toggleButton3.disableProperty().bind(rele3.runningProperty());
        toggleButton4.disableProperty().bind(rele4.runningProperty());
        toggleButton5.disableProperty().bind(rele5.runningProperty());
        toggleButton6.disableProperty().bind(rele6.runningProperty());

        // LED COLORS

        final LedTapeController ledTapeController = new LedTapeController(thingsClient.createTarget());
        
        btnRed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                int color = getTextColorValue();
                if (color > -1) {
                    ledTapeController.updateColor("red", color);
                }
                
            }
        });
        
        
        btnGreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                int color = getTextColorValue();
                if (color > -1) {
                    ledTapeController.updateColor("green", color);
                }
            }
        });
        
        btnBlue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                int color = getTextColorValue();
                if (color > -1) {
                    ledTapeController.updateColor("blue", color);
                }
            }
        });
        
        imgHumidity.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                System.exit(0);
            }
        });
    }
    
    private int getTextColorValue() {
        String val = textColorValue.getText();
        int integer = -1;
        try {
            integer = Integer.parseInt(val);
            if (integer < 0 || integer > 255) {
                integer = -1;
            }
        } catch (Exception e) {
        }
        if (integer == -1) {
            textColorValue.textProperty().set("");
        }
        return integer;
    }
}
