package org.openRealmOfStars.ambient.connection;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.openRealmOfStars.utilities.ErrorLogger;

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
* Blindly trust certificate or with certain subject and issuer.
* NOTE THIS IS NOT SAFE METHOD TO VERIFY SERVER'S CERTIFICATE.
* THIS CODE SHOULD BE ONLY USED AGAINST SERVER YOU TRUST!
*
*/
public class BlindTrustManager implements X509TrustManager {

  /**
   * Issuer CN to trust.
   */
  private static final String TRUST_ISSUER_CN = "root-bridge";
  /**
   * BridgeId
   */
  private String bridgeId;
  /**
   * Blind trust manager no prior knowledge about server.
   */
  public BlindTrustManager() {
    bridgeId = null;
  }

  /**
   * Blind trust manager, with certain bridge id.
   * @param bridgeId where to trust.
   */
  public BlindTrustManager(final String bridgeId) {
    this.bridgeId = bridgeId;
  }
  @Override
  public void checkClientTrusted(final X509Certificate[] arg0,
      final String arg1) throws CertificateException {
    throw new CertificateException("This cannot be used for verifying"
        + " client certs.");
  }

  /**
   * Parse principal string and find certain key.
   * @param principal String to parse
   * @param key Key to find
   * @return Value or null.
   */
  public String parsePrincipal(final String principal, final String key) {
    String[] parts = principal.split(",");
    for (String line : parts) {
      String[] lineParts = line.split("=");
      if (lineParts.length == 2 && lineParts[0].equalsIgnoreCase(key)) {
        return lineParts[1];
      }
    }
    return null;
  }
  @Override
  public void checkServerTrusted(final X509Certificate[] arg0,
      final String arg1) throws CertificateException {
    ErrorLogger.debug("Received server certificate...");
    // Check that certificate is currently in valid period.
    arg0[0].checkValidity();
    String subjectCn = parsePrincipal(arg0[0].getSubjectDN().toString(), "CN");
    if (bridgeId != null) {
      if (subjectCn == null) {
        throw new CertificateException("Server has invalid certificate!");
      }
      if (!bridgeId.equals(subjectCn)) {
        throw new CertificateException("Server has invalid certificate!");
      }
    } else if (subjectCn != null) {
      bridgeId = subjectCn;
    }
    String issuerCn = parsePrincipal(arg0[0].getIssuerDN().toString(), "CN");
    if (issuerCn == null) {
      throw new CertificateException("Server has invalid certificate!");
    }
    if (!TRUST_ISSUER_CN.equals(issuerCn)) {
      throw new CertificateException("Server has invalid certificate!");
    }
  }

  /**
   * Get Bridge id. This is available after certificate check
   * or construction made with giving bridge id.
   * @return Bridge id
   */
  public String getBridgeId() {
    return bridgeId;
  }
  @Override
  public X509Certificate[] getAcceptedIssuers() {
    // Bridges have self-signed certs, no accepted issuers.
    return null;
  }

}
