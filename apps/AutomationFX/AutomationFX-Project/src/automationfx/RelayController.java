package automationfx;

import java.net.NoRouteToHostException;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author bruno.borges@oracle.com
 */
public class RelayController {

    private final BooleanProperty running = new SimpleBooleanProperty();
    private final BooleanProperty state = new SimpleBooleanProperty();
    private final WebTarget relayTarget;

    public RelayController(WebTarget target, int relay) {
        relayTarget = target.path("/relay" + relay + "/{state}");

        state.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (running.get() == false) {
                    running.set(true);
                    callRestURL(t1.booleanValue());
                }
            }
        });
    }

    private void callRestURL(final boolean bState) {
        Runnable callURLTask = new Runnable() {
            @Override
            public void run() {
                int value = bState ? 1 : 0;
                WebTarget target = relayTarget.resolveTemplate("state", value);
                try {
                    Response res = target.request().get();
                    final int status = res.getStatus();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (status != 200) {
                                state.set(false);
                            }
                            running.set(false);
                        }
                    });
                } catch (Exception e) {
                    if (e instanceof NoRouteToHostException) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                state.set(false);
                                running.set(false);
                            }
                        });
                    }
                }
            }
        };

        Thread t = new Thread(callURLTask);
        t.setDaemon(true);
        t.start();
    }

    public boolean getState() {
        return state.get();
    }

    public void setState(boolean b) {
        this.state.set(b);
    }

    public BooleanProperty stateProperty() {
        return state;
    }

    public BooleanProperty runningProperty() {
        return running;
    }
}
