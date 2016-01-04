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
import javafx.stage.Stage;

/**
 * This is a basic behavior to allow for {@link Node}s to minimize the window it resides it.
 *
 * @param <TNode> the type of the node this behavior will be applied to
 *
 * @author Xyanid on 23.10.2015.
 */
public class MinimizeBehavior<TNode extends Node> extends StageBehavior<TNode> {

    //region Constructor

    /**
     * Creates a new instance of the behavior using the given methodname.
     *
     * @param methodName value of the method to be called
     */
    public MinimizeBehavior(final String methodName) {
        super(methodName);
    }

    //endregion

    //region Override StageBehvaior

    @Override protected void handleStage(final Stage stage) {
        stage.setIconified(true);
    }

    //endregion
}
