/*
 * Copyright (c) 2009, Oracle
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
package org.netbeans.javafx.design;

/**
 * Represents a type of behaviour for timelines of previous and new state when an actual state is changed in state a DesignState.
 */
public enum DesignStateChangeType {

    /**
     * Use this constant for setting <code>DesignState.stateChangeType</code> field.
     * This type stops previous timeline and plays the new timeline from its start.
     */
    PAUSE_AND_PLAY_FROM_START,

    /**
     * Use this constant for setting <code>DesignState.stateChangeType</code> field.
     * This type finishes previous timeline by setting the time to the totalDuration and plays the new timeline from its start.
     */
    FINISH_AND_PLAY_FROM_START,

    /**
     * Use this constant for setting <code>DesignState.stateChangeType</code> field.
     * This type leaves the previous timeline running and starts playing the new one.
     * This behaviour may lead to some unconsistent state e.g. when a new timeline finishes before the old one.
     */
    CONTINUE_AND_PLAY,

    /**
     * Use this constant for setting <code>DesignState.stateChangeType</code> field.
     * This type does not do anything with the timelines.
     */
    DO_NOTHING

}
