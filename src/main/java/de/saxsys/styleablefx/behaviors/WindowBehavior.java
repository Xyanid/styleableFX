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
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * {@inheritDoc}
 * This is a basic behavior to allow for {@link Node}s to be informed about the {@link Window} in which they have been placed in.
 *
 * @param <TNode> the type of the node this behavior will be applied to
 *
 * @author Xyanid on 23.10.2015.
 */
public class WindowBehavior<TNode extends Node> extends SceneBehavior<TNode> {

    //region Fields

    /**
     * Determines the window of the scene of the node.
     */
    private final ObjectProperty<Window> window = new SimpleObjectProperty<>(null);

    /**
     * This listener will be called when the scene of the pane was changed.
     */
    private final ChangeListener<Scene> sceneChangeListener = (observable, oldValue, newValue) -> {
        if (newValue != null) {
            window.bind(newValue.windowProperty());
        } else {
            window.unbind();
        }
    };

    /**
     * @return the value of the {@link Window} as a {@link Stage}
     */
    public final Stage getStage() {
        return Stage.class.cast(window.getValue());
    }

    /**
     * @return the value of the {@link Window} of the scene
     */
    public final Window getWindow() {
        return window.getValue();
    }

    /**
     * Returns the {@link WindowBehavior#window}.
     *
     * @return the {@link WindowBehavior#window}
     */
    public final ObjectProperty<Window> windowProperty() {
        return window;
    }

    //endregion

    //region Implement BehaviorBase

    @Override public void applyBehavior(final TNode node) throws Exception {
        super.applyBehavior(node);

        sceneProperty().addListener(sceneChangeListener);
    }

    @Override public void removeBehavior(final TNode node) throws Exception {
        super.applyBehavior(node);

        sceneProperty().removeListener(sceneChangeListener);
    }

    //endregion
}
