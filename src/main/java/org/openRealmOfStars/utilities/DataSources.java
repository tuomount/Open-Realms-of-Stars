package org.openRealmOfStars.utilities;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 BottledByte
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
 */

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

/**
 * Class for looking-up files in various data sources.
 *
 * <p>
 * Intended to allow (rather basic) modding.
 * Game data can be "shadowed" by data with same name
 * in other source, which has higher priority.
 * In case the data is not found in external source,
 * it will fallback to trying to load it from game JAR.
 * </p>
 *
 * Only alternative external data source is currenly
 * <i>"gamedata" directory, in directory with game JAR</i>.
 */
public final class DataSources {
  /** The Singleton */
  private static final DataSources SINGLETON = new DataSources();

  /**
   * Search for requested file with relativeName in all sources,
   * prefer external, fallback to JAR.
   * @param relativeName data "name"
   * @return URL to data or empty
   */
  public static Optional<URL> getDataUrl(final String relativeName) {
    return SINGLETON.getUrl(relativeName);
  }

  /**
   * Find JSON files in certain path.
   * @param path Path where to search
   * @return Array of JSON files in path.
   */
  public static String[] findJsonFilesInPath(final String path) {
    ArrayList<String> jsonInPath = new ArrayList<>();
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();
    if (listOfFiles != null) {
      for (File file : listOfFiles) {
        if (file.isFile() && file.getAbsolutePath().endsWith(".json")) {
          String parentPath;
          try {
            parentPath = file.getParentFile().getCanonicalPath();
            String str = file.getCanonicalPath();
            str = str.substring(parentPath.length() + 1, str.length() - 5);
            jsonInPath.add(str);
          } catch (IOException e) {
            ErrorLogger.log("Error with path handling: " + e.getMessage());
          }
        }
      }
    }
    return jsonInPath.toArray(new String[0]);
  }

  /** List of paths to try before fallback */
  private ArrayList<String> shadowPaths;

  /** Construct new object */
  private DataSources() {
    this.shadowPaths = new ArrayList<>();

    String srcJarPath = null;
    try {
      srcJarPath = DataSources.class.getProtectionDomain()
          .getCodeSource().getLocation().toURI().getPath();
    } catch (URISyntaxException e) {
      ErrorLogger.log(e);
    } catch (SecurityException e) {
      ErrorLogger.log(e);
    }
    // Probably run in aan unusual or strict environment
    // Don't add relative external data directory
    if (srcJarPath == null) {
      ErrorLogger.log("Could not locate where the game JAR is");
      return;
    }

    // Information about what data dir will be used does not hurt
    ErrorLogger.debug("External data dir at: " + srcJarPath);

    var srcJarDir = new File(srcJarPath).getParent();
    addSource(srcJarDir + "/gamedata");
  }

  /**
   *
   * @param extSourcePath path to add
   */
  private void addSource(final String extSourcePath) {
    shadowPaths.add(0, extSourcePath);
  }

  /**
   * Search for requested data with relativeName in all sources,
   * prefer external, fallback to JAR.
   * @param relativeName data "name"
   * @return URL to data or empty
   */
  private Optional<URL> getUrl(final String relativeName) {
    Objects.requireNonNull(relativeName);
    // Search in absolute path first
    File absFile = new File(relativeName);
    if (absFile.exists() && absFile.isFile()) {
      try {
        return Optional.of(absFile.toURI().toURL());
      } catch (MalformedURLException e) {
        ErrorLogger.log(e);
      }
    }
    // Search in external data directories second
    for (var basePath : shadowPaths) {
      final var file = new File(basePath, relativeName);
      if (file.exists() && file.isFile()) {
        try {
          return Optional.of(file.toURI().toURL());
        } catch (MalformedURLException e) {
          ErrorLogger.log(e);
        }
      }
    }

    // Search in JAR file resources as fallback
    // TODO: This if a compatibility hack, to work with rest of the codebase
    var resName = relativeName;
    if (resName.startsWith("/")) {
      resName = resName.substring(1);
    }

    final var resourceUrl = DataSources.class.getResource("/" + resName);
    if (resourceUrl == null) {
      ErrorLogger.log("Failed to locate file in JAR: " + relativeName);
    }
    return Optional.ofNullable(resourceUrl);
  }
}
