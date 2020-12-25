package org.openRealmOfStars.ambient.connection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

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
* Hostname Verifier.
* NOTE THIS IS NOT SAFEWAY TO DO THIS!
*
*/
public class BridgeHostnameVerifier implements HostnameVerifier {

  /**
   * Bridge host name.
   */
  private String bridgeHostName;
  /**
   * Constructor for Bridge hostname verifier.
   * @param bridgeHostName Bridge's hostname
   */
  public BridgeHostnameVerifier(final String bridgeHostName) {
    this.bridgeHostName = bridgeHostName;
  }

  @Override
  public boolean verify(final String arg0, final SSLSession arg1) {
    if (arg0.equals(arg1.getPeerHost()) && arg0.equals(bridgeHostName)) {
      return true;
    }
    return false;
  }

}
