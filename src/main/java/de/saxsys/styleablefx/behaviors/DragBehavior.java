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

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.EnumSet;

/**
 * This is a basic behavior to allow for {@link Node}s to drag the entire window when they are dragged and dropped.
 *
 * @param <TNode> the type of the node this behavior will be applied to
 *
 * @author Xyanid on 23.10.2015.
 */
public class DragBehavior<TNode extends Node> extends WindowBehavior<TNode> {

    // region Enumerations

    /**
     * Determines when the {@link DragBehavior} is not active for the node it has been placed in.
     */
    public enum DisableMode {
        /**
         * Meaning the behavior is not active if the stage is maximized.
         */
        MAXIMIZED,
        /**
         * Meaning the behavior is not active if the stage is fullscreen.
         */
        FULLSCREEN
    }

    // endregion

    // region Fields

    /**
     * Determines when the behavior is not active, by Default all of the {@link DisableMode} values are used.
     */
    private final EnumSet<DisableMode> disableModes = EnumSet.allOf(DisableMode.class);

    /**
     * The point on which the user has pressed.
     */
    private final Point2D dragDelta = new Point2D.Double();

    /**
     * Will be called when the mouse is being pressed.
     */
    private final EventHandler<MouseEvent> mousePressedHandler = event -> {
        if (getWindow() != null) {
            dragDelta.setLocation(getWindow().getX() - event.getScreenX(), getWindow().getY() - event.getScreenY());
        }
    };

    /**
     * Will be called when the mouse is being draggee.
     */
    private final EventHandler<MouseEvent> mouseDraggedHandler = event -> {
        if (getWindow() != null) {

            if ((disableModes.contains(DisableMode.FULLSCREEN) && getStage().isFullScreen()) || (disableModes.contains(DisableMode.MAXIMIZED) && getStage().isMaximized())) {
                return;
            }

            getWindow().setX(event.getScreenX() + dragDelta.getX());
            getWindow().setY(event.getScreenY() + dragDelta.getY());
        }
    };

    // endregion

    // region Constructor

    /**
     * Creates a new instance of the behavior.
     */
    public DragBehavior() {
    }

    /**
     * Creates a new instance of the behavior.
     *
     * @param modes determines when the behavior is disalbed
     */
    public DragBehavior(final Collection<DisableMode> modes) {
        this();

        this.disableModes.addAll(modes);
    }

    // endregion

    // region Getter/Setter

    /**
     * @return the {@link DragBehavior#disableModes}.
     */
    public EnumSet<DisableMode> getDisableModes() {
        return disableModes;
    }

    // endregion

    // region Implement BehaviorBase

    @Override public void applyBehavior(final TNode node) throws Exception {
        super.applyBehavior(node);

        node.setOnMousePressed(mousePressedHandler);
        node.setOnMouseDragged(mouseDraggedHandler);
    }

    @Override public void removeBehavior(final TNode node) throws Exception {
        super.removeBehavior(node);

        node.setOnMousePressed(null);
        node.setOnMouseDragged(null);
    }

    // endregion
}
