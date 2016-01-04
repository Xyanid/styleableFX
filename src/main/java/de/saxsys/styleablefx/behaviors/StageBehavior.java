/*
 *
 * ******************************************************************************
 *  * Copyright 2015 - 2016 Xyanid
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

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * {@inheritDoc}
 * This is a basic behavior to allow for {@link Node}s to handle the {@link javafx.stage.Window} it resides it.
 *
 * @param <TNode> the type of the node this behavior will be applied to
 *
 * @author Xyanid on 23.10.2015.
 */
public abstract class StageBehavior<TNode extends Node> extends WindowBehavior<TNode> {

    //region Fields

    private final String methodName;

    /**
     * Will be called when the desired event takes place.
     */
    private final EventHandler<Event> eventHandler = event -> {
        if (getWindow() != null) {

            handleStage(getStage());
        }
    };

    //endregion

    //region Constructor

    /**
     * Creates a new instance of the behavior using the given methodname.
     *
     * @param methodName value of the method to be called
     */
    public StageBehavior(final String methodName) {

        if (methodName == null) {
            throw new IllegalArgumentException("given method value must not be null");
        }

        this.methodName = methodName;
    }

    //endregion

    //region Implement BehaviorBase

    /**
     * This method will be called when the stage should be handled, which happens when the eventhandler of the provides method is called.
     *
     * @param stage the stage in which the node this behavior is attached to is resided
     */
    protected abstract void handleStage(Stage stage);

    @Override public void applyBehavior(final TNode node) throws Exception {
        super.applyBehavior(node);

        node.getClass().getMethod(methodName, EventHandler.class).invoke(node, eventHandler);
    }


    //endregion

    //region Abstract

    @Override public void removeBehavior(final TNode node) throws Exception {
        super.removeBehavior(node);

        node.getClass().getMethod(methodName, EventHandler.class).invoke(node, new Object[]{null});
    }

    //endregion
}
