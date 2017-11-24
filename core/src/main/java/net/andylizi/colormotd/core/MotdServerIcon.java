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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

// todo unit test
public class MotdServerIcon {
    static String imageToDataString(RenderedImage image) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", bout);
        } catch (IOException e) {
            throw new UncheckedIOException("Not possible", e);
        }

        byte[] data = bout.toByteArray();
        byte[] base64 = Base64.getEncoder().encode(data);
        return "data:image/png;base64," + new String(base64, StandardCharsets.UTF_8);
    }

    static BufferedImage dataStringToImage(String data) {
        final String header = "data:image/";
        final int headerLength = header.length();
        final String base64Header = "base64,";

        if (!data.regionMatches(true, 0, header, 0, headerLength)) {
            throw new IllegalArgumentException("Invalid data: " + data);
        }

        try {
            String str = data.substring(headerLength);
            int firstSemicolon = str.indexOf(';');
            if (!str.regionMatches(true, firstSemicolon + 1, base64Header, 0, base64Header.length())) {
                throw new IllegalArgumentException("Invalid data: " + data);
            }

            int firstComma = str.indexOf(',');
            String base64 = str.substring(firstComma + 1);
            return ImageIO.read(Base64.getDecoder().wrap(new ByteArrayInputStream(base64.getBytes(StandardCharsets.UTF_8))));
        } catch (IndexOutOfBoundsException | IOException e) {
            throw new IllegalArgumentException("Invalid data: " + data, e);
        }
    }

    private BufferedImage bufferedImage;
    private String data;

    public MotdServerIcon(String data) {
        this.data = Objects.requireNonNull(data);
    }

    public MotdServerIcon(BufferedImage bufferedImage) {
        this.bufferedImage = Objects.requireNonNull(bufferedImage);
    }

    protected MotdServerIcon(String data, BufferedImage bufferedImage) {
        if (data == null && bufferedImage == null) {
            throw new NullPointerException();
        }
        this.data = data;
        this.bufferedImage = bufferedImage;
    }

    public String toDataString() {
        if (data == null) {
            synchronized (this) {
                if (data == null) {
                    if (bufferedImage == null) {
                        throw new IllegalStateException("data = null and bufferedImage = null");
                    }
                    data = imageToDataString(bufferedImage);
                }
            }
        }
        return data;
    }

    public BufferedImage toBufferedImage() {
        if (bufferedImage == null) {
            synchronized (this) {
                if (bufferedImage == null) {
                    if (data == null) {
                        throw new IllegalStateException("bufferedImage = null and data = null");
                    }
                    bufferedImage = dataStringToImage(data);
                }
            }
        }
        return bufferedImage;
    }

    public boolean isBufferedImageInitialized() {
        return bufferedImage != null;
    }

    public boolean isDataStringInitialized() {
        return data != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MotdServerIcon)) return false;
        MotdServerIcon that = (MotdServerIcon) o;
        return Objects.equals(toDataString(), that.toDataString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toDataString());
    }

    @Override
    public String toString() {
        return toDataString();
    }
}
