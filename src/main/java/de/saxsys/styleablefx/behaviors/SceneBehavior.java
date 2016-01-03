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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Window;

/**
 * {@inheritDoc}
 * This is a basic behavior to allow for {@link Node}s to be informed about the {@link Window} in which it has been placed.
 *
 * @param <TNode> the type of the node this behavior will be applied to
 *
 * @author Xyanid on 23.10.2015.
 */
public class SceneBehavior<TNode extends Node> extends BehaviorBase<TNode> {

    //region Fields

    /**
     * Determines the scene in which the node has been placed in.
     */
    private final ObjectProperty<Scene> scene = new SimpleObjectProperty<>();

    /**
     * This listener will be called when the scene of the pane was changed.
     */
    private final ChangeListener<Scene> sceneChangeListener = (observable, oldValue, newValue) -> {
        scene.set(newValue);
    };

    /**
     * @return the value of the {@link Scene} of the node.
     */
    public final Scene getScene() {
        return scene.get();
    }

    /**
     * @return the {@link SceneBehavior#scene}.
     */
    public final ObjectProperty<Scene> sceneProperty() {
        return scene;
    }

    //endregion

    //region Implement BehaviorBase

    @Override public void applyBehavior(final TNode node) throws Exception {
        node.sceneProperty().addListener(sceneChangeListener);
    }

    @Override public void removeBehavior(final TNode node) throws Exception {
        node.sceneProperty().removeListener(sceneChangeListener);
    }

    //endregion
}
