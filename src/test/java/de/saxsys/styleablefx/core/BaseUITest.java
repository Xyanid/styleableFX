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
import javafx.stage.Stage;
import org.junit.BeforeClass;

import static javafx.application.Application.launch;

/**
 * This class will setup JavaFX so UI components can be created without causing the ExceptionInInitializerError.
 *
 * @author Xyanid on 10.01.2016.
 */
public class BaseUITest {

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
}
