/*
 *
 * ******************************************************************************
 *  * Copyright 2015 - 2015 Xyanid
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package de.saxsys.styleablefx.behaviors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.util.HashSet;

/**
 * This interfaces will apply the behavior to the given {@link EventTarget}. The intention is to get certain
 * properties from the {@link EventTarget} and add {@link javafx.beans.value.ChangeListener}. Thus we can react to
 * them e.g. when there is a change of the {@link Node#scene} we can react to it any if needed attach a listener to
 * its {@link Scene#width} so we can adjust the width of the node to that of the scene.
 * The behavior can also track on which {@link EventTarget}s it has been applied and remove itself automatically from them.
 *
 * @param <TEventTarget> the type of the node this behavior will be applied to
 *
 * @author Xyanid on 23.10.2015.
 */
public abstract class BehaviorBase<TEventTarget extends EventTarget> {

    // region Fields

    /**
     * Contains all the event targets this behavior has been applied to.
     */
    private final ObservableSet<TEventTarget> eventTargets = FXCollections.observableSet(new HashSet<>());

    // endregion

    // region Constructor

    /**
     * Creates a new instance and attaches an anonymous event listener to the {@link #eventTargets}.
     */
    public BehaviorBase() {
        eventTargets.addListener(this::onEventTargetsChanged);
    }

    // endregion

    // region Getter

    /**
     * @return the {@link #eventTargets}.
     */
    public ObservableSet<TEventTarget> getEventTargets() {
        return eventTargets;
    }

    // endregion

    // region Public

    /**
     * This method will apply this behavior to the provided {@link EventTarget}.
     *
     * @param eventTarget {@link EventTarget} to which this behavior is to be applied
     */
    public final void applyBehaviorTrackable(final TEventTarget eventTarget) {
        eventTargets.add(eventTarget);
    }

    /**
     * This method will apply this behavior from its {@link #eventTargets}.
     *
     * @throws Exception when an error occurs while removing the behavior
     */
    public final void removeBehavior() throws Exception {
        for (TEventTarget eventTarget : eventTargets) {
            removeBehavior(eventTarget);
        }
    }

    // endregion

    // region Abstract

    /**
     * This method will apply the {@link BehaviorBase} to the provided {@link EventTarget}.
     *
     * @param eventTarget {@link EventTarget} no which the {@link BehaviorBase} is to be applied
     *
     * @throws Exception when an error occurs while applying the behavior
     */
    public abstract void applyBehavior(final TEventTarget eventTarget) throws Exception;

    /**
     * This method will remove the {@link BehaviorBase} from the provided {@link EventTarget}.
     *
     * @param eventTarget {@link EventTarget} from which the {@link BehaviorBase} is to be removed
     *
     * @throws Exception when an error occurs while removing the behavior
     */
    public abstract void removeBehavior(final TEventTarget eventTarget) throws Exception;

    // endregion

    // region Event Handling

    /**
     * Will be called when the {@link #eventTargets} has changed.
     */
    private void onEventTargetsChanged(final SetChangeListener.Change<? extends TEventTarget> change) {

        if (change.wasRemoved()) {
            try {
                removeBehavior(change.getElementRemoved());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (change.wasAdded()) {
            try {
                applyBehavior(change.getElementAdded());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // endregion

    // region Override Object

    @Override public int hashCode() {
        return getClass().hashCode();
    }

    @Override public boolean equals(final Object obj) {
        boolean result = super.equals(obj);

        if (!result) {
            result = obj != null && getClass().equals(obj.getClass());
        }

        return result;
    }

    // endregion
}
