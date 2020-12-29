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
   * Subject DN.
   */
  private String subjectDn;
  /**
   * Issuer DN.
   */
  private String issuerDn;
  /**
   * Blind trust manager no prior knowledge about server.
   */
  public BlindTrustManager() {
    subjectDn = null;
    issuerDn = null;
  }

  /**
   * Blind trust manager, with certain subject Dn and issuerDn.
   * @param subjectDn Subject DN
   * @param issuerDn Issuer DN
   */
  public BlindTrustManager(final String subjectDn, final String issuerDn) {
    this.subjectDn = subjectDn;
    this.issuerDn = issuerDn;
  }
  @Override
  public void checkClientTrusted(final X509Certificate[] arg0,
      final String arg1) throws CertificateException {
    throw new CertificateException("This cannot be used for verifying"
        + " client certs.");
  }

  @Override
  public void checkServerTrusted(final X509Certificate[] arg0,
      final String arg1) throws CertificateException {
    ErrorLogger.log("Received server certificate...");
    // Check that certificate is currently in valid period.
    arg0[0].checkValidity();
    if (subjectDn != null && issuerDn != null) {
      if (!subjectDn.equals(arg0[0].getSubjectDN().toString())) {
        throw new CertificateException(
            "Subject DN did not match for expected!");
      }
      if (!issuerDn.equals(arg0[0].getIssuerDN().toString())) {
        throw new CertificateException(
            "Issuer DN did not match for expected!");
      }
    } else {
      subjectDn = arg0[0].getSubjectDN().toString();
      issuerDn = arg0[0].getIssuerDN().toString();
    }
  }

  /**
   * Get Subject Dn. This is available after certificate check
   * or construction made with giving subject DN.
   * @return Subject DN
   */
  public String getSubjectDn() {
    return subjectDn;
  }
  /**
   * Get Issuer Dn. This is available after certificate check
   * or construction made with giving issuer DN.
   * @return Issuer DN
   */
  public String getIssuerDn() {
    return issuerDn;
  }
  @Override
  public X509Certificate[] getAcceptedIssuers() {
    // Bridges have self-signed certs, no accepted issuers.
    return null;
  }

}
