package org.openRealmOfStars.ambient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.openRealmOfStars.utilities.ErrorLogger;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
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
* UPnP Service Discovery
*
*/
public class ServiceDiscovery {

  /**
   * Header for hue-bridgeid
   */
  private static final String BRIDGE_ID = "hue-bridgeid";
  /**
   * SSDP Discover message.
   */
  private static final String DISCOVER_MESSAGE = "M-SEARCH * HTTP/1.1\r\n"
      + "HOST: 239.255.255.250:1900\r\n"
      + "MAN: \"ssdp:discover\"\r\n"
      + "MX: 1\r\n"
      + "ST: upnp:rootdevice\r\n\r\n";

  /**
   * Multicast socket for SSDP.
   */
  private MulticastSocket socket;
  /**
   * Multicast address
   */
  private InetAddress address;
  /**
   * Constructor for Service Discovery.
   * @throws IOException If joining to multicast group fails.
   */
  public ServiceDiscovery() throws IOException {
    // Ugly way to avoid warning of fixed IP
    // Multicast IP address is fixed though.
    StringBuilder addressBuilder = new StringBuilder();
    addressBuilder.append("239");
    addressBuilder.append(".");
    addressBuilder.append("255");
    addressBuilder.append(".");
    addressBuilder.append("255");
    addressBuilder.append(".");
    addressBuilder.append("250");
    address = InetAddress.getByName(addressBuilder.toString());
    socket = new MulticastSocket();
    socket.joinGroup(address);
    socket.setSoTimeout(500);
  }

  /**
   * Parse Bridge Id from payload.
   * @param payload Payload to parse
   * @return Bridge id or null.
   */
  private String parseBridgeId(final String payload) {
    if (payload.contains("hue-bridgeid:")) {
      int index = payload.indexOf(BRIDGE_ID);
      if (index > -1) {
        int endOfLine = payload.indexOf("\r\n", index);
        if (endOfLine > -1) {
          String value = payload.substring(index + BRIDGE_ID.length() + 1,
              endOfLine);
          value = value.trim();
          return value;
        }
      }
    }
    return null;
  }

  /**
   * Discover all available HUE bridges.
   * @return Array of bridges.
   */
  public BridgeDevice[] discoverBridges() {
    byte[] buffer = DISCOVER_MESSAGE.getBytes(StandardCharsets.US_ASCII);
    DatagramPacket datagram = new DatagramPacket(buffer, buffer.length,
        address, 1900);
    try {
      socket.send(datagram);
    } catch (IOException e) {
      ErrorLogger.log(e);
      return new BridgeDevice[0];
    }
    boolean receiveDone = false;
    buffer = new byte[65535];
    long start = System.currentTimeMillis();
    ArrayList<BridgeDevice> devices = new ArrayList<>();
    do {
      long current = System.currentTimeMillis();
      if (current - start > 5000) {
        receiveDone = true;
        break;
      }
      datagram = new DatagramPacket(buffer, buffer.length);
      try {
        socket.receive(datagram);
        InetAddress receiveAddr = datagram.getAddress();
        String bridgeAddr = receiveAddr.getHostAddress();
        String str = new String(datagram.getData(), StandardCharsets.US_ASCII);
        String id = parseBridgeId(str);
        if (id != null) {
          BridgeDevice device = new BridgeDevice(id, bridgeAddr);
          boolean found = false;
          for (BridgeDevice listDev : devices) {
            if (listDev.equals(device)) {
              found = true;
            }
          }
          if (!found) {
            devices.add(device);
          }
        }
      } catch (IOException e) {
        ErrorLogger.debug(e.getMessage());
      }
    } while (!receiveDone);
    return devices.toArray(new BridgeDevice[devices.size()]);
  }

  /**
   * Discover one specific bridge
   * @param idToSearch Bridge Id.
   * @return BridgeDevice or null if not found
   */
  public BridgeDevice discoverBridge(final String idToSearch) {
    byte[] buffer = DISCOVER_MESSAGE.getBytes(StandardCharsets.US_ASCII);
    DatagramPacket datagram = new DatagramPacket(buffer, buffer.length,
        address, 1900);
    try {
      socket.send(datagram);
    } catch (IOException e) {
      ErrorLogger.log(e);
      return null;
    }
    boolean receiveDone = false;
    buffer = new byte[65535];
    long start = System.currentTimeMillis();
    do {
      long current = System.currentTimeMillis();
      if (current - start > 5000) {
        receiveDone = true;
        break;
      }
      datagram = new DatagramPacket(buffer, buffer.length);
      try {
        socket.receive(datagram);
        InetAddress receiveAddr = datagram.getAddress();
        String bridgeAddr = receiveAddr.getHostAddress();
        String str = new String(datagram.getData(), StandardCharsets.US_ASCII);
        String id = parseBridgeId(str);
        if (id != null && id.equals(idToSearch)) {
          BridgeDevice device = new BridgeDevice(id, bridgeAddr);
          return device;
        }
      } catch (IOException e) {
        ErrorLogger.debug(e.getMessage());
      }
    } while (!receiveDone);
    return null;
  }

}
