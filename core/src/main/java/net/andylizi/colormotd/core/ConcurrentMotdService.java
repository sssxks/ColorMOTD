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

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class ConcurrentMotdService implements MotdService {
    private final ReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;

    public ConcurrentMotdService(ReadWriteLock lock) {
        this.readWriteLock = Objects.requireNonNull(lock);
        this.readLock = readWriteLock.readLock();
        this.writeLock = readWriteLock.writeLock();
    }

    public ConcurrentMotdService(boolean fair) {
        this(new ReentrantReadWriteLock(fair));
    }

    public ConcurrentMotdService() {
        this(false);
    }

    @Override
    public final boolean hasProvider() {
        readLock.lock();
        try {
            return hasProvider0();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public final MotdProvider getProvider() {
        readLock.lock();
        try {
            return getProvider0();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public final void setProvider(MotdProvider provider) {
        writeLock.lock();
        try {
            setProvider0(provider);
        } finally {
            writeLock.unlock();
        }
    }

    protected abstract boolean hasProvider0();

    protected abstract MotdProvider getProvider0();

    protected abstract void setProvider0(MotdProvider provider) throws UnsupportedOperationException;
}
