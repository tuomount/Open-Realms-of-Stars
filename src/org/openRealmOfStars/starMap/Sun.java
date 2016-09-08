package org.openRealmOfStars.starMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openRealmOfStars.utilities.IOUtilities;
import org.openRealmOfStars.utilities.RandomSystemNameGenerator;

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
 * Class sun
 * 
 */

public class Sun {

  /**
   * Sun's name
   */
  private String name;
  
  /**
   * Sun's Center x coordinate
   */
  private int centerX;
  /**
   * Sun's Center y coordinate
   */
  private int centerY;
  
  /**
   * Create the Sun with name and center coordinates
   * @param cx X coordinate
   * @param cy Y coordinate
   * @param name Sun name if null then random generator applied
   */
  public Sun(int cx,int cy, String name) {
    this.setCenterX(cx);
    this.setCenterY(cy);
    if (name == null) {
      this.setName(RandomSystemNameGenerator.generate());
    } else {
      this.setName(name);
    }
  }

  /**
   * Create the sun from DataInputStream
   * @param dis DataInputStream
   * @throws IOException
   */
  public Sun(DataInputStream dis) throws IOException {
    this.setCenterX(dis.readInt());
    this.setCenterY(dis.readInt());
    this.setName(IOUtilities.readString(dis));
  }
  
  /**
   * Save Sun information to DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException
   */
  public void saveSun(DataOutputStream dos) throws IOException {
    dos.writeInt(centerX);
    dos.writeInt(centerY);
    IOUtilities.writeString(dos, name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCenterX() {
    return centerX;
  }

  public void setCenterX(int centerX) {
    this.centerX = centerX;
  }

  public int getCenterY() {
    return centerY;
  }

  public void setCenterY(int centerY) {
    this.centerY = centerY;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append(" X:");
    sb.append(centerX);
    sb.append(" Y:");
    sb.append(centerY);
    return sb.toString();
  }
  
  
}
