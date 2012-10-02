/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.globalcode.jhome.web;

import org.things.web.AdminController;
import org.things.Device;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.things.Thing;
import org.things.Things;

/**
 *
 * @author vsenger
 */
public class AdminControllerTest {

  AdminController adminController;
  Things deviceManager;
  Thing component;
  //Component component;

  public AdminControllerTest() {
  }

  @Before
  public void setUp() {
    /*
     * deviceManager = createMock(DeviceManager.class); component =
     * createMock(Component.class); adminController=new AdminController();
     *
     *
     * adminController.setDeviceManager(deviceManager);
     * adminController.setComponentToEdit(component);
     */
    adminController = new AdminController();
    deviceManager = createMock(Things.class);
    adminController.setDeviceManager(deviceManager);

  }

  @After
  public void tearDown() {
  }

  /**
   * Test of updateComponent method, of class AdminController.
   */
  /**
   * Test of discoverySerial method, of class AdminController.
   */
  @Test
  public void testDiscoverySerialComDevices() {
    Collection<Device> devices = new ArrayList<Device>();
    devices.add(createMock(Device.class));
    /*try {
      expect(deviceManager.discoverySerial("/dev/ttyUSB0")).andReturn(devices);
    } catch (Exception ex) {
      Logger.getLogger(AdminControllerTest.class.getName()).log(Level.SEVERE, null, ex);
      fail(ex.getMessage());
    }*/
    replay(deviceManager);
    String result = adminController.discoverySerial();
    assertNotNull(adminController.getLastDeviceFound());
    assertEquals("setup", result);
    verify(deviceManager);
  }

  @Test
  public void testDiscoverySerialSemDevices() {
    /*try {
      expect(deviceManager.discoverySerial(null)).andReturn(new ArrayList<Device>());
    } catch (Exception ex) {
      Logger.getLogger(AdminControllerTest.class.getName()).log(Level.SEVERE, null, ex);
      fail(ex.getMessage());
    }
    replay(deviceManager);
    String result = adminController.discoverySerial();
    assertNull(adminController.getLastDeviceFound());
    assertEquals("discovery", result);
    verify(deviceManager);*/
  }

  @Test
  public void testDiscoveryNetworkComDevices() {
    Collection<Device> devices = new ArrayList<Device>();
    //devices.add(createMock(Device.class));
    /*try {
      expect(deviceManager.discoveryNetwork(anyObject(String.class))).andReturn(devices);
      replay(deviceManager);
      String result = adminController.discoveryNetwork();
      assertNotNull(adminController.getLastDeviceFound());
      assertEquals("setup", result);
      verify(deviceManager);
    } catch (Exception ex) {
      Logger.getLogger(AdminControllerTest.class.getName()).log(Level.SEVERE, null, ex);
      fail(ex.getMessage());
    }*/
  }

  @Test
  public void testDiscoveryNetworkSemDevices() {
    /*try {
      expect(deviceManager.discoveryNetwork(anyObject(String.class))).andReturn(new ArrayList<Device>());
      replay(deviceManager);
      String result = adminController.discoveryNetwork();
      assertNull(adminController.getLastDeviceFound());
      assertEquals("discovery", result);
      verify(deviceManager);
    } catch (Exception ex) {
      Logger.getLogger(AdminControllerTest.class.getName()).log(Level.SEVERE, null, ex);
      fail(ex.getMessage());
    }*/
  }

  /**
   * Test of discoveryNetwork method, of class AdminController.
   */
  @Test
  public void testDiscoveryNetwork() {
  }

  /**
   * Test of editComponent method, of class AdminController.
   */
  @Test
  public void testUpdateComponent() throws Exception {
    /*component = createMock(Thing.class);

    expect(component.execute()).andReturn("10");
    component.setLastValue("10");
    replay(component);

    adminController.setComponentToEdit(component);
    adminController.updateComponent();

    assertNotNull(adminController.getComponentToEdit());*/
  }

  @Test
  public void testEditComponent() throws Exception {
    Device device = createMock(Device.class);
    ArrayList<Thing> comps = new ArrayList<Thing>();
    comps.add(createMock(Thing.class));
    expect(device.getThingsList()).andReturn(comps);
    replay(device);
    adminController.setLastDeviceFound(device);
    adminController.editComponent(0);
    assertNotNull(adminController.getComponentToEdit());
  }
}
