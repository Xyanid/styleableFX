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

import com.sun.javafx.scene.control.skin.ButtonSkin;
import de.saxsys.styleablefx.additions.SVGStyleableAddition;
import de.saxsys.styleablefx.controls.SVGGroup;
import de.saxsys.styleablefx.core.IStyleableAdditionProvider;
import de.saxsys.styleablefx.core.StyleableAdditionBase;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.Optional;

/**
 * This skin can be applied to and a {@link Button} in order to provide an svg image on the button. Internally a {@link SVGGroup} is used, which is also
 * styleable via compound selectors whose name is <b>svg-data</b>. The {@link SVGGroup} is used as the {@link Button}s {@link Button#graphicProperty()} and
 * is scaled every time the {@link Button}s {@link Button#widthProperty()} or {@link Button#heightProperty()} is changed.
 */
public class SVGButtonSkin extends ButtonSkin implements IStyleableAdditionProvider {

    //region Description

    /**
     * Contains the name of the compound selector for the {@link SVGGroup} used in this element.
     */
    public static final String SVG_COMPOUND_SELECTOR = "svg-Data";

    //endregion

    //region Fields

    /**
     * Contains the original width of the {@link Button}, as it was created the first time around.
     */
    double originalWidth;

    /**
     * Contains the original height of the {@link Button}, as it was created the first time around.
     */
    double originalHeight;

    /**
     * The svg data that is being displayed in the graphic node.
     */
    final SVGGroup svgData;

    //endregion

    //region Constructor

    /**
     * Constructor for the skin
     *
     * @param button The {@link Button} for which this Skin should attach to.
     */
    public SVGButtonSkin(final Button button) {
        super(button);

        svgData = new SVGGroup();
        svgData.getStyleClass().setAll(SVG_COMPOUND_SELECTOR);
        svgData.setId(SVG_COMPOUND_SELECTOR);

        button.setGraphic(svgData);

        button.widthProperty().addListener(this::onWidthChanged);
        button.heightProperty().addListener(this::onHeightChanged);
        button.graphicProperty().addListener(this::onGraphicChanged);
    }

    //endregion

    //region Event Handling

    /**
     * Will be called if the width property changes and thus scaling the svg graphic.
     *
     * @param observable the property which changed
     * @param oldValue   the old value of the property
     * @param newValue   the new value of the property
     */
    private void onWidthChanged(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {

        if (originalWidth <= 0.0d) {
            originalWidth = newValue.doubleValue();
        }

        if (originalWidth > 0.0d) {
            svgData.setScaleX(getSkinnable().widthProperty().getValue() / originalWidth);
        }
    }

    /**
     * Will be called if the height property changes and thus scaling the svg graphic.
     *
     * @param observable the property which changed
     * @param oldValue   the old value of the property
     * @param newValue   the new value of the property
     */
    private void onHeightChanged(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {

        if (originalHeight <= 0.0d) {
            originalHeight = newValue.doubleValue();
        }

        if (originalHeight > 0.0d) {
            svgData.setScaleY(getSkinnable().heightProperty().getValue() / originalHeight);
        }
    }

    /**
     * Will be called if the {@link Button#graphicProperty()} changed, this will prevent that the {@link Button#graphicProperty()}is set to anything else
     * then the {@link #svgData}
     *
     * @param observable the property which changed
     * @param oldValue   the old value of the property
     * @param newValue   the new value of the property
     */
    private void onGraphicChanged(final ObservableValue<? extends Node> observable, final Node oldValue, final Node newValue) {
        if (newValue != svgData) {
            getSkinnable().graphicProperty().set(svgData);
        }
    }

    //endregion

    // region Implement IStyleableAdditionProvider

    /**
     * {@inheritDoc}. This implementation only supports {@link SVGStyleableAddition}.
     *
     * @throws IllegalArgumentException if the given {@link Class} is null.
     */
    @Override
    public <TStyleableAddition extends StyleableAdditionBase> Optional<TStyleableAddition> getStyleableAddition(final Class<TStyleableAddition> clazz) {

        if (clazz == null) {
            throw new IllegalArgumentException("Given clazz must not be null");
        }

        if (clazz.equals(SVGStyleableAddition.class)) {
            return svgData.getStyleableAddition(clazz);
        }
        return Optional.empty();
    }

    // endregion
}
