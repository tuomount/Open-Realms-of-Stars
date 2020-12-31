package org.openRealmOfStars.ambient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.openRealmOfStars.ambient.connection.BlindTrustManager;
import org.openRealmOfStars.ambient.connection.BridgeHostnameVerifier;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;
import org.openRealmOfStars.utilities.json.JsonParser;
import org.openRealmOfStars.utilities.json.JsonStream;
import org.openRealmOfStars.utilities.json.values.BooleanValue;
import org.openRealmOfStars.utilities.json.values.JsonRoot;
import org.openRealmOfStars.utilities.json.values.JsonValue;
import org.openRealmOfStars.utilities.json.values.Member;
import org.openRealmOfStars.utilities.json.values.NumberValue;
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
   * Effect name warm white.
   */
  public static final String EFFECT_WARM_WHITE = "Warm white";
  /**
   * Effect name darkest.
   */
  public static final String EFFECT_DARKEST = "Darkest";
  /**
   * Effect name Red alert.
   */
  public static final String EFFECT_RED_ALERT = "Red alert!";
  /**
   * Effect name Yellow alert.
   */
  public static final String EFFECT_YELLOW_ALERT = "Yellow alert!";
  /**
   * Effect name Nuke.
   */
  public static final String EFFECT_NUKE = "Nuke";
  /**
   * Effect name Nuke.
   */
  public static final String EFFECT_FLOAT_IN_SPACE = "Float in space";
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
   * Lights in bridge.
   */
  private ArrayList<Light> lights;

  /**
   * Left light name.
   */
  private String leftLightName;
  /**
   * Right light name.
   */
  private String rightLightName;
  /**
   * Center light name.
   */
  private String centerLightName;
  /**
   * Phase value for effects. This value is in degrees.
   */
  private double phase;
  /**
   * Effect intense value;
   */
  private int intense;
  /**
   * Has effect ended so that BridgeThread can stop running the effect.
   */
  private boolean effectEnded;
  /**
   * Boolean if lights are enabled.
   */
  private boolean lightsEnabled;
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
    lights = null;
    leftLightName = "none";
    rightLightName = "none";
    centerLightName = "none";
    intense = 3;
    lightsEnabled = true;
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
    ErrorLogger.debug(root.getValueAsString());
    InputStream is = connection.getInputStream();
    byte[] buf = IOUtilities.readAll(is);
    String str = new String(buf, StandardCharsets.UTF_8);
    ErrorLogger.debug("Code:" + connection.getResponseCode());
    ErrorLogger.debug(str);
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
    ErrorLogger.debug("Code:" + connection.getResponseCode());
    ErrorLogger.debug(str);
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
   * Updates light values on bridge
   * @param light Light to update on bridge
   * @throws IOException If something goes wrong.
   */
  public void updateLight(final Light light) throws IOException {
    ObjectValue json = light.updateLampJson();
    if (json.getMembers().size() == 0 || !areLightsEnabled()) {
      // No change for selected light or lights are disabled
      return;
    }
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
    URL url = new URL("https://" + hostname + "/api/" + username + "/lights/"
        + light.getName() + "/state");
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setSSLSocketFactory(sslContext.getSocketFactory());
    connection.setHostnameVerifier(new BridgeHostnameVerifier(hostname));
    connection.setDoOutput(true);
    connection.setRequestMethod("PUT");
    connection.getOutputStream().write(json.getValueAsString().getBytes(
        StandardCharsets.UTF_8));
    ErrorLogger.debug("URL: " + url.toString());
    ErrorLogger.debug(json.getValueAsString());
    InputStream is = connection.getInputStream();
    byte[] buf = IOUtilities.readAll(is);
    String str = new String(buf, StandardCharsets.UTF_8);
    ErrorLogger.debug("Code:" + connection.getResponseCode());
    ErrorLogger.debug(str);
    is.close();
    status = BridgeStatusType.CONNECTED;
  }

  /**
   * Get Lights in array list.
   * @return Array list of lights.
   */
  public ArrayList<Light> getLigths() {
    return lights;
  }

  /**
   * Make light effect.
   * @param light Light
   * @param hue Hue, -1 means no change to current value
   * @param sat Saturation, -1 means no change to current value
   * @param bri Brightness, -1 means no change to current value
   */
  public void makeLightEffect(final Light light, final int hue,
      final int sat, final int bri) {
    if (light != null) {
      light.setOn(true);
      if (hue != -1) {
        light.setHue(hue);
      }
      if (sat != -1) {
        light.setSat(sat);
      }
      if (bri != -1) {
        light.setBri(bri);
      }
      try {
        updateLight(light);
      } catch (IOException e) {
        // Ignore errors
        setStatus(BridgeStatusType.CONNECTED);
      }
    }
  }
  /**
   * Changes all light for warm white light.
   */
  public void effectWarmWhiteLight() {
    Light light = getLeftLight();
    makeLightEffect(light, 4000, 10, 255);
    light = getRightLight();
    makeLightEffect(light, 4000, 10, 255);
    light = getCenterLight();
    makeLightEffect(light, 4000, 10, 255);
  }
  /**
   * Changes all light for darkest value
   */
  public void effectDarkest() {
    Light light = getLeftLight();
    makeLightEffect(light, -1, -1, 0);
    light = getRightLight();
    makeLightEffect(light, -1, -1, 0);
    light = getCenterLight();
    makeLightEffect(light, -1, -1, 0);
  }

  /**
   * Makes lights blinking in certain hue.
   * @param hue Hue for blinking light
   */
  public void effectAlert(final int hue) {
    int center = 128;
    int amp = 127;
    int inc = 90;
    if (intense == 4) {
      amp = 100;
      center = 155;
      inc = 70;
    }
    if (intense == 3) {
      amp = 80;
      center = 175;
      inc = 45;
    }
    if (intense == 2) {
      amp = 60;
      center = 195;
      inc = 30;
    }
    if (intense == 1) {
      amp = 50;
      center = 205;
      inc = 30;
    }
    phase = phase + inc;
    if (phase > 359) {
      phase = phase - 359;
    }
    int bri = (int) (Math.sin(Math.toRadians(phase)) * amp);
    bri = bri + center;
    Light light = getLeftLight();
    makeLightEffect(light, hue, 255, bri);
    bri = (int) (Math.cos(Math.toRadians(phase)) * amp);
    bri = bri + center;
    light = getRightLight();
    makeLightEffect(light, hue, 255, bri);
    bri = (int) (Math.sin(Math.toRadians(phase + 45)) * amp);
    bri = bri + center;
    light = getCenterLight();
    makeLightEffect(light, hue, 128, bri);
  }

  /**
   * Initialize nuke effect.
   */
  public void effectInitNuke() {
    phase = 90;
    effectEnded = false;
    Light light = getLeftLight();
    makeLightEffect(light, 48000, 20, 255);
    light = getRightLight();
    makeLightEffect(light, 20000, 10, 255);
    light = getCenterLight();
    makeLightEffect(light, 50000, 30, 255);
  }

  /**
   * Fades down nuclear blast effect
   */
  public void effectFadeNuke() {
    int center = 128;
    int amp = 127;
    int inc = 30;
    phase = phase + inc;
    if (phase > 270) {
      effectEnded = true;
      return;
    }
    int bri = (int) (Math.sin(Math.toRadians(phase)) * amp);
    bri = bri + center;
    Light light = getLeftLight();
    makeLightEffect(light, 48000, 20, bri);
    bri = (int) (Math.sin(Math.toRadians(phase)) * amp);
    bri = bri + center;
    light = getRightLight();
    makeLightEffect(light, 20000, 10, bri);
    bri = (int) (Math.sin(Math.toRadians(phase)) * amp);
    bri = bri + center;
    light = getCenterLight();
    makeLightEffect(light, 50000, 30, bri);
  }

  /**
   * Makes blueish space floating effect
   */
  public void effectBlueSpace() {
    int center = 43500;
    int amp = 6500;
    int inc = 3;
    int trickle = 100;
    if (intense == 4) {
      trickle = 80;
      inc = 2;
    }
    if (intense == 3) {
      trickle = 60;
      inc = 2;
    }
    if (intense == 2) {
      trickle = 40;
      inc = 2;
    }
    if (intense == 1) {
      trickle = 40;
      inc = 1;
    }
    phase = phase + inc;
    if (phase > 359) {
      phase = phase - 359;
    }
    int hue = (int) (Math.sin(Math.toRadians(phase)) * amp);
    hue = hue + center;
    int bri = 150 + (int) (Math.sin(Math.toRadians(phase * 2)) * trickle);
    Light light = getLeftLight();
    makeLightEffect(light, hue, 255, bri);
    hue = (int) (Math.cos(Math.toRadians(phase)) * amp);
    hue = hue + center;
    bri = 150 + (int) (Math.cos(Math.toRadians(phase * 2)) * trickle);
    light = getRightLight();
    makeLightEffect(light, hue, 255, bri);
    hue = (int) (Math.sin(Math.toRadians(phase + 45)) * amp);
    hue = hue + center;
    bri = 150 + (int) (Math.sin(Math.toRadians(phase * 2)) * trickle);
    light = getCenterLight();
    makeLightEffect(light, hue, 255, bri);
  }

  /**
   * Method for testing connection OROS to bridge.
   * @throws IOException If something goes wrong.
   */
  public void updateAllLights() throws IOException {
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
    setStatus(BridgeStatusType.BUSY);
    URL url = new URL("https://" + hostname + "/api/" + username + "/lights");
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setSSLSocketFactory(sslContext.getSocketFactory());
    connection.setHostnameVerifier(new BridgeHostnameVerifier(hostname));
    connection.setRequestMethod("GET");
    InputStream is = connection.getInputStream();
    byte[] buf = IOUtilities.readAll(is);
    String str = new String(buf, StandardCharsets.UTF_8);
    ErrorLogger.debug("Code:" + connection.getResponseCode());
    ErrorLogger.debug(str);
    JsonStream stream = new JsonStream(buf);
    JsonParser parser = new JsonParser();
    JsonRoot jsonRoot = parser.parseJson(stream);
    is.close();
    lights = new ArrayList<>();
    // Officially Hue bridge supports 50 lights, but let's be on safe side.
    for (int i = 0; i < 255; i++) {
      String lightName = String.valueOf(i);
      Member member = jsonRoot.findFirst(lightName);
      if (member != null && member.getValue().getType() == ValueType.OBJECT) {
        Light light = new Light(member.getName());
        ObjectValue lightNum = (ObjectValue) member.getValue();
        member = lightNum.findFirst("name");
        if (member.getValue().getType() == ValueType.STRING) {
          StringValue strName = (StringValue) member.getValue();
          light.setHumanReadablename(strName.getValue());
        } else {
          ErrorLogger.debug("No name found for light " + lightName + ".");
          continue;
        }
        member = lightNum.findFirst("state");
        if (member != null
            && member.getValue().getType() == ValueType.OBJECT) {
          ObjectValue state = (ObjectValue) member.getValue();
          member = state.findFirst("on");
          if (member != null
              && member.getValue().getType() == ValueType.BOOLEAN) {
            BooleanValue value = (BooleanValue) member.getValue();
            light.setOn(value.getValue());
          } else {
            ErrorLogger.debug("No on found for light " + lightName + ".");
            continue;
          }
          member = state.findFirst("bri");
          if (member != null
              && member.getValue().getType() == ValueType.NUMBER) {
            NumberValue value = (NumberValue) member.getValue();
            light.setBri(value.getValueAsInt());
          } else {
            ErrorLogger.debug("No bri found for light " + lightName + ".");
            continue;
          }
          member = state.findFirst("sat");
          if (member != null
              && member.getValue().getType() == ValueType.NUMBER) {
            NumberValue value = (NumberValue) member.getValue();
            light.setSat(value.getValueAsInt());
          } else {
            ErrorLogger.debug("No sat found for light " + lightName + ".");
            // We only support full color lights
            continue;
          }
          member = state.findFirst("hue");
          if (member != null
              && member.getValue().getType() == ValueType.NUMBER) {
            NumberValue value = (NumberValue) member.getValue();
            light.setHue(value.getValueAsInt());
          } else {
            ErrorLogger.debug("No hue found for light " + lightName + ".");
            // We only support full color lights
            continue;
          }
        }
        lights.add(light);
      }
    }
    setStatus(BridgeStatusType.CONNECTED);
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

  /**
   * Get left light actual light object
   * @return Light object or null if not found or not defined.
   */
  public Light getLeftLight() {
    if (getLeftLightName() == null
        || getLeftLightName().equals("none") || getLeftLightName().isEmpty()) {
      return null;
    }
    for (Light light : lights) {
      if (light.getHumanReadablename().equals(getLeftLightName())) {
        return light;
      }
    }
    return null;
  }
  /**
   * Get center light actual light object
   * @return Light object or null if not found or not defined.
   */
  public Light getCenterLight() {
    if (getCenterLightName() == null
        || getCenterLightName().equals("none")
        || getCenterLightName().isEmpty()) {
      return null;
    }
    for (Light light : lights) {
      if (light.getHumanReadablename().equals(getCenterLightName())) {
        return light;
      }
    }
    return null;
  }
  /**
   * Get right light actual light object
   * @return Light object or null if not found or not defined.
   */
  public Light getRightLight() {
    if (getRightLightName() == null
        || getRightLightName().equals("none")
        || getRightLightName().isEmpty()) {
      return null;
    }
    for (Light light : lights) {
      if (light.getHumanReadablename().equals(getRightLightName())) {
        return light;
      }
    }
    return null;
  }
  /**
   * Get left lightname
   * @return the leftLightName
   */
  public String getLeftLightName() {
    return leftLightName;
  }

  /**
   * Set left lightname.
   * @param leftLightName the leftLightName to set
   */
  public void setLeftLightName(final String leftLightName) {
    this.leftLightName = leftLightName;
  }

  /**
   * Get right light name.
   * @return the rightLightName
   */
  public String getRightLightName() {
    return rightLightName;
  }

  /**
   * Set right light name.
   * @param rightLightName the rightLightName to set
   */
  public void setRightLightName(final String rightLightName) {
    this.rightLightName = rightLightName;
  }

  /**
   * Get center light name.
   * @return the centerLightName
   */
  public String getCenterLightName() {
    return centerLightName;
  }

  /**
   * Set center light name.
   * @param centerLightName the centerLightName to set
   */
  public void setCenterLightName(final String centerLightName) {
    this.centerLightName = centerLightName;
  }

  /**
   * Get Effect intense value
   * @return the intense
   */
  public int getIntense() {
    return intense;
  }

  /**
   * Set effect intense value.
   * @param intense the intense to set
   */
  public void setIntense(final int intense) {
    this.intense = intense;
  }

  /**
   * Has current effect ended?
   * @return True if effect has ended.
   */
  public boolean isEffectEnded() {
    return effectEnded;
  }

  /**
   * Are lights enabled?
   * @return true if lights are enabled
   */
  public boolean areLightsEnabled() {
    return lightsEnabled;
  }

  /**
   * Set lights enabled or not.
   * @param lightsEnabled the lightsEnabled to set
   */
  public void setLightsEnabled(final boolean lightsEnabled) {
    this.lightsEnabled = lightsEnabled;
  }
}
