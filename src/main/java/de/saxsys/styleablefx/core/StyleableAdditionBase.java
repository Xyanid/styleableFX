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

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.control.Control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The idea of this class is simply to allow both {@link javafx.scene.layout.Region}s and {@link Control}s to share a base class which responsible to style a {@link javafx.scene.Node},
 * so there is no need to have the same code twice. Usually an instance of this class will be created within the {@link javafx.scene.control.Skin} or {@link javafx.scene.layout.Region} it should be
 * used by. Then the region calls the {@link StyleableAdditionBase#getClassCssMetaData}s, which will provide the list of {@link CssMetaData} that can be styled for this class.
 *
 * @author Xyanid on 08.11.2015.
 */
public abstract class StyleableAdditionBase {

    /**
     * Returns the {@link StyleableAdditionBase} from the given {@link Styleable} using the given clazz. This method is supposed to be used inside a {@link CssMetaData} of a
     * {@link StyleableAdditionBase} and ensured that the {@link StyleableAdditionBase} is found on the {@link Styleable} which used the {@link StyleableAdditionBase}.
     * The way this method works is that it checks if the passed {@link Styleable} is a {@link IStyleableAdditionProvider}, if so the desired {@link StyleableAdditionBase} is requested from the
     * {@link IStyleableAdditionProvider}. If the {@link StyleableAdditionBase} was not found this way, it is possible that the {@link Styleable} is a {@link Control}. In this case the
     * {@link javafx.scene.control.Skin} is checked the same way.
     *
     * @param styleable            {@link Styleable} to use.
     * @param clazz                class to use.
     * @param <TStyleableAddition> type of the styleable addition.
     *
     * @return the {@link StyleableAdditionBase} from the {@link Styleable}.
     *
     * @throws IllegalArgumentException if either {@link Styleable} or {@link Class} is null or the desired {@link StyleableAdditionBase} was not found on the {@link Styleable}.
     */
    public static <TStyleableAddition extends StyleableAdditionBase> TStyleableAddition getStyleableAddition(final Styleable styleable, final Class<TStyleableAddition> clazz) {

        if (styleable == null) {
            throw new IllegalArgumentException("Given styleable is null");
        }

        if (clazz == null) {
            throw new IllegalArgumentException("Given class must not be null");
        }

        Optional<TStyleableAddition> styleableAddition = Optional.empty();

        // first we check if the element itself already provided the desired StyleableAdditionBase
        if (styleable instanceof IStyleableAdditionProvider) {
            styleableAddition = IStyleableAdditionProvider.class.cast(styleable).getStyleableAddition(clazz);
        }

        // if no StyleableAdditionBase was found yet, we need might need to look into the controls skin
        if (!styleableAddition.isPresent() && styleable instanceof Control && ((Control) styleable).getSkin() != null) {
            styleableAddition = IStyleableAdditionProvider.class.cast(((Control) styleable).getSkin()).getStyleableAddition(clazz);
        }

        // if we still do not have a StyleableAdditionBase at this point the method failed
        if (!styleableAddition.isPresent()) {
            throw new IllegalArgumentException(String.format("Given StyleableAdditionBase for clazz %s was not found on provided styleable %s", clazz.getName(), styleable.getClass().getName()));
        }

        return styleableAddition.get();
    }

    /**
     * This methods need to be provided in any class extending a {@link StyleableAdditionBase}. The sole purpose of the method in the {@link StyleableAdditionBase} is to provide an recommendation
     * for the name to use, as such this method will only return an empty {@link ArrayList}.
     *
     * @return an empty {@link ArrayList}.
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return new ArrayList<>();
    }
}
