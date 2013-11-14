package automationfx;

import javax.ws.rs.client.WebTarget;

/**
 *
 * @author bruno.borges@oracle.com
 */
public class LedTapeController {

    private final WebTarget ledTarget;

    LedTapeController(WebTarget restClient) {
        ledTarget = restClient.path("/{color}/{value}");
    }

    public void updateColor(String color, int value) {
        ledTarget.resolveTemplate("value", value)
                .resolveTemplate("color", color)
                .request()
                .async()
                .get();
    }
}
