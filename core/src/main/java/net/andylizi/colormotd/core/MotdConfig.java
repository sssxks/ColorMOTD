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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MotdConfig {
    private final List<String> motds = new ArrayList<>();
    private final List<String> motdView = Collections.unmodifiableList(motds);

    private final List<MotdServerIcon> serverIcons = new ArrayList<>();
    private final List<MotdServerIcon> serverIconView = Collections.unmodifiableList(serverIcons);

    public List<String> getMotds() {
        return motdView;
    }

    public void addMotd(String motd) {
        motds.add(motd);
    }

    public void clearMotds() {
        motds.clear();
    }

    public List<MotdServerIcon> getServerIcons() {
        return serverIconView;
    }

    public void addServerIcon(MotdServerIcon icon) {
        serverIcons.add(icon);
    }

    public void clearServerIcons() {
        serverIcons.clear();
    }
}
