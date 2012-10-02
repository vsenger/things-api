package org.things;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.things.Thing;

/**
 *
 * @author vsenger
 */
public interface Device {
  public String getName();
  public String getResourceString();
  public String getDescription();
  public String getID();
  public Map<String, Thing> getThings();
  public Collection<Thing> getThingsList();
  public void send(String s) throws IOException;
  public String receive() throws IOException;
  public void close() throws IOException;
  public void open() throws IOException;
  public void discovery() throws Exception;
  public boolean connected();
}
