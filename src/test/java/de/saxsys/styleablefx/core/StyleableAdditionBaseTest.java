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

package de.saxsys.styleablefx.core;

import de.saxsys.styleablefx.mocks.StyleableAdditionBaseMockA;
import de.saxsys.styleablefx.mocks.StyleableAdditionBaseMockB;
import de.saxsys.styleablefx.mocks.StyleableAdditionBaseMockC;
import de.saxsys.styleablefx.mocks.StyleableAdditionProviderButtonMock;
import de.saxsys.styleablefx.mocks.StyleableAdditionProviderPaneMock;
import javafx.css.Styleable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains test for {@link StyleableAdditionBase}.
 *
 * @author Xyanid on 08.11.2015.
 */
@Ignore(value = "Travis does not support UI test, so run this manually or fine another CI that supports it")
public class StyleableAdditionBaseTest extends BaseUITest {

    /**
     * Ensures that a styleable addition provider is retrieved when {@link StyleableAdditionBase#getStyleableAddition(Styleable, Class)} is called with either a region or a node which are directly
     */
    @Test
    public void ensureStyleableAdditionProviderCanBeRetrieved() {

        assertNotNull(StyleableAdditionBase.getStyleableAddition(new StyleableAdditionProviderPaneMock(), StyleableAdditionBaseMockA.class));

        assertNotNull(StyleableAdditionBase.getStyleableAddition(new StyleableAdditionProviderButtonMock(), StyleableAdditionBaseMockB.class));

        assertNotNull(StyleableAdditionBase.getStyleableAddition(new StyleableAdditionProviderButtonMock(), StyleableAdditionBaseMockC.class));
    }

    /**
     * Ensures that an {@link IllegalArgumentException} is thrown when
     * {@link StyleableAdditionBase#getStyleableAddition(Styleable, Class)} is provided with an {@link Styleable} that does not extend {@link IStyleableAdditionProvider}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureExceptionWillBeThrownIfTheStyleableDoesNotInheritFromIStyleableAdditionProvider() {

        StyleableAdditionBase.getStyleableAddition(new Pane(), StyleableAdditionBaseMockA.class);
    }

    /**
     * Ensures that an {@link IllegalArgumentException} is thrown when{@link StyleableAdditionBase#getStyleableAddition(Styleable, Class)} is provided with an {@link javafx.scene.control.Control} that
     * does not
     * extend have a {@link javafx.scene.control.Skin} which {@link IStyleableAdditionProvider}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureExceptionWillBeThrownIfTheStyleableIsAControlAndDoesNotHaveASkinWhichInheritsFromIStyleableAdditionProvider() {

        StyleableAdditionBase.getStyleableAddition(new Button(), StyleableAdditionBaseMockA.class);
    }
}
