/*
 * Copyright (c) 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */
package org.things.gateway.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Esta classe representa a implementação do seu hardware! 
 */
@XmlRootElement
public class ThingBean {

    public String text;

    public ThingBean() {
    }

    public ThingBean(String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return "[" + text + "]";
    }
}
