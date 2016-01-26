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

package de.saxsys.styleablefx.skins;

import de.saxsys.styleablefx.additions.SVGStyleableAddition;
import de.saxsys.styleablefx.controls.SVGGroup;
import de.saxsys.styleablefx.core.BaseUITest;
import javafx.scene.control.Button;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This test is aimed to ensure functionality of the {@link SVGButtonSkin}.
 *
 * @author Xyanid on 23.01.2016.
 */
public class SVGButtonSkinTest extends BaseUITest {


    /**
     * Ensures that the {@link de.saxsys.styleablefx.controls.SVGGroup} in the {@link SVGButtonSkin} is set up.
     */
    @Test
    public void ensureSkinIsSetUp() {

        Button button = new Button();

        SVGButtonSkin skin = new SVGButtonSkin(button);

        assertEquals(1, skin.svgData.getStyleClass().size());

        assertEquals(SVGButtonSkin.SVG_COMPOUND_SELECTOR, skin.svgData.getStyleClass().get(0));

        assertEquals(skin.svgData.getStyleableAddition(SVGStyleableAddition.class), skin.getStyleableAddition(SVGStyleableAddition.class));
    }

    /**
     * Ensures that the {@link de.saxsys.styleablefx.controls.SVGGroup} in the {@link SVGButtonSkin} is set up.
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureExceptionIsThrownWhenAccessingNotSupportedStyleableAddition() {

        Button button = new Button();

        SVGButtonSkin skin = new SVGButtonSkin(button);

        skin.getStyleableAddition(null);
    }

    /**
     * Ensures that the {@link SVGButtonSkin} is styleable via css.
     */
    @Test
    public void ensureSVGDataIsGraphicNodeAndCanNotBeChanged() {

        Button button = new Button();

        SVGButtonSkin skin = new SVGButtonSkin(button);

        button.setSkin(skin);

        assertEquals(button.getGraphic(), skin.svgData);

        button.setGraphic(null);

        assertEquals(button.getGraphic(), skin.svgData);
    }


    /**
     * Ensures that the {@link SVGGroup} is styleable via css using class selectors.
     */
    @Test
    public void ensureIsStyleableViaClassSelectors() {

        Button button = new Button();

        button.setSkin(new SVGButtonSkin(button));

        assertSkinStyleableViaClassSelector(button, "skins", SVGButtonSkinTest::assertIsDefault, SVGButtonSkinTest::assertIsChanged);
    }

    /**
     * Ensures that the {@link SVGGroup} is styleable via css using class selectors.
     */
    @Test
    public void ensureIsStyleableViaIdSelectors() {

        Button button = new Button();

        button.setSkin(new SVGButtonSkin(button));

        assertSkinStyleableViaIdSelector(button, "skins", SVGButtonSkinTest::assertIsDefault, SVGButtonSkinTest::assertIsChanged);
    }

    private static void assertIsDefault(final Button button) {

        SVGButtonSkin skin = (SVGButtonSkin) button.getSkin();

        assertTrue(skin.getStyleableAddition(SVGStyleableAddition.class).get().getWillCacheSvg());

        assertEquals(null, skin.getStyleableAddition(SVGStyleableAddition.class).get().getSvgUrl());
    }

    private static void assertIsChanged(final Button button) {

        SVGButtonSkin skin = (SVGButtonSkin) button.getSkin();

        assertFalse(skin.getStyleableAddition(SVGStyleableAddition.class).get().getWillCacheSvg());

        assertEquals("test.svg", skin.getStyleableAddition(SVGStyleableAddition.class).get().getSvgUrl());
    }
}
