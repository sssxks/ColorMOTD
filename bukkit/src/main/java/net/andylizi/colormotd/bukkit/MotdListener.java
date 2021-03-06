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

import net.andylizi.colormotd.core.MotdProvider;
import net.andylizi.colormotd.core.MotdServerIcon;
import net.andylizi.colormotd.core.MotdService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdListener implements Listener {
    private final MotdService service;

    public MotdListener(MotdService service) {
        this.service = service;
    }

    @EventHandler(ignoreCancelled = true)
    public void onMotd(ServerListPingEvent event) {
        MotdProvider provider = service.getProvider();
        if (provider != null && provider.isEnabled()) {
            String motd = provider.provideMotd();
            if (motd != null) {
                event.setMotd(motd);
            }

            MotdServerIcon serverIcon = provider.provideServerIcon();
            if (serverIcon != null) {
                event.setServerIcon(BukkitMotdServerIcon.wrap(serverIcon)
                        .toCachedServerIcon());
            }
        }
    }
}
