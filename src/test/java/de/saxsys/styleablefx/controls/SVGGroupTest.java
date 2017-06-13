/*
 * Copyright 2015 - 2016 Xyanid
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package de.saxsys.styleablefx.controls;

import de.saxsys.styleablefx.additions.SVGStyleableAddition;
import de.saxsys.styleablefx.core.BaseUITest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Ensures that the {@link SVGGroup} works as expected.
 *
 * @author Xyanid on 23.01.2016.
 */
@SuppressWarnings ("OptionalGetWithoutIsPresent")
public class SVGGroupTest extends BaseUITest {

    /**
     * Ensures that the {@link SVGGroup} is styleable via css using class selectors.
     */
    @Test
    public void ensureIsStyleableViaClassSelectors() {

        SVGGroup group = new SVGGroup();

        assertParentStyleableViaClassSelector(group, "controls", SVGGroupTest::assertIsDefault, SVGGroupTest::assertIsChanged);
    }

    /**
     * Ensures that the {@link SVGGroup} is styleable via css using class selectors.
     */
    @Test
    public void ensureIsStyleableViaIdSelectors() {

        SVGGroup group = new SVGGroup();

        assertParentStyleableViaIdSelector(group, "controls", SVGGroupTest::assertIsDefault, SVGGroupTest::assertIsChanged);
    }

    private static void assertIsDefault(final SVGGroup group) {
        assertTrue(group.getStyleableAddition(SVGStyleableAddition.class).get().getWillCacheSvg());

        assertEquals(null, group.getStyleableAddition(SVGStyleableAddition.class).get().getSvgUrl());
    }

    private static void assertIsChanged(final SVGGroup group) {
        assertFalse(group.getStyleableAddition(SVGStyleableAddition.class).get().getWillCacheSvg());

        assertEquals("test.svg", group.getStyleableAddition(SVGStyleableAddition.class).get().getSvgUrl());
    }

}
