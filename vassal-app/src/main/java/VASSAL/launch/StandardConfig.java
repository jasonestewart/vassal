/*
 * Copyright (c) 2020 by Joel Uckelman
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License (LGPL) as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, copies are available
 * at http://www.opensource.org.
 */
package VASSAL.launch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.SystemUtils;

import VASSAL.tools.version.GitProperties;

public class StandardConfig implements Config {
  private final Path baseDir; 
  private final Path docDir;
  private final Path confDir;
  private final Path tmpDir;
  private final Path prefsDir;

  private final Path errorLogPath;
  private final Path javaBinPath;

  private final int instanceID;

  private final String version;
  private final String reportableVersion;

  public StandardConfig() throws IOException {
    // Set the instance id from the system properties.
    final String idstr = System.getProperty("VASSAL.id");
    if (idstr == null) {
      instanceID = 0;
    }
    else {
      int id;
      try {
        id = Integer.parseInt(idstr);
      }
      catch (NumberFormatException e) {
        id = -1;
      }

      instanceID = id;
    }

    // Set the version, reportable version
    final GitProperties gitProperties = new GitProperties();
    version = gitProperties.getVersion();
    reportableVersion = version.contains("-") ? version.substring(0, version.indexOf('-')) : version;

    baseDir = Path.of(System.getProperty("user.dir"));

    docDir = baseDir.resolve(
      SystemUtils.IS_OS_MAC_OSX ? "Contents/Resources/doc" : "doc"
    );

    // Set up the config dir and ensure it exists
    if (SystemUtils.IS_OS_MAC_OSX) {
      confDir = Path.of(System.getProperty("user.home"), "Library/Application Support/VASSAL");
    }
    else if (SystemUtils.IS_OS_WINDOWS) {
      confDir = Path.of(System.getenv("APPDATA"), "VASSAL");
    }
    else {
      confDir = Path.of(System.getProperty("user.home"), ".VASSAL");
    }

    Files.createDirectories(confDir);    

    prefsDir = confDir.resolve("prefs");
    errorLogPath = confDir.resolve("errorLog-" + getVersion());

    javaBinPath = Path.of(System.getProperty("java.home"), "bin", "java");

    // Set up the temp dir and ensure it exists
    tmpDir = Files.createTempDirectory("vassal_");
    tmpDir.toFile().deleteOnExit();
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public String getReportableVersion() {
    return reportableVersion; 
  }

  @Override
  public int getInstanceID() {
    return instanceID;
  }

  @Override
  public Path getBaseDir() {
    return baseDir;
  }

  @Override
  public Path getDocDir() {
    return docDir;
  }

  @Override
  public Path getConfDir() {
    return confDir;
  }

  @Override
  public Path getTempDir() {
    return tmpDir;
  }

  @Override
  public Path getPrefsDir() {
    return prefsDir;
  }

  @Override
  public Path getErrorLogPath() {
    return errorLogPath;
  }

  @Override
  public Path getJavaBinPath() {
    return javaBinPath;
  }
}
