package org.openRealmOfStars.ambient;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020-2021 Tuomo Untinen
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openRealmOfStars.ambient.connection.BlindTrustManager;
import org.openRealmOfStars.ambient.connection.BridgeHostnameVerifier;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
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
   * Effect Green console
   */
  public static final String EFFECT_GREEN_CONSOLE = "Green Console";
  /**
   * Effect Space Console
   */
  public static final String EFFECT_SPACE_CONSOLE = "Space Console";
  /**
   * Effect Bright Cyan
   */
  public static final String EFFECT_BRIGHT_CYAN = "Bright cyan";
  /**
   * Effect Blueish white
   */
  public static final String EFFECT_BLUEISH_WHITE = "Blueish white";
  /**
   * Effect Grey blue light
   */
  public static final String EFFECT_GREY_BLUE = "GreyBlue";
  /**
   * Effect dark orange
   */
  public static final String EFFECT_DARK_ORANGE = "Dark orange";
  /**
   * Effect dark red
   */
  public static final String EFFECT_DARK_RED = "Dark red";
  /**
   * Effect purple dream
   */
  public static final String EFFECT_PURPLE_DREAM = "Purple dream";
  /**
   * Effect fade in
   */
  public static final String EFFECT_FADE_IN = "Fade in";
  /**
   * Effect orange blink
   */
  public static final String EFFECT_ORANGE_BLINK = "Orange Blink";
  /**
   * Effect orange blink
   */
  public static final String EFFECT_ORANGE_BLUE = "Orange Blue";
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
   * Bridge id.
   */
  private String id;
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
    lightsEnabled = false;
    id = null;
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
   * Set Username for the bridge. Empty username set username to null.
   * @param username the username to set
   */
  public void setUsername(final String username) {
    if (username == null || username.isEmpty()) {
      this.username = null;
    } else {
      this.username = username;
    }
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
   * Locate Bridge using SSDP and bridge id.
   * This will update hostname according the match.
   * @return true if bridge war located.
   */
  public boolean locateBridge() {
    if (id == null) {
      // cannot locate if ID is null.
      return false;
    }
    try {
      ServiceDiscovery discovery = new ServiceDiscovery();
      BridgeDevice device = discovery.discoverBridge(id);
      if (device != null) {
        hostname = device.getAddress();
        return true;
      }
    } catch (IOException e) {
      ErrorLogger.log(e);
    }
    return false;
  }

  /**
   * Write output to bridge.
   * @param str String content to write
   * @param connection HTTPSURLConnection
   */
  private void writeOutput(final String str,
      final HttpsURLConnection connection) {
    try (OutputStream os = connection.getOutputStream()) {
      os.write(str.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      ErrorLogger.debug("Exception:" + e.getMessage());
      status = BridgeStatusType.ERROR;
      lastErrorMsg = "Write failed.";
    }
  }
  /**
   * Read Input from HTTPSURL Connection
   * @param connection HTTPS URL COnnection
   * @return bytearray or null
   */
  private byte[] readInput(final HttpsURLConnection connection) {
    byte[] buf = null;
    try (InputStream is = connection.getInputStream()) {
      buf = is.readAllBytes();
    } catch (IOException e) {
      try {
        ErrorLogger.debug("Code:" + connection.getResponseCode());
      } catch (IOException e1) {
        // DO NOTHING
        ErrorLogger.debug("Exception:" + e1.getMessage());
      }
      ErrorLogger.debug("Exception:" + e.getMessage());
      status = BridgeStatusType.ERROR;
      lastErrorMsg = "Read failed.";
    }
    return buf;
  }

  /**
   * Get Object from byte array.
   * @param buf Byte Array
   * @return Object JSONObject or JSONArray or someother JSON
   *                or null if parsing fails.
   */
  private static Object parseJsonRoot(final byte[] buf) {
    String jsonString = new String(buf, StandardCharsets.UTF_8);
    try {
      JSONTokener tokener = new JSONTokener(jsonString);
      return tokener.nextValue();
    } catch (JSONException e) {
      return null;
    }
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
    if (getId() != null) {
      trustManagers[0] = new BlindTrustManager(getId());
    } else {
      trustManagers[0] = new BlindTrustManager();
    }
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
    JSONObject root = new JSONObject();
    root.put("devicetype", DEVICE_TYPE);
    String temp = root.toString();
    writeOutput(temp, connection);
    ErrorLogger.debug(temp);
    byte[] buf = readInput(connection);
    String str = new String(buf, StandardCharsets.UTF_8);
    if (buf != null) {
      ErrorLogger.debug(str);
      Object jsonRoot = parseJsonRoot(buf);
      if (jsonRoot instanceof JSONObject) {
        JSONObject json = (JSONObject) jsonRoot;
        String successStr = json.optString("success");
        if (successStr != null) {
          String usernameStr = json.optString("username");
          if (usernameStr != null) {
            username = usernameStr;
            status = BridgeStatusType.REGISTERED;
            setLightsEnabled(true);
          } else {
            status = BridgeStatusType.NOT_CONNECTED;
            lastErrorMsg = "No username received.";
          }
        } else {
          status = BridgeStatusType.ERROR;
          lastErrorMsg = "Unknown error happened.";
        }
      } else if (jsonRoot instanceof JSONArray) {
        status = BridgeStatusType.ERROR;
        JSONArray jsonArray = (JSONArray) jsonRoot;
        JSONObject jsonError = jsonArray.optJSONObject(0);
        if (jsonError == null) {
          status = BridgeStatusType.ERROR;
          lastErrorMsg = "No JSON Error.";
          return;
        }
        JSONObject jsonErrorValue = jsonError.getJSONObject("error");
        if (jsonErrorValue != null) {
          String descStr = jsonErrorValue.optString("description");
          if (descStr != null) {
            lastErrorMsg = "Remember press sync button"
                + " before clicking register. "
                + descStr;
          } else {
            lastErrorMsg = "Remember press sync button"
                + " before clicking register. ";
          }
        } else {
          status = BridgeStatusType.ERROR;
          lastErrorMsg = "No JSON Error.";
        }
      } else {
        status = BridgeStatusType.ERROR;
        lastErrorMsg = "Unexpected JSON type";
      }
    } else {
      status = BridgeStatusType.ERROR;
      lastErrorMsg = "No payload.";
    }
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
    if (getId() != null) {
      trustManagers[0] = new BlindTrustManager(getId());
    } else {
      trustManagers[0] = new BlindTrustManager();
    }
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
    byte[] buf = readInput(connection);
    if (buf != null) {
      String str = new String(buf, StandardCharsets.UTF_8);
      ErrorLogger.debug("Code:" + connection.getResponseCode());
      ErrorLogger.debug(str);
      Object jsonRoot = parseJsonRoot(buf);
      if (jsonRoot instanceof JSONObject) {
        JSONObject json = (JSONObject) jsonRoot;
        JSONObject one = json.optJSONObject("1");
        if (one != null) {
          status = BridgeStatusType.CONNECTED;
        } else {
          status = BridgeStatusType.ERROR;
          lastErrorMsg = "Could not connected.";
        }
      } else {
        status = BridgeStatusType.ERROR;
        lastErrorMsg = "Could not connected.";
      }
    } else {
      ErrorLogger.debug("Code:" + connection.getResponseCode());
      status = BridgeStatusType.ERROR;
      lastErrorMsg = "No payload received.";
    }
  }

  /**
   * Updates light values on bridge
   * @param light Light to update on bridge
   * @throws IOException If something goes wrong.
   */
  public void updateLight(final Light light) throws IOException {
    JSONObject json = light.updateLampJson();
    if (json.isEmpty() || !areLightsEnabled()) {
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
    if (getId() != null) {
      trustManagers[0] = new BlindTrustManager(getId());
    } else {
      trustManagers[0] = new BlindTrustManager();
    }
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
    String temp = json.toString();
    writeOutput(temp, connection);
    ErrorLogger.debug("URL: " + url.toString());
    ErrorLogger.debug(json.toString());
    try (InputStream is = connection.getInputStream()) {
      byte[] buf = is.readAllBytes();
      if (buf != null) {
        String str = new String(buf, StandardCharsets.UTF_8);
        ErrorLogger.debug("Code:" + connection.getResponseCode());
        ErrorLogger.debug(str);
        status = BridgeStatusType.CONNECTED;
      } else {
        ErrorLogger.debug("Code:" + connection.getResponseCode());
      }
    } catch (IOException e) {
      ErrorLogger.debug("Code:" + connection.getResponseCode());
      ErrorLogger.debug("Exception:" + e.getMessage());
      status = BridgeStatusType.ERROR;
      lastErrorMsg = "Read failed.";
    }
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
   * Changes all light for brightCyan light.
   */
  public void effectBrightCyanLight() {
    Light light = getLeftLight();
    makeLightEffect(light, 40000, 255, 255);
    light = getRightLight();
    makeLightEffect(light, 40000, 255, 255);
    light = getCenterLight();
    makeLightEffect(light, 40000, 240, 255);
  }
  /**
   * Changes all light for blueish white light.
   */
  public void effectBlueishWhiteLight() {
    Light light = getLeftLight();
    makeLightEffect(light, 46000, 190, 128);
    light = getRightLight();
    makeLightEffect(light, 46000, 190, 128);
    light = getCenterLight();
    makeLightEffect(light, 46000, 80, 255);
  }
  /**
   * Changes all light for grey blue light.
   */
  public void effectGreyBlueLight() {
    Light light = getLeftLight();
    makeLightEffect(light, 46000, 128, 230);
    light = getRightLight();
    makeLightEffect(light, 46000, 128, 230);
    light = getCenterLight();
    makeLightEffect(light, 46000, 255, 255);
  }
  /**
   * Changes all light for Purple dream light.
   */
  public void effectPurpleDreamLight() {
    Light light = getLeftLight();
    makeLightEffect(light, 49000, 190, 160);
    light = getRightLight();
    makeLightEffect(light, 48000, 190, 160);
    light = getCenterLight();
    makeLightEffect(light, 50000, 120, 160);
  }
  /**
   * Changes all light for dark orange light.
   */
  public void effectDarkOrangeLight() {
    Light light = getLeftLight();
    makeLightEffect(light, 8000, 230, 128);
    light = getRightLight();
    makeLightEffect(light, 9000, 230, 128);
    light = getCenterLight();
    makeLightEffect(light, 8500, 200, 200);
  }
  /**
   * Changes all light for dark red light.
   */
  public void effectDarkRedLight() {
    Light light = getLeftLight();
    makeLightEffect(light, 2000, 160, 128);
    light = getRightLight();
    makeLightEffect(light, 2000, 160, 128);
    light = getCenterLight();
    makeLightEffect(light, 400, 160, 200);
  }
  /**
   * Changes all light for Green console
   */
  public void effectGreenConsole() {
    Light light = getLeftLight();
    makeLightEffect(light, 36000, 230, 255);
    light = getRightLight();
    makeLightEffect(light, 36000, 230, 255);
    light = getCenterLight();
    makeLightEffect(light, 36000, 200, 255);
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
   * Initialize fade in.
   */
  public void effectInitFadeIn() {
    phase = 180;
    effectEnded = false;
    Light light = getLeftLight();
    makeLightEffect(light, 48000, 80, 0);
    light = getRightLight();
    makeLightEffect(light, 49000, 80, 0);
    light = getCenterLight();
    makeLightEffect(light, 47000, 80, 0);
  }

  /**
   * Changes all light for dark orange light.
   */
  public void effectBlinkOrange() {
    int dark = 0;
    int bright = 240;
    if (intense == 4) {
      dark = 20;
      bright = 235;
    }
    if (intense == 3) {
      dark = 30;
      bright = 235;
    }
    if (intense == 2) {
      dark = 60;
      bright = 235;
    }
    if (intense == 1) {
      dark = 80;
      bright = 235;
    }
    if (DiceGenerator.getRandom(100) < 2) {
      Light light = getLeftLight();
      makeLightEffect(light, 8000, 230, dark);
      light = getRightLight();
      makeLightEffect(light, 9000, 230, dark);
      light = getCenterLight();
      makeLightEffect(light, 8500, 200, dark);
    } else {
      Light light = getLeftLight();
      if (DiceGenerator.getRandom(100) < 2) {
        makeLightEffect(light, 36000, 230, bright);
      } else {
        makeLightEffect(light, 8000, 230, bright);
      }
      light = getRightLight();
      if (DiceGenerator.getRandom(100) < 2) {
        makeLightEffect(light, 36000, 230, bright);
      } else {
        makeLightEffect(light, 9000, 230, bright);
      }
      light = getCenterLight();
      if (DiceGenerator.getRandom(100) < 2) {
        makeLightEffect(light, 36000, 230, bright);
      } else {
        makeLightEffect(light, 8500, 200, bright);
      }
    }
  }

  /**
   * Changes all dark orange light and blue in middle.
   */
  public void effectOrangeBlue() {
    int dark = 0;
    int bright = 240;
    if (intense == 4) {
      dark = 20;
      bright = 235;
    }
    if (intense == 3) {
      dark = 30;
      bright = 235;
    }
    if (intense == 2) {
      dark = 60;
      bright = 235;
    }
    if (intense == 1) {
      dark = 80;
      bright = 235;
    }
    Light light = getLeftLight();
    makeLightEffect(light, 8000, 230, bright);
    light = getRightLight();
    makeLightEffect(light, 9000, 230, bright);
    light = getCenterLight();
    if (DiceGenerator.getRandom(100) < 2) {
      makeLightEffect(light, 46000, 230, dark);
    } else {
      makeLightEffect(light, 46000, 230, bright);
    }
  }

  /**
   * Fade in effect
   */
  public void effectFadeIn() {
    int center = 128;
    int amp = 127;
    int inc = 30;
    phase = phase + inc;
    if (phase > 359) {
      effectEnded = true;
      phase = phase - 359;
      return;
    }
    int bri = (int) (Math.cos(Math.toRadians(phase)) * amp);
    bri = bri + center;
    Light light = getLeftLight();
    makeLightEffect(light, 48000, 140, bri);
    bri = (int) (Math.cos(Math.toRadians(phase)) * amp);
    bri = bri + center;
    light = getRightLight();
    makeLightEffect(light, 49000, 150, bri);
    bri = (int) (Math.cos(Math.toRadians(phase)) * amp);
    bri = bri + center;
    light = getCenterLight();
    makeLightEffect(light, 47000, 160, bri);
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
   * Makes left light green, and right blueish space.
   * Center is green space floating.
   * @param version Space Console type version.
   */
  public void effectSpaceConsole(final int version) {
    int center = 43500;
    int amp = 6500;
    int greenCenter = 34500;
    int greenAmp = 2000;
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
    if (version == 2) {
      light = getRightLight();
    }
    if (version == 3) {
      makeLightEffect(light, hue, 255, bri);
    } else {
      makeLightEffect(light, 35000, 230, bri);
    }
    hue = (int) (Math.cos(Math.toRadians(phase)) * amp);
    hue = hue + center;
    bri = 150 + (int) (Math.cos(Math.toRadians(phase * 2)) * trickle);
    if (version == 1 || version == 3) {
      light = getRightLight();
    } else {
      light = getLeftLight();
    }
    makeLightEffect(light, hue, 255, bri);
    hue = (int) (Math.sin(Math.toRadians(phase)) * greenAmp);
    hue = hue + greenCenter;
    bri = 150 + (int) (Math.sin(Math.toRadians(phase * 2)) * trickle);
    light = getCenterLight();
    makeLightEffect(light, hue, 222, bri);
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
    if (getId() != null) {
      trustManagers[0] = new BlindTrustManager(getId());
    } else {
      trustManagers[0] = new BlindTrustManager();
    }
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
    byte[] buf = readInput(connection);
    String str = new String(buf, StandardCharsets.UTF_8);
    ErrorLogger.debug("Code:" + connection.getResponseCode());
    ErrorLogger.debug(str);
    Object jsonRoot = parseJsonRoot(buf);
    if (!(jsonRoot instanceof JSONObject)) {
      return;
    }
    JSONObject json = (JSONObject) jsonRoot;
    lights = new ArrayList<>();
    // Officially Hue bridge supports 50 lights, but let's be on safe side.
    for (int i = 0; i < 255; i++) {
      String lightName = String.valueOf(i);
      JSONObject member = json.optJSONObject(lightName);
      if (member != null) {
        Light light = new Light(lightName);
        String nameStr = member.optString("name");
        if (nameStr != null) {
          light.setHumanReadablename(nameStr);
        } else {
          ErrorLogger.debug("No name found for light " + lightName + ".");
          continue;
        }
        JSONObject state = member.optJSONObject("state");
        if (state != null) {
          Boolean on = state.optBooleanObject("on");
          if (on != null) {
            light.setOn(on.booleanValue());
          } else {
            ErrorLogger.debug("No on found for light " + lightName + ".");
            continue;
          }
          Integer bri = state.optIntegerObject("bri");
          if (bri != null) {
            light.setBri(bri.intValue());
          } else {
            ErrorLogger.debug("No bri found for light " + lightName + ".");
            continue;
          }
          Integer sat = state.optIntegerObject("sat");
          if (sat != null) {
            light.setSat(sat.intValue());
          } else {
            ErrorLogger.debug("No sat found for light " + lightName + ".");
            // We only support full color lights
            continue;
          }
          Integer hue = state.optIntegerObject("hue");
          if (hue != null) {
            light.setHue(hue.intValue());
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

  /**
   * Get bridge id.
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Set bridge Id.
   * @param id the id to set
   */
  public void setId(final String id) {
    this.id = id;
  }
}
