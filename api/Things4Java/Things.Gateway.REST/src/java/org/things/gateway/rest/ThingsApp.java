/*
 * Copyright (c) 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */
package org.things.gateway.rest;

import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class ThingsApp extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    
    final Set<Class<?>> classes = new HashSet<Class<?>>();
    // register root resource
    classes.add(ThingService.class);
    return classes;
  }
}
