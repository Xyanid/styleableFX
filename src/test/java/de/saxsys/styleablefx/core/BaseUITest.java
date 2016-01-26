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

import javafx.application.Application;
import javafx.css.Styleable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.BeforeClass;

import java.util.function.Consumer;

import static javafx.application.Application.launch;

/**
 * This class will setup JavaFX so UI components can be created without causing the ExceptionInInitializerError.
 *
 * @author Xyanid on 10.01.2016.
 */
public class BaseUITest {

    //region Sets up the test so UI elements can be created

    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {}
    }

    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        // Initialise Java FX

        System.out.printf("About to launch FX App\n");
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                launch(AsNonApp.class);
            }
        };
        t.setDaemon(true);
        t.start();
        System.out.printf("FX App thread started\n");
        Thread.sleep(500);
    }

    //endregion

    //region Testing of Styles can be applied to Controls or Nodes

    /**
     * Sets up a basic scene for the given {@link Control} and then adds a stylesheet called. There are a couple of rules the stylesheet has to
     * follow in order for this method to work. The rule are as follows.
     * <ul>
     * <li>the name of the stylesheet must be |classname of the skin| + "ClassSelector"</li>
     * <li>the stylesheet must be in a sub folder in the resources named like resourceRoot</li>
     * <li>if resource root is submit and not null, it determines the root path of the stylesheet in the resources</li>
     * </ul>
     *
     * @param parent             the {@link Control} that is to be checked.
     * @param resourceRoot       the
     * @param consumeBeforeApply the {@link Consumer} which will be called before the actual style are applied to the {@link javafx.scene.control.Skin}. In
     *                           this you should check if the state of the {@link javafx.scene.control.Skin} is as expected.
     * @param consumerAfterApply the {@link Consumer} which will be called after the actual style are applied to the {@link javafx.scene.control.Skin}. In
     *                           this you should check if the state of the {@link javafx.scene.control.Skin} has been changed as expected.
     * @param <TControl>         the type of the {@link Control}.
     */
    public static <TControl extends Control> void assertSkinStyleableViaClassSelector(final TControl parent,
                                                                                      final String resourceRoot,
                                                                                      final Consumer<TControl> consumeBeforeApply,
                                                                                      final Consumer<TControl> consumerAfterApply) {
        assertIsStyleableViaClassSelector(parent, resourceRoot, parent.getSkin().getClass(), consumeBeforeApply, consumerAfterApply);
    }

    /**
     * Sets up a basic scene for the given {@link Control} to and then adds a stylesheet called. There are a couple of rules the stylesheet has to
     * follow in order for this method to work. The rule are as follows.
     * <ul>
     * <li>the name of the stylesheet must be |classname| + IdSelector</li>
     * <li>the stylesheet must be in a sub folder in the resources named like resourceRoot</li>
     * <li>the id in the stylesheet must be "id"</li>
     * <li>if resource root is submit and not null, it determines the root path of the stylesheet in the resources</li>
     * </ul>
     *
     * @param parent             the {@link Parent} that is to be checked.
     * @param resourceRoot       the
     * @param consumeBeforeApply the {@link Consumer} which will be called before the actual style are applied to the given {@link javafx.scene.control.Skin}.
     *                           In this you should check if the state of the {@link javafx.scene.control.Skin} is as expected.
     * @param consumerAfterApply the {@link Consumer} which will be called after the actual style are applied to the given {@link javafx.scene.control.Skin}.
     *                           In this you should check if the state of the {@link javafx.scene.control.Skin} has been changed as expected.
     * @param <TControl>         the type of the {@link Control}.
     */
    public static <TControl extends Control> void assertSkinStyleableViaIdSelector(final TControl parent,
                                                                                   final String resourceRoot,
                                                                                   final Consumer<TControl> consumeBeforeApply,
                                                                                   final Consumer<TControl> consumerAfterApply) {
        parent.setId("#id");

        assertStyleableViaIdSelector(parent, resourceRoot, parent.getSkin().getClass(), consumeBeforeApply, consumerAfterApply);
    }

    /**
     * Sets up a basic scene for the given {@link Parent} and then adds a stylesheet. There are a couple of rules the stylesheet has to follow in order
     * for this method to work. The rule are as follows.
     * <ul>
     * <li>the name of the stylesheet must be |classname| + "ClassSelector"</li>
     * <li>the stylesheet must be in a sub folder in the resources named like resourceRoot</li>
     * <li>if resource root is submit and not null, it determines the root path of the stylesheet in the resources</li>
     * </ul>
     *
     * @param parent             the {@link Parent} that is to be checked.
     * @param resourceRoot       the
     * @param consumeBeforeApply the {@link Consumer} which will be called before the actual style are applied to the given {@link Parent}. In this you
     *                           should check if the state of the {@link Parent} is as expected.
     * @param consumerAfterApply the {@link Consumer} which will be called after the actual style are applied to the given {@link Parent}. In this you
     *                           should check if the state of the {@link Parent} has been changed as expected.
     * @param <TParent>          the type of the {@link Parent}.
     */
    public static <TParent extends Parent> void assertParentStyleableViaClassSelector(final TParent parent,
                                                                                      final String resourceRoot,
                                                                                      final Consumer<TParent> consumeBeforeApply,
                                                                                      final Consumer<TParent> consumerAfterApply) {
        assertIsStyleableViaClassSelector(parent, resourceRoot, parent.getClass(), consumeBeforeApply, consumerAfterApply);
    }

    /**
     * Sets up a basic scene for the given {@link Parent} to and then adds a stylesheet called. There are a couple of rules the stylesheet has to
     * follow in order for this method to work. The rule are as follows.
     * <ul>
     * <li>the name of the stylesheet must be |classname| + IdSelector</li>
     * <li>the stylesheet must be in a sub folder in the resources named like resourceRoot</li>
     * <li>the id in the stylesheet must be "id"</li>
     * <li>if resource root is submit and not null, it determines the root path of the stylesheet in the resources</li>
     * </ul>
     *
     * @param parent             the {@link Parent} that is to be checked.
     * @param resourceRoot       the
     * @param consumeBeforeApply the {@link Consumer} which will be called before the actual style are applied to the given {@link Parent}. In this you
     *                           should check if the state of the {@link Parent} is as expected.
     * @param consumerAfterApply the {@link Consumer} which will be called after the actual style are applied to the given {@link Parent}. In this you
     *                           should check if the state of the {@link Parent} has been changed as expected.
     * @param <TParent>          the type of the {@link Parent}.
     */
    public static <TParent extends Parent> void assertParentStyleableViaIdSelector(final TParent parent,
                                                                                   final String resourceRoot,
                                                                                   final Consumer<TParent> consumeBeforeApply,
                                                                                   final Consumer<TParent> consumerAfterApply) {
        parent.setId("id");

        assertStyleableViaIdSelector(parent, resourceRoot, parent.getClass(), consumeBeforeApply, consumerAfterApply);
    }

    //endregion

    //region Private

    /**
     * Sets up a basic scene for the given {@link Parent} and then adds a stylesheet. There are a couple of rules the stylesheet has to follow in order
     * for this method to work. The rule are as follows.
     * <ul>
     * <li>the name of the stylesheet must be {@link Class#getSimpleName()} + "ClassSelector", where the class is styleSheetNameClass</li>
     * <li>the stylesheet must be in a sub folder in the resources named like resourceRoot</li>
     * <li>if resource root is submit and not null, it determines the root path of the stylesheet in the resources</li>
     * </ul>
     *
     * @param parent             the {@link Parent} that is to be checked.
     * @param resourceRoot       the
     * @param consumeBeforeApply the {@link Consumer} which will be called before the actual style are applied to the given {@link Parent}. In this you
     *                           should check if the state of the {@link Parent} is as expected.
     * @param consumerAfterApply the {@link Consumer} which will be called after the actual style are applied to the given {@link Parent}. In this you
     *                           should check if the state of the {@link Parent} has been changed as expected.
     * @param <TParent>          the type of the {@link Parent}.
     */
    private static <TParent extends Parent> void assertIsStyleableViaClassSelector(final TParent parent,
                                                                                   final String resourceRoot,
                                                                                   final Class<?> styleSheetNameClass,
                                                                                   final Consumer<TParent> consumeBeforeApply,
                                                                                   final Consumer<TParent> consumerAfterApply) {
        assertStyleable(parent,
                        String.format("%s/%sClassSelector.css", resourceRoot != null ? resourceRoot : "", styleSheetNameClass.getSimpleName()),
                        consumeBeforeApply,
                        consumerAfterApply);
    }

    /**
     * Sets up a basic scene for the given {@link Parent} to and then adds a stylesheet called. There are a couple of rules the stylesheet has to
     * follow in order for this method to work. The rule are as follows.
     * <ul>
     * <li>the name of the stylesheet must be {@link Class#getSimpleName()} + "IdSelector", where the class is styleSheetNameClass</li>
     * <li>the stylesheet must be in a sub folder in the resources named like resourceRoot</li>
     * <li>the id in the stylesheet must be "id"</li>
     * <li>if resource root is submit and not null, it determines the root path of the stylesheet in the resources</li>
     * </ul>
     *
     * @param parent             the {@link Parent} that is to be checked.
     * @param resourceRoot       the
     * @param consumeBeforeApply the {@link Consumer} which will be called before the actual style are applied to the given {@link Parent}. In this you
     *                           should check if the state of the {@link Parent} is as expected.
     * @param consumerAfterApply the {@link Consumer} which will be called after the actual style are applied to the given {@link Parent}. In this you
     *                           should check if the state of the {@link Parent} has been changed as expected.
     * @param <TParent>          the type of the {@link Parent}.
     */
    private static <TParent extends Parent> void assertStyleableViaIdSelector(final TParent parent,
                                                                              final String resourceRoot,
                                                                              final Class<?> styleSheetNameClass,
                                                                              final Consumer<TParent> consumeBeforeApply,
                                                                              final Consumer<TParent> consumerAfterApply) {
        parent.setId("#id");

        assertStyleable(parent,
                        String.format("%s/%sIdSelector.css", resourceRoot != null ? resourceRoot : "", styleSheetNameClass.getSimpleName()),
                        consumeBeforeApply,
                        consumerAfterApply);
    }

    /**
     * Basic setup a scene for the given {@link Styleable} to and then adds a stylesheet. There are a couple of rules the stylesheet has to follow in order
     * for this method to work. The rule are as follows.
     * <ul>
     * <li>the name of the stylesheet must be "classname" + resourceExtension</li>
     * <li>the stylesheet must be in a sub folder in the resources named like resourceRoot</li>
     * <li>if resourceRoot is submit and not null, it determines the root path of the stylesheet in the resources</li>
     * <li>if resourceExtension</li>
     * </ul>
     *
     * @param parent             the {@link Parent} that is to be checked.
     * @param styleSheetLocation the location of the stylesheet to be used.
     * @param consumeBeforeApply the {@link Consumer} which will be called before the actual style are applied to the given {@link Parent}. In this you
     *                           should check if the state of the {@link Parent} is as expected.
     * @param consumerAfterApply the {@link Consumer} which will be called after the actual style are applied to the given {@link Parent}. In this you
     *                           should check if the state of the {@link Parent} has been changed as expected.
     * @param <TParent>          the type of the {@link Parent}.
     */
    private static <TParent extends Parent> void assertStyleable(final TParent parent,
                                                                 final String styleSheetLocation,
                                                                 final Consumer<TParent> consumeBeforeApply,
                                                                 final Consumer<TParent> consumerAfterApply) {

        runOnJavaFXThread(() -> {

            parent.getStylesheets().add(styleSheetLocation);

            consumeBeforeApply.accept(parent);

            // create root and scene and add button
            Pane root = new Pane();

            root.getChildren().add(parent);

            Stage stage = new Stage();

            stage.setScene(new Scene(root));

            stage.show();

            consumerAfterApply.accept(parent);
        });
    }

    /**
     * Runs the given {@link Runnable} in a javaFX thread.
     *
     * @param runnable the {@link Runnable} to be run on a java fx thread
     */
    public static void runOnJavaFXThread(Runnable runnable) {
        javafx.application.Platform.runLater(runnable);
    }

    //endregion
}
