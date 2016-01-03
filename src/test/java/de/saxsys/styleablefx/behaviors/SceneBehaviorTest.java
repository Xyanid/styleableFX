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

import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author rico.hentschel on 05.10.2015.
 */
public final class SceneBehaviorTest {
    /**
     *
     */
    @Test public void create() {
        final JFXPanel fxPanel = new JFXPanel();

        Pane pane = new Pane();

        SceneBehavior behavior = new SceneBehavior();

        try {
            behavior.applyBehavior(pane);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
