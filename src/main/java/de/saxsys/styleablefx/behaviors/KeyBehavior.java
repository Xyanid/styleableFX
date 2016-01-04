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

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;

/**
 * This behavior allows for the {@link Scene} of the {@link Node} to apply certain {@link KeyCodeCombination}s.
 *
 * @param <TNode> the type of the node this behavior will be applied to
 *
 * @author Xyanid on 23.10.2015.
 */
public class KeyBehavior<TNode extends Node> extends SceneBehavior<TNode> {

    //region Constructor

    /**
     * This behavior will manage the key events that will be added to the scene.
     */
    private final SceneEventBehavior<Scene, KeyEvent> sceneEventBehavior = new SceneEventBehavior<>();

    //endregion

    //region Constructor

    /**
     * Creates a new instance of the behavior.
     */
    public KeyBehavior() {
        //TODO attach a listener to the scene so we can apply the scene behavior to is
    }

    //endregion

    // region Override SceneBehavior

    @Override
    public void applyBehavior(final TNode node) throws Exception {
        super.applyBehavior(node);
    }

    @Override
    public void removeBehavior(final TNode node) throws Exception {
        super.removeBehavior(node);
    }

    // endregion
}
