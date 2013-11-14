package automationfx;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

/**
 * @author bruno.borges@oracle.com
 */
public class ThingsClientREST {

    private Client client = ClientBuilder.newClient();
    private final URI thingsServerURI;

    public ThingsClientREST() {
        String uriTemplate = "http://{host}:{port}/things";
        String host = System.getProperty("things.host", "192.168.1.11");
        String port = System.getProperty("things.port", "8080");

        Map<String, Object> params = new HashMap<>();
        params.put("host", host);
        params.put("port", port);

        UriBuilder uriBuilder = UriBuilder.fromUri(uriTemplate);
        this.thingsServerURI = uriBuilder.buildFromMap(params);
    }

    public WebTarget createTarget() {
        return client.target(thingsServerURI);
    }
}
