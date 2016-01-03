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

import eu.lestard.doc.Beta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;

/**
 * This behavior allows for the {@link javafx.scene.Scene} of the {@link Node} to apply certain {@link KeyCodeCombination}s.
 *
 * @param <TScene>     the type of the node this behavior will be applied to.
 * @param <TEventType> the type of events that will be added.
 *
 * @author Xyanid on 23.10.2015.
 */

@Beta public class SceneEventBehavior<TScene extends Scene, TEventType extends Event> extends BehaviorBase<TScene> {

    // region Class

    /**
     * This class combines a {@link KeyEvent} with a {@link EventHandler} so that is can be added and removed from the {@link Node}s {@link Scene}.
     *
     * @param <T> the type of event to add
     */
    public class RegisteredEvent<T extends Event> {

        //region Fields

        /**
         * Determines the key eventType the handler is registered to.
         */
        private EventType<T> eventType;

        /**
         * Determines the eventType handler that is registered.
         */
        private EventHandler<T> handler;

        //endregion

        //region Constructor

        /**
         * Creates a new instance.
         *
         * @param event   eventType type to register.
         * @param handler eventType handler that will be registered.
         */
        public RegisteredEvent(final EventType<T> event, final EventHandler<T> handler) {
            if (event == null) {
                throw new IllegalArgumentException("given eventType must not be null");
            }

            if (handler == null) {
                throw new IllegalArgumentException("given handler must not be null");
            }

            this.eventType = event;
            this.handler = handler;
        }

        //endregion

        //region Getter

        /**
         * @return the {@link RegisteredEvent#eventType}.
         */
        public EventType<T> getEventType() {
            return eventType;
        }

        /**
         * @return the {@link RegisteredEvent#handler}.
         */
        public EventHandler<T> getHandler() {
            return handler;
        }

        //endregion
    }

    // endregion

    // region Fields

    /**
     * Contains the registered key events.
     */
    private final ObservableSet<RegisteredEvent<TEventType>> registeredEvents = FXCollections.observableSet(new HashSet<>());

    // endregion

    //region Constructor

    /**
     * Creates a new instance.
     */
    public SceneEventBehavior() {
        registeredEvents.addListener(this::onRegisteredEventsChanged);
    }

    //endregion

    // region Getter

    /**
     * @return the {@link #registeredEvents}.
     */
    public ObservableSet<RegisteredEvent<TEventType>> registeredEvents() {
        return registeredEvents;
    }

    // endregion

    // region Event Handling

    /**
     * Will be called when a change to the {@link #registeredEvents} has been made, applying the changes to the scene.
     *
     * @param change change das was performed.
     */
    private void onRegisteredEventsChanged(final SetChangeListener.Change<? extends RegisteredEvent<TEventType>> change) {

        //        if (change.wasRemoved()) {
        //            registeredEvents().removeEventFilter(change.getElementAdded().getEventType(), change.getElementAdded().getHandler());
        //        }
        //
        //        if (change.wasAdded()) {
        //            registeredEvents().addEventFilter(change.getElementAdded().getEventType(), change.getElementAdded().getHandler());
        //        }
    }

    // endregion

    // region Implement BehaviorBase

    @Override public void applyBehavior(final TScene scene) throws Exception {
        for (RegisteredEvent<TEventType> event : registeredEvents) {
            scene.addEventFilter(event.getEventType(), event.getHandler());
        }
    }

    /**
     * {@inheritDoc}
     * Removes all events from the scene.
     *
     * @param scene scene which shall be cleared.
     */
    @Override public void removeBehavior(final TScene scene) throws Exception {
        for (RegisteredEvent<TEventType> event : registeredEvents) {
            scene.removeEventFilter(event.getEventType(), event.getHandler());
        }
    }

    // endregion
}
