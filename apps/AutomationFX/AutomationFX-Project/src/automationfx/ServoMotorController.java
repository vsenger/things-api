package automationfx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author bruno.borges@oracle.com
 */
public class ServoMotorController {

    private WebTarget target;
    private IntegerProperty value = new SimpleIntegerProperty();

    public ServoMotorController(WebTarget webTarget) {
        this.target = webTarget.path("/servo/{value}");
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public void girar() {
        target.resolveTemplate("value", value.intValue()).request().async().get();
    }
}
