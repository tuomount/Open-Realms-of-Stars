package org.openRealmOfStars.ambient;

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
* Bridge status type
*
*/
public enum BridgeStatusType {
  /**
   * Not connected to bridge.
   */
  NOT_CONNECTED,
  /**
   * Registering, but not yet registered
   */
  REGISTERING,
  /**
   * Registered and ready for connecting.
   */
  REGISTERED,
  /**
   * Connecting, but not yet connected.
   */
  CONNECTING,
  /**
   * Connected and ready for action
   */
  CONNECTED,
  /**
   * Connected but doing command
   */
  BUSY,
  /**
   * Error has occured
   */
  ERROR;
}
