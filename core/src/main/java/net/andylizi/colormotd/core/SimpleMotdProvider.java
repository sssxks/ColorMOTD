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

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleMotdProvider implements MotdProvider {
    protected final MotdConfig config;
    private boolean enabled = true;

    public SimpleMotdProvider(MotdConfig config) {
        this.config = Objects.requireNonNull(config);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String provideMotd() {
        List<String> motds = config.getMotds();
        return motds.get(ThreadLocalRandom.current().nextInt(motds.size()));
    }
}
