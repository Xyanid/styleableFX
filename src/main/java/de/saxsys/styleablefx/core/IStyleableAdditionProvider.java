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
package de.saxsys.styleablefx.core;

/**
 * This interface is to be attached either a {@link javafx.scene.control.Skin} or {@link javafx.scene.layout.Region} that use a {@link StyleableAdditionBase}.
 *
 * @author Xyanid on 08.11.2015.
 */
public interface IStyleableAdditionProvider {

    /**
     * Provides the {@link StyleableAdditionBase} for the desired class.
     *
     * @param clazz                the class for which the skin addition should be returned.
     * @param <TStyleableAddition> type of the styleable
     *
     * @return the {@link StyleableAdditionBase} that is attached to this skin
     */
    <TStyleableAddition extends StyleableAdditionBase> TStyleableAddition getStyleableAddition(final Class<TStyleableAddition> clazz);
}

