package org.openRealmOfStars.utilities;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

import javax.imageio.ImageIO;


/**
 * 
 * Open Realm of Stars Game Project
 * Copyright (C) 2016  Tuomo Untinen
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
 * Generic IO Utilities
 *
 */

public class IOUtilities {

  /**
   * Load image with URL. Can be used to read images inside JAR file
   * @param urlToImage
   * @return BufferedImage if succeed null if fails
   */
  public static BufferedImage loadImage(URL urlToImage) {
    try {
      return ImageIO.read(urlToImage);
    } catch (IOException e) {
      System.err.print(urlToImage.toString()+" not found!");
      return null;
    } 
  }

  /**
   * Read file as text file return as US-ASCII string
   * @param is DataInputStream
   * @return String
   * @throws IOException
   */
  public static String readTextFile(DataInputStream is) throws IOException {
    byte[] dataBuf = new byte[is.available()];
    is.readFully(dataBuf);
    String str = new String(dataBuf,"US-ASCII");
    return str;
  }
  
  /**
   * Save Buffered Image into file under directory screenshots
   * @param image to save into screenshots directory.
   */
  public static void saveScreenShot(BufferedImage image) {
    Calendar cal = Calendar.getInstance();
    File dir = new File("screenshots");
    if (!dir.exists()) {
      dir.mkdir();
    }
    String filename="Screenshot-"+cal.getTimeInMillis()+"-"+DiceGenerator.getRandom(10000)+".png";
    File file = new File("screenshots/"+filename);
    try {
      ImageIO.write(image, "png", file);
    } catch (IOException e) {
      System.err.println("Failing to write screenshot!");
      e.printStackTrace();
    }
  }

  /**
   * Reads string from DataInputStream. First 4 octets tell string length
   * then each character is read with 2 octets.
   * @param is DataInputStream
   * @return string
   * @throws IOException if read fails
   */
  public static String readString(DataInputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    int len = is.readInt();
    for (int i=0;i<len;i++) {
      char ch =is.readChar();
      sb.append(ch);
    }
    return sb.toString();
  }
  
  /**
   * Wraps string into new lines
   * @param input String to wrap
   * @param lineWidth Maximum line width
   * @return String with new line wraps
   */
  public static String stringWrapper(String input,int lineWidth) {
    StringBuilder sb = new StringBuilder(input);
    int lastSpace = -1;
    int rowLen = 0;
    for (int i=0;i<sb.length();i++) {
      if (sb.charAt(i)==' ') {
        lastSpace = i;
      }
      if (sb.charAt(i)=='\n') {
        sb.setCharAt(i, ' ');
        lastSpace = i;
      } else {
        rowLen++;
      }
      if (rowLen > lineWidth) {
        if (lastSpace != -1) {
          sb.setCharAt(lastSpace, '\n');
          rowLen=i-lastSpace;
          lastSpace = -1;
        }
      }
    }
    return sb.toString();
  }
  
  /**
   * Writes string into DataOutputStream. First 4 octets tell string length
   * then each character is written with 2 octets
   * @param os
   * @param str
   * @throws IOException
   */
  public static void writeString(DataOutputStream os, String str) 
                                               throws IOException {
    if (str!=null) {
      os.writeInt(str.length());
      os.writeChars(str);
    } else {
      os.writeInt(0);
    }
  }


}
