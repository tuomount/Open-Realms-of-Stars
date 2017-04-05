package org.openRealmOfStars.utilities;

import java.io.File;
import java.io.FileFilter;

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
 * Generic file filter to filter out files by extension
 *
 */
public class GenericFileFilter implements FileFilter {

  /**
   * Extension to filter files
   */
  private String extension;

  /**
   * Filter with file extension. Accept only file to matching extension
   * @param extension File Extension
   */
  public GenericFileFilter(final String extension) {
    this.extension = extension;
  }

  @Override
  public boolean accept(final File pathname) {
    return pathname.getName().endsWith(extension);
  }

}
