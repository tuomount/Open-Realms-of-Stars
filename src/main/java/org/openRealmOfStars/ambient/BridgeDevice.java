package org.openRealmOfStars.ambient;

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
* UPnP Device of Hue Bridge
*
*/
public class BridgeDevice {

  /**
   * Bridge ID.
   */
  private String bridgeId;
  /**
   * Bridge Address.
   */
  private String bridgeAddress;

  /**
   * Constructor for bridge device
   * @param id Bridge Id
   * @param address Bridge address
   */
  public BridgeDevice(final String id, final String address) {
    bridgeId = id;
    bridgeAddress = address;
  }

  /**
   * Get Bridge Id.
   * @return Bridge Id.
   */
  public String getId() {
    return bridgeId;
  }

  /**
   * Get Bridge address.
   * @return Bridge address.
   */
  public String getAddress() {
    return bridgeAddress;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof BridgeDevice) {
      BridgeDevice another = (BridgeDevice) obj;
      if (this.bridgeId.equals(another.bridgeId)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return bridgeAddress + ":" + bridgeId;
  }

  @Override
  public int hashCode() {
    return bridgeId.hashCode() * 47;
  }

}
