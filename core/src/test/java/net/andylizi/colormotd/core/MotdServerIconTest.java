/*
 * Copyright (C) 2017 andylizi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.andylizi.colormotd.core;

import com.google.common.io.ByteStreams;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MotdServerIconTest {
    static BufferedImage image;
    static String data;

    @BeforeClass
    public static void loadResources() throws IOException {
        image = ImageIO.read(MotdServerIconTest.class.getResourceAsStream("/favicon_64px.png"));
        data = new String(ByteStreams.toByteArray(MotdServerIconTest.class
                .getResourceAsStream("/favicon_64px_encoded.txt")), StandardCharsets.UTF_8);
    }

    @Test
    public void testImageToDataString() throws IOException {
        assertEquals(data, MotdServerIcon.imageToDataString(image));
    }

    @Test
    public void testDataStringToImage() {
        assertImageEquals(image, MotdServerIcon.dataStringToImage(data));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDataString1() {
        MotdServerIcon.dataStringToImage("base64,blablablabla");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDataString2() {
        MotdServerIcon.dataStringToImage("data:text/png;base64,66666666");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDataString3() {
        MotdServerIcon.dataStringToImage("data:image/png");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDataString4() {
        MotdServerIcon.dataStringToImage("data:image/png;base23,3333333");
    }

    private static void assertImageEquals(BufferedImage expected, BufferedImage actual) {
        int width = expected.getWidth(), height = expected.getHeight();
        assertEquals(width, actual.getWidth());
        assertEquals(height, actual.getHeight());

        int[] expectedPixels = new int[width * height];
        int[] actualPixels = new int[expectedPixels.length];
        expected.getRGB(0, 0, width, height, expectedPixels, 0, 1);
        actual.getRGB(0, 0, width, height, actualPixels, 0, 1);
        assertArrayEquals(expectedPixels, actualPixels);
    }
}