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
package net.andylizi.colormotd.bukkit;

import net.andylizi.colormotd.core.MotdServerIcon;
import org.bukkit.Bukkit;
import org.bukkit.util.CachedServerIcon;

import java.awt.image.BufferedImage;

public class BukkitMotdServerIcon extends MotdServerIcon {
    public static BukkitMotdServerIcon wrap(MotdServerIcon serverIcon) {
        if (serverIcon instanceof BukkitMotdServerIcon) {
            return (BukkitMotdServerIcon) serverIcon;
        }
        return new BukkitMotdServerIcon(serverIcon);
    }

    private CachedServerIcon cachedServerIcon;

    public BukkitMotdServerIcon(String data) {
        super(data);
    }

    public BukkitMotdServerIcon(BufferedImage bufferedImage) {
        super(bufferedImage);
    }

    public BukkitMotdServerIcon(MotdServerIcon serverIcon) {
        super(serverIcon.isDataStringInitialized() ? serverIcon.toDataString() : null,
                serverIcon.isBufferedImageInitialized() ? serverIcon.toBufferedImage() : null);
    }

    public CachedServerIcon toCachedServerIcon() {
        if (cachedServerIcon == null) {
            synchronized (this) {
                if (cachedServerIcon == null) {
                    try {
                        cachedServerIcon = Bukkit.loadServerIcon(toBufferedImage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return cachedServerIcon;
    }
}
