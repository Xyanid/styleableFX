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

/**
 * The idea of this class is simply to allow both {@link javafx.scene.layout.Region}s and {@link Control}s to share a base class which responsible to style a {@link javafx.scene.Node},
 * so there is no need to have the same code twice. Usually an instance of this class will be created within the {@link javafx.scene.control.Skin} or {@link javafx.scene.layout.Region} it should be
 * used by. Then the region calls the {@link StyleableAdditionBase#getClassCssMetaData}s, which will provide the list of {@link CssMetaData} that can be styled for this class.
 *
 * @author Xyanid on 08.11.2015.
 */
public abstract class StyleableAdditionBase {

    /**
     * Returns the {@link IStyleableAdditionProvider} from the given {@link Styleable}.
     * The {@link IStyleableAdditionProvider} is either the {@link Styleable} itself or a {@link javafx.scene.control.Skin} in case the provided {@link Styleable} is a {@link Control}.
     * This is needed since {@link Control}s are only styleable via their {@link javafx.scene.control.Skin}s.
     *
     * @param styleable the {@link Styleable} from which the {@link IStyleableAdditionProvider} is to be retrieved.
     *
     * @return the {@link IStyleableAdditionProvider} associated with the given {@link Styleable}.
     */
    public static IStyleableAdditionProvider getStyleableAddition(final Styleable styleable) {
        if (styleable == null) {
            throw new IllegalArgumentException("given styleable must not be null");
        }

        if (styleable instanceof IStyleableAdditionProvider) {
            return (IStyleableAdditionProvider) styleable;
        } else {
            return (IStyleableAdditionProvider) ((Control) styleable).getSkin();
        }
    }

    /**
     * Returns the {@link StyleableAdditionBase} from the given {@link Styleable} using the given clazz. Using this method will invoke {@link #getStyleableAddition(Styleable)} to
     * determine if the {@link Styleable} is either a {@link Control} or {@link javafx.scene.layout.Region}.
     *
     * @param styleable            {@link Styleable} to use.
     * @param clazz                class to use.
     * @param <TStyleableAddition> type of the styleable addition.
     *
     * @return the {@link StyleableAdditionBase} from the {@link Styleable}.
     *
     * @throws IllegalArgumentException in case the given class is null.
     */
    public static <TStyleableAddition extends StyleableAdditionBase> TStyleableAddition getStyleableAddition(final Styleable styleable, final Class<TStyleableAddition> clazz) {

        if (clazz == null) {
            throw new IllegalArgumentException("given class must not be null");
        }

        return getStyleableAddition(styleable).getStyleableAddition(clazz);
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
