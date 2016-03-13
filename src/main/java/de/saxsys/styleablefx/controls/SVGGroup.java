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

package de.saxsys.styleablefx.controls;

import de.saxsys.styleablefx.additions.SVGStyleableAddition;
import de.saxsys.styleablefx.core.IStyleableAdditionProvider;
import de.saxsys.styleablefx.core.StyleableAdditionBase;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This group contains svg data provided by an SVG Parser. It allows for the svg data to be switched dynamically via its styleable contentMap.
 *
 * @author Xyanid on 09.11.2015.
 */
public class SVGGroup extends Group implements IStyleableAdditionProvider {

    // region Static

    public static final String DEFAULT_STYLE_CLASS = "svg-group";

    // endregion

    // region Classes

    /**
     * contains all styleable CssMetaData needed.
     */
    private static class StyleableProperties {
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Group.getClassCssMetaData());

            styleables.addAll(SVGStyleableAddition.getClassCssMetaData());

            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    // endregion

    // region Fields

    /**
     * The skin addition will provide the styleable contentMap as well as handling loading of svg files.
     */
    private SVGStyleableAddition styleAddition = new SVGStyleableAddition();

    // endregion

    // region Constructor

    /**
     * Creates a new instance.
     */
    public SVGGroup() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        getChildren().addAll(styleAddition.getSvgGroup());
    }

    // endregion

    // region Getter

    /**
     * @return the {@link #styleAddition}.
     */
    public SVGStyleableAddition getSVGStyleableAddition() {
        return styleAddition;
    }

    // endregion

    // region Static Methods

    /**
     * @return The CssMetaData associated with this class, which may include the CssMetaData of its super classes.
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    // endregion

    // region Override SVGGroup

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    // endregion

    // region Implement IStyleableAdditionProvider

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the given {@link Class} is null.
     */
    @Override
    public <TStyleableAddition extends StyleableAdditionBase> Optional<TStyleableAddition> getStyleableAddition(Class<TStyleableAddition> clazz) {

        if (clazz == null) {
            throw new IllegalArgumentException("Given clazz must not be null");
        }

        if (clazz.equals(SVGStyleableAddition.class)) {
            return Optional.of(clazz.cast(styleAddition));
        }

        return Optional.empty();
    }

    // endregion
}
