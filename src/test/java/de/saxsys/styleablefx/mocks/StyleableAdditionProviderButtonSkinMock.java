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

package de.saxsys.styleablefx.mocks;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import de.saxsys.styleablefx.core.IStyleableAdditionProvider;
import de.saxsys.styleablefx.core.StyleableAdditionBase;
import javafx.scene.control.Button;

import java.util.Optional;

/**
 * Mock to create and test the {@link StyleableAdditionBase}
 *
 * @author Xyanid on 10.01.2016.
 */
public class StyleableAdditionProviderButtonSkinMock extends ButtonSkin implements IStyleableAdditionProvider {

    StyleableAdditionBaseMockC addition = new StyleableAdditionBaseMockC();


    public StyleableAdditionProviderButtonSkinMock(Button button) {
        super(button);
    }

    @Override
    public <TStyleableAddition extends StyleableAdditionBase> Optional<TStyleableAddition> getStyleableAddition(Class<TStyleableAddition> clazz) {

        if (clazz.equals(StyleableAdditionBaseMockC.class)) {
            return Optional.of(clazz.cast(addition));
        } else {
            return Optional.empty();
        }
    }
}
