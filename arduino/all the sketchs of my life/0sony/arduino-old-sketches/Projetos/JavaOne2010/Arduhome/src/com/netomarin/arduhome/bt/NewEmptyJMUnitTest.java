/*
 * NewEmptyJMUnitTest.java
 * JMUnit based test
 *
 * Created on 12/09/2012, 14:37:41
 */
package com.netomarin.arduhome.bt;

import jmunit.framework.cldc10.*;

/**
 * @author vsenger
 */
public class NewEmptyJMUnitTest extends TestCase {
  
  public NewEmptyJMUnitTest() {
    //The first parameter of inherited constructor is the number of test cases
    super(0, "NewEmptyJMUnitTest");
  }  
  
  public void test(int testNumber) throws Throwable {
  }  
}
