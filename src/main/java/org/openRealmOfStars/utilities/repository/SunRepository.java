package org.openRealmOfStars.utilities.repository;

import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.utilities.IOUtilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 *
 *
 * Sun repository class
 *
 */
public class SunRepository {

    /**
     * Save Sun information to DataOutputStream
     * @param dos DataOutputStream
     * @param sun Sun to save
     * @throws IOException if there is any problem with DataOutputStream
     */
    public void saveSun(final DataOutputStream dos, final Sun sun)
        throws IOException {
        dos.writeInt(sun.getCenterX());
        dos.writeInt(sun.getCenterY());
        IOUtilities.writeString(dos, sun.getName());
    }

    /**
     * Create the sun from DataInputStream
     * @param dis DataInputStream
     * @return Sun from DataInputStream
     * @throws IOException if there is any problem with DataInputStream
     */
    public Sun restoreSun(final DataInputStream dis) throws IOException {
        return new Sun(new Coordinate(dis.readInt(), dis.readInt()),
            IOUtilities.readString(dis));
    }
}
