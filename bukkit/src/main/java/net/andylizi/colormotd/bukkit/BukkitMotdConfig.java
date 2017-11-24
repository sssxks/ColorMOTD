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

import net.andylizi.colormotd.core.MotdConfig;
import net.andylizi.colormotd.core.MotdServerIcon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BukkitMotdConfig extends MotdConfig {
    private final List<BukkitMotdServerIcon> cachedServerIcons = new ArrayList<>();
    private final List<BukkitMotdServerIcon> cachedServerIconView = Collections.unmodifiableList(cachedServerIcons);

    public List<BukkitMotdServerIcon> getCachedServerIcons() {
        return cachedServerIconView;
    }

    @Override
    public void addServerIcon(MotdServerIcon icon) {
        super.addServerIcon(icon);
        cachedServerIcons.add(BukkitMotdServerIcon.wrap(icon));
    }

    @Override
    public void clearServerIcons() {
        super.clearServerIcons();
        cachedServerIcons.clear();
    }
}
