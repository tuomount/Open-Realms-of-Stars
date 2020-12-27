package org.openRealmOfStars.ambient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.openRealmOfStars.ambient.connection.BlindTrustManager;
import org.openRealmOfStars.ambient.connection.BridgeHostnameVerifier;
import org.openRealmOfStars.utilities.IOUtilities;
import org.openRealmOfStars.utilities.json.values.ObjectValue;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* Hue Bridge
*
*/
public class Bridge {

  /**
   * Name for contact Hue bridge.
   */
  public static final String DEVICE_TYPE = "OROS AMBIENT LIGHTS";
  /**
   * Default Username for asking access.
   */
  public static final String DEFAULT_USERNAME = "OrosAmbientLights";
  /**
   * Bridge username
   */
  private String username;

  /**
   * Bridge hostname or ip address.
   */
  private String hostname;

  /**
   * Constructs new Hue bridge controller. Not authenticated yet,
   * so no username set.
   * @param hostname Hostname or IP address.
   */
  public Bridge(final String hostname) {
    this.setHostname(hostname);
    setUsername(null);
  }

  /**
   * Get Username for the bridge.
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Set Username for the bridge.
   * @param username the username to set
   */
  public void setUsername(final String username) {
    this.username = username;
  }

  /**
   * Get the hostname or IP address for the bridge.
   * @return the hostname IP address or hostname.
   */
  public String getHostname() {
    return hostname;
  }

  /**
   * Set Hostname for bridge.
   * @param hostname the hostname to set
   */
  public void setHostname(final String hostname) {
    this.hostname = hostname;
  }

  /**
   * Method for registering OROS to bridge.
   * @throws IOException If something goes wrong.
   */
  public void register() throws IOException {
    SSLContext sslContext;
    try {
      sslContext = SSLContext.getInstance("TLSv1.2");
    } catch (NoSuchAlgorithmException e) {
      throw new IOException("Missing algorithm. " + e.getMessage());
    }
    TrustManager[] trustManagers = new TrustManager[1];
    trustManagers[0] = new BlindTrustManager();
    try {
      sslContext.init(null, trustManagers, new SecureRandom());
    } catch (KeyManagementException e) {
      throw new IOException("Error in key management. " + e.getMessage());
    }
    URL url = new URL("https://" + hostname + "/api");
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setSSLSocketFactory(sslContext.getSocketFactory());
    connection.setHostnameVerifier(new BridgeHostnameVerifier(hostname));
    connection.setDoOutput(true);
    connection.setRequestMethod("POST");
    ObjectValue root = new ObjectValue();
    root.addStringMember("devicetype", DEVICE_TYPE);
    connection.getOutputStream().write(root.getValueAsString().getBytes(
        StandardCharsets.UTF_8));
    System.out.println(root.getValueAsString());
    InputStream is = connection.getInputStream();
    byte[] buf = IOUtilities.readAll(is);
    String str = new String(buf, StandardCharsets.UTF_8);
    System.out.println(str);
    is.close();
  }
}
