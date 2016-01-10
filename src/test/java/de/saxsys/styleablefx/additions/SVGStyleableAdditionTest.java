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

package de.saxsys.styleablefx.additions;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * This class tests the {@link SVGStyleableAddition}.
 *
 * @author Xyanid on 10.01.2016.
 */
public class SVGStyleableAdditionTest {

    private static final String TEST_SVG = "test.svg";

    /**
     * Ensures that {@link SVGStyleableAddition#getClassCssMetaData()} contains all styleable {@link javafx.css.CssMetaData} that is styleable.
     */
    @Test
    public void ensureCssMetaDataContainsAllStyleableProperties() {
        assertTrue(SVGStyleableAddition.getClassCssMetaData().contains(SVGStyleableAddition.SVG_URL));
        assertTrue(SVGStyleableAddition.getClassCssMetaData().contains(SVGStyleableAddition.WILL_CACHE_SVG));
    }

    /**
     * Ensures that changing the {@link SVGStyleableAddition#svgUrlProperty()} will load the file and set the {@link SVGStyleableAddition#svgGroup}.
     */
    @Test
    public void changingUrlPropertyWillLoadTheSvgFileAndSetTheData() {
        SVGStyleableAddition addition = new SVGStyleableAddition();

        assertEquals(0, addition.getSvgGroup().getChildren().size());

        addition.setSvgUrl(TEST_SVG);

        assertEquals(1, addition.getSvgGroup().getChildren().size());
        assertThat(addition.getSvgGroup().getChildren().get(0), new IsInstanceOf(Group.class));
        assertEquals(2, ((Group) addition.getSvgGroup().getChildren().get(0)).getChildren().size());
        assertThat(((Group) addition.getSvgGroup().getChildren().get(0)).getChildren().get(0), new IsInstanceOf(Line.class));
        assertThat(((Group) addition.getSvgGroup().getChildren().get(0)).getChildren().get(1), new IsInstanceOf(Line.class));
    }

    /**
     * Ensure that a file referencing an absolute path can be loaded.
     */
    @Test
    public void ensureAbsolutePathCanBeLoaded() {

        File newFile = null;
        try {
            newFile = new File(exportFile(TEST_SVG, getClass()));
        } catch (Exception e) {
            fail();

            if (newFile.exists()) {
                newFile.delete();
            }
        }

        SVGStyleableAddition addition = new SVGStyleableAddition();

        Thread.currentThread().setUncaughtExceptionHandler((thread, exception) -> fail());

        addition.setSvgUrl(newFile.getAbsolutePath());

        newFile.delete();
    }

    /**
     * Ensure that a file referencing an absolute path can be loaded.
     */
    @Test
    public void ensureRelativePathCanBeLoaded() {

        File newFile = null;
        try {

            newFile = new File(getClassPath(getClass()));

            newFile = new File(newFile.getParentFile().getPath(), "sub/path");

            if (!newFile.exists()) {
                newFile.mkdirs();
            }

            newFile = new File(newFile.getAbsolutePath(), TEST_SVG);

            copyFile(getResourcePath(getClass(), TEST_SVG), newFile.getAbsolutePath());

        } catch (Exception e) {
            fail();

            if (newFile.exists()) {
                newFile.delete();
            }
        }

        SVGStyleableAddition addition = new SVGStyleableAddition();

        Thread.currentThread().setUncaughtExceptionHandler((thread, exception) -> fail());

        addition.setSvgUrl("target/sub/path/" + TEST_SVG);

        newFile.delete();
    }

    /**
     * Ensures that attempting to load an no existing svg file will cause an exception.
     */
    @Test
    public void ensureExceptionIsThrownIfTheFileIsNotFound() {
        SVGStyleableAddition addition = new SVGStyleableAddition();

        Thread.currentThread().setUncaughtExceptionHandler((thread, exception) -> assertThat(exception, new IsInstanceOf(IllegalArgumentException.class)));

        addition.setSvgUrl("asdasd");
    }

    /**
     * Ensure that loading a corrupted svg file will case an exception.
     */
    @Test
    public void ensureExceptionIsThrownIfFileIsCorrupted() {
        SVGStyleableAddition addition = new SVGStyleableAddition();

        Thread.currentThread().setUncaughtExceptionHandler((thread, exception) -> assertThat(exception, new IsInstanceOf(SVGStyleableAddition.ParseException.class)));

        addition.setSvgUrl("corrupted.svg");
    }

    /**
     * Returns the classPath of the given class.
     *
     * @param clazz class to be used
     *
     * @return the class path of hte given class
     *
     * @throws URISyntaxException if the uri is mal formed.
     */
    public static String getClassPath(Class clazz) throws URISyntaxException {
        return clazz.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
    }

    /**
     * Retrieved the given resource using the given class to resolve.
     *
     * @param clazz        the {@link Class} used to resolve the resource.
     * @param resourceName the name of the resource to look for.
     *
     * @return the absolute path to the resource.
     *
     * @throws URISyntaxException is the uri is malformed
     */
    public static String getResourcePath(Class clazz, String resourceName) throws URISyntaxException {
        return new File(clazz.getClassLoader().getResource(resourceName).toURI()).getAbsolutePath();
    }

    /**
     * Copies the given resource name from the package to the target folder.
     *
     * @param resourceName the name of the resource to look for.
     * @param clazz        the class used to load the resource.
     *
     * @return the new absolute path where the file was placed.
     *
     * @throws URISyntaxException if the URI is malformed.
     * @throws IOException        if an error occurs during read or writing the new file.
     */
    public static String exportFile(String resourceName, Class clazz) throws URISyntaxException, IOException {

        if (clazz == null) {
            throw new IllegalArgumentException("Given clazz must not be null");
        }

        String resultPath = new File(getClassPath(clazz)).getParentFile().getPath().concat("/").concat(resourceName);

        copyFile(getResourcePath(clazz, resourceName), resultPath);

        return resultPath;
    }

    /**
     * Copies the given data ob the source file to the given destination.
     *
     * @param source      absolute path to the file to be used as teh source.
     * @param destination the class used to load the resource.
     *
     * @throws URISyntaxException if the URI is malformed.
     * @throws IOException        if an error occurs during read or writing the new file.
     */
    public static void copyFile(String source, String destination) throws URISyntaxException, IOException {

        if (source == null) {
            throw new IllegalArgumentException("Given source must not be null");
        }

        if (destination == null) {
            throw new IllegalArgumentException("Given destination must not be null");
        }

        InputStream stream = new FileInputStream(source);

        int readBytes;

        byte[] buffer = new byte[4096];

        OutputStream resStreamOut = new FileOutputStream(destination);

        while ((readBytes = stream.read(buffer)) > 0) {
            resStreamOut.write(buffer, 0, readBytes);
        }

        stream.close();
        resStreamOut.close();
    }
}