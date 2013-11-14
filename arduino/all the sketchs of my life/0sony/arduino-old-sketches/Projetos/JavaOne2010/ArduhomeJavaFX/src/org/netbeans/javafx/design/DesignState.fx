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

import javafx.animation.Timeline;
import org.netbeans.javafx.design.DesignStateChangeType;

/**
 * Represents a container of a single state variable in a design.
 * State variable can hold at maximum one particular state at a time.
 * The actual state is held in <code>actual</code> field.
 *
 * It is required to set <code>timelines</code> field.
 * Then you can switch between states using <code>actual</code> field directly or any helper method e.g. <code>previous</code> or <code>next</code>.
 * Optionally you can set <code>names</code> field to navigate to states using their names.
 *
 * The <code>onActualStateChanged</code> method provides a callback for <code>actual</code> field changes.
 * The <code>onTransitionFinished</code> method provides a callback for notifying of a finished transition animations.
 * The <code>doNotAnimateOnce</code> field allows to fast-forward a transition animation of next state change.
 */
public class DesignState {

    /**
     * Contains a sequence of names.
     * Each name is assigned to a particular state represented by an index starting from zero.
     * For getting an index from a specified name, you can use <code>findIndex</code> method.
     */
    public-init var names: String[];

    /**
     * Contains a sequence of timelines.
     * Each timeline is assigned to a particular state represented by an index starting from zero.
     * A timeline sets various properties of components in a design to specific value.
     * At the end of the timeline, the design should look as designed for a specific state.
     */
    public-init var timelines: Timeline[] on replace {
        initiateTransitionStates ();
    }


    var transitionStates: TransitionState[];

    function initiateTransitionStates (): Void {
        if (not FX.isInitialized(transitionStates)) {
            transitionStates = for (i in [0..<sizeof (timelines)]) TransitionState { designState: this, timeline: timelines[i], index: i };
        }
    }

    /**
     * Defines how the timelines should be stop/started when the actual state is changed.
     * Default value is <code>PAUSE_AND_PLAY_FROM_START</code>.
     * If null value is set, <code>IllegalArgumentException</code> is thrown.
     */
    public-init var stateChangeType = DesignStateChangeType.PAUSE_AND_PLAY_FROM_START on replace oldValue {
        if (stateChangeType == null) {
            stateChangeType = oldValue;
            throw new java.lang.IllegalArgumentException ();
        }
    }

    /**
     * This is a notification method called when the actual state is changed.
     * @param oldState the previous state
     * @param newState the new actual state
     */
    public var onActualStateChanged: function (oldState: Integer, newState: Integer): Void;

    /**
     * This method is called when a timeline for a specific state is interrupted or finished.
     * This method can be used e.g. for switching to next states for a slide-show effect.
     * If the finishedState parameter value equals to DesignState.actual value
     * then the transition is finished normally by finishing playing the whole animation.
     * If the finishedState parameter value does NOT equal to DesignState.actual value
     * then the transition is interrupted by invoked switch to another state i.e.
     * by setting DesignState.actual field value or using the control methods.
     * @param finishedState the state which transition is finished; If equals to DesignState.actual then transition has finished normally. Otherwise it has been interrupted.
     */
    public var onTransitionFinished: function (finishedState: Integer): Void;

    /**
     * Defines whether the next animation is played normally or fast-forward to the end immediately.
     * If initially set to true, then a design immediately skips an animation for the start state.
     * Similarly if you want to fast-switch to a new state, then set this field to true right before setting the new actual state index.
     * Once an animation is skipped, the field is set to false automatically.
     * The field must not be initialized with bind expression.
     */
    public var doNotAnimateOnce: Boolean = true;

    /**
     * Holds the actual state index. The defined indices are [0 .. < sizeof names].
     * Any other index is taken as undefined.
     * Default actual state index is <code>-1</code>.
     * When a new actual state index is set, then based on <code>onActualStateChanged</code>
     * a new timeline is started and <code>onActualStateChanged</code> method is called.
     */
    public var actual = -1 on replace old {
        initiateTransitionStates ();
        def oldTimeline = timelines[old];
        def actualTimeline = timelines[actual];

        if (stateChangeType == DesignStateChangeType.PAUSE_AND_PLAY_FROM_START) {
            oldTimeline.stop ();
            actualTimeline.evaluateKeyValues ();
            actualTimeline.playFromStart ();

        } else if (stateChangeType == DesignStateChangeType.FINISH_AND_PLAY_FROM_START) {
            oldTimeline.time = oldTimeline.totalDuration;
            actualTimeline.evaluateKeyValues ();
            actualTimeline.playFromStart ();

        } else if (stateChangeType == DesignStateChangeType.CONTINUE_AND_PLAY) {
            actualTimeline.evaluateKeyValues ();
            actualTimeline.play ();

        } else if (stateChangeType == DesignStateChangeType.DO_NOTHING) {
        }

        if (doNotAnimateOnce) {
            doNotAnimateOnce = false;
            FX.deferAction (function (): Void {
                actualTimeline.time = actualTimeline.totalDuration;
            });
        }

        onActualStateChanged (old, actual);
    }

    /**
     * Returns a state index by a state name which can be found in <code>names</code> field.
     * @param name the state name to search for
     * @return the state index or <code>-1</code> if not found.
     */
    public function findIndex (name: String): Integer {
        javafx.util.Sequences.indexOf(names, name);
    }

    /**
     * Returns whether the actual state index is 0 or less.
     * @return true, if actual state index <= 0
     */
    public bound function isFirst (): Boolean {
        actual <= 0
    }

    /**
     * Returns whether the actual state index is equal or greater than the last defined index.
     * @return true, if actual state index >= sizeof names - 1
     */
    public bound function isLast (): Boolean {
        actual >= sizeof names - 1
    }

    /**
     * If possible, sets the actual state index to previous state index
     * i.e. decrements the actual state index by 1.
     * The method cares about the lower limit and disables decrement
     * when the new actual state index would be undefined index.
     */
    public function previous () {
        if (not isFirst ()) {
            actual = actual - 1;
        }
    }

    /**
     * If possible, sets the actual state index to next state index
     * i.e. increments the actual state index by 1.
     * The method cares about the upper limit and disables increment
     * when the new actual state index would be undefined index.
     */
    public function next () {
        if (not isLast ()) {
            actual = actual + 1;
        }
    }

    /**
     * If possible, sets the actual state index to previous state index in a loop
     * i.e. decrements the actual state index by 1 in a loop of defined indices.
     * If the new actual state index would be undefined index, the last defined index is set instead.
     */
    public function previousWrapped () {
        actual = if (isFirst ()) then sizeof names - 1 else actual - 1
    }

    /**
     * If possible, sets the actual state index to next state index in a loop
     * i.e. increments the actual state index by 1 in a loop of defined indices.
     * If the new actual state index would be undefined index, the first defined index is set instead.
     */
    public function nextWrapped () {
        actual = if (isLast ()) then 0 else actual + 1
    }

}

class TransitionState {

    public-init var designState: DesignState;

    public-init var timeline: Timeline;

    public-init var index: Integer;

    var running = bind timeline.running on replace oldRunning {
        if (oldRunning  and  not running) {
            designState.onTransitionFinished (index);
        }
    }

}
