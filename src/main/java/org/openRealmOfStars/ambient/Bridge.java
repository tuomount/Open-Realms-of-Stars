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
import org.openRealmOfStars.utilities.json.JsonParser;
import org.openRealmOfStars.utilities.json.JsonStream;
import org.openRealmOfStars.utilities.json.values.JsonRoot;
import org.openRealmOfStars.utilities.json.values.JsonValue;
import org.openRealmOfStars.utilities.json.values.Member;
import org.openRealmOfStars.utilities.json.values.ObjectValue;
import org.openRealmOfStars.utilities.json.values.StringValue;
import org.openRealmOfStars.utilities.json.values.ValueType;

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
   * Bridge status.
   */
  private BridgeStatusType status;
  /**
   * Last Error message.
   */
  private String lastErrorMsg;
  /**
   * Next bridge command
   */
  private BridgeCommandType nextCommand;
  /**
   * Bridge thread.
   */
  private BridgeThread bridgeThread;
  /**
   * Constructs new Hue bridge controller. Not authenticated yet,
   * so no username set.
   * @param hostname Hostname or IP address.
   */
  public Bridge(final String hostname) {
    this.setHostname(hostname);
    setUsername(null);
    status = BridgeStatusType.NOT_CONNECTED;
    lastErrorMsg = "";
    setNextCommand(null);
    bridgeThread = new BridgeThread(this);
  }

  /**
   * Get Last Error Message.
   * @return Error as string.
   */
  public synchronized String getLastErrorMsg() {
    return lastErrorMsg;
  }
  /**
   * Set Last error messagge.
   * @param error Error as a string.
   */
  public synchronized void setLastErrorMsg(final String error) {
    lastErrorMsg = error;
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
    status = BridgeStatusType.REGISTERING;
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
    System.out.println("Code:" + connection.getResponseCode());
    System.out.println(str);
    JsonStream stream = new JsonStream(buf);
    JsonParser parser = new JsonParser();
    JsonRoot jsonRoot = parser.parseJson(stream);
    Member member = jsonRoot.findFirst("error");
    if (member == null) {
      member = jsonRoot.findFirst("success");
      if (member != null) {
        member = jsonRoot.findFirst("username");
        if (member != null) {
          JsonValue value = member.getValue();
          if (value.getType() == ValueType.STRING) {
            StringValue strValue = (StringValue) value;
            username = strValue.getValue();
            status = BridgeStatusType.REGISTERED;
          }
        } else {
          status = BridgeStatusType.NOT_CONNECTED;
          lastErrorMsg = "No username received.";
        }
      } else {
        status = BridgeStatusType.ERROR;
        lastErrorMsg = "Unknown error happened.";
      }
    } else {
      status = BridgeStatusType.ERROR;
      member = jsonRoot.findFirst("description");
      if (member != null) {
        JsonValue value = member.getValue();
        if (value.getType() == ValueType.STRING) {
          StringValue strValue = (StringValue) value;
          lastErrorMsg = "Remember press sync button"
              + " before clicking register. "
              + strValue.getValue();

        } else {
          lastErrorMsg = "Remember press sync button"
              + " before clicking register. "
              + member.getValue().getValueAsString();
        }
      }
    }
    is.close();
  }

  /**
   * Method for testing connection OROS to bridge.
   * @throws IOException If something goes wrong.
   */
  public void testConnection() throws IOException {
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
    URL url = new URL("https://" + hostname + "/api/" + username + "/lights");
    status = BridgeStatusType.CONNECTING;
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setSSLSocketFactory(sslContext.getSocketFactory());
    connection.setHostnameVerifier(new BridgeHostnameVerifier(hostname));
    connection.setRequestMethod("GET");
    InputStream is = connection.getInputStream();
    byte[] buf = IOUtilities.readAll(is);
    String str = new String(buf, StandardCharsets.UTF_8);
    System.out.println("Code:" + connection.getResponseCode());
    System.out.println(str);
    JsonStream stream = new JsonStream(buf);
    JsonParser parser = new JsonParser();
    JsonRoot jsonRoot = parser.parseJson(stream);
    Member member = jsonRoot.findFirst("1");
    if (member != null) {
      status = BridgeStatusType.CONNECTED;
    } else {
      status = BridgeStatusType.ERROR;
      lastErrorMsg = "Could not connected.";
    }
    is.close();
  }

  /**
   * Get Bridge status.
   * @return Bridge status
   */
  public BridgeStatusType getStatus() {
    return status;
  }

  /**
   * Change bridge status.
   * @param newStatus for bridge.
   */
  public void setStatus(final BridgeStatusType newStatus) {
    status = newStatus;
  }
  /**
   * Get next command
   * @return the nextCommand
   */
  public synchronized BridgeCommandType getNextCommand() {
    return nextCommand;
  }

  /**
   * Set next command. Will also launch new thread if control thread
   *  is not running.
   * @param nextCommand the nextCommand to set
   */
  public synchronized void setNextCommand(
      final BridgeCommandType nextCommand) {
    this.nextCommand = nextCommand;
    if (this.nextCommand != null) {
      if (!bridgeThread.isRunning() && bridgeThread.isStarted()) {
        bridgeThread = new BridgeThread(this);
      }
      if (!bridgeThread.isRunning() && !bridgeThread.isStarted()) {
        bridgeThread.start();
      }
    }
  }
}
