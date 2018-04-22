package org.openRealmOfStars.utilities;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import javax.imageio.ImageIO;

/**
 *
 * Open Realm of Stars Game Project
 * Copyright (C) 2016, 2018  Tuomo Untinen
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
public final class IOUtilities {

  /**
   * Hiding the constructor for utility class.
   */
  private IOUtilities() {
    // Nothing to do here
  }

  /**
   * Load image with URL. Can be used to read images inside JAR file
   * @param urlToImage the url to the image file
   * @return BufferedImage if succeed null if fails
   */
  public static BufferedImage loadImage(final URL urlToImage) {
    try {
      return ImageIO.read(urlToImage);
    } catch (IOException e) {
      System.err.print(urlToImage.toString() + " not found!");
      return null;
    }
  }

  /**
   * Read all data from input stream
   * @param is InputStream where to read
   * @return Read data as a byte array
   * @throws IOException if reading fails with IOException
   */
  public static byte[] readAll(final InputStream is) throws IOException {
    int dataLength = 0;
    int byteOffset = 0;
    int blockSize = 8000;
    byte[] buffer = null;
    do {
      byte[] tmpBuf = new byte[blockSize];
      dataLength = is.read(tmpBuf, 0, tmpBuf.length);
      if (dataLength != -1) {
        byte[] targetBuf;
        if (buffer == null) {
          targetBuf = new byte[dataLength];
          byteOffset = 0;
        } else {
          targetBuf = new byte[buffer.length + dataLength];
          System.arraycopy(buffer, 0, targetBuf, 0, buffer.length);
          byteOffset = buffer.length;
        }
        System.arraycopy(tmpBuf, 0, targetBuf, byteOffset, dataLength);
        buffer = targetBuf;
      }
    } while (dataLength != -1);
    return buffer;
  }
  /**
   * Read file as text file return as US-ASCII string
   * @param is DataInputStream
   * @return String
   * @throws IOException if there is any problem with the DataInputStream
   */
  public static String readTextFile(final DataInputStream is)
      throws IOException {
    byte[] dataBuf = new byte[is.available()];
    is.readFully(dataBuf);
    return new String(dataBuf, "US-ASCII");
  }

  /**
   * Big number for placing after screenshot
   */
  private static final int BIG_NUMBER = 9999;

  /**
   * Save Buffered Image into file under directory screenshots
   * @param image to save into screenshots directory.
   */
  public static void saveScreenShot(final BufferedImage image) {
    Calendar cal = Calendar.getInstance();
    File dir = new File("screenshots");
    if (!dir.exists()) {
      dir.mkdir();
    }
    String filename = "Screenshot-" + cal.getTimeInMillis() + "-"
        + DiceGenerator.getRandom(BIG_NUMBER) + ".png";
    File file = new File("screenshots/" + filename);
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
  public static String readString(final DataInputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    int len = is.readInt();
    for (int i = 0; i < len; i++) {
      char ch = is.readChar();
      sb.append(ch);
    }
    return sb.toString();
  }

  /**
   * Set or disable flag in bit mask.
   * Bit mask is limited for 8 bits.
   * @param value Original 8 bit value
   * @param bit Bit number to set or disable
   * @param set True to set and false to disable
   * @return new value with flag set or disabled
   */
  public static byte setFlag(final byte value, final int bit,
      final boolean set) {
    byte result = value;
    if (set) {
      byte bitmask = (byte) (1 << bit);
      result = (byte) (result | bitmask);
    } else {
      byte bitmask = (byte) ~((byte) (1 << bit));
      result = (byte) (result & bitmask);
    }
    return result;
  }

  /**
   * Get flag from a 8 bit value
   * @param value Original 8 bit value
   * @param bit Bit to get set or disable
   * @return true if flag has been set and false if not
   */
  public static boolean getFlag(final byte value, final int bit) {
    byte bitmask = (byte) (1 << bit);
    int tmp = value & bitmask;
    if (tmp != 0) {
      return true;
    }
    return false;
  }

  /**
   * Wraps string into new lines
   * @param input String to wrap
   * @param lineWidth Maximum line width
   * @return String with new line wraps
   */
  public static String stringWrapper(final String input, final int lineWidth) {
    StringBuilder sb = new StringBuilder(input);
    int lastSpace = -1;
    int rowLen = 0;
    for (int i = 0; i < sb.length(); i++) {
      if (sb.charAt(i) == ' ') {
        lastSpace = i;
      }
      if (sb.charAt(i) == '\n') {
        sb.setCharAt(i, ' ');
        lastSpace = i;
      } else {
        rowLen++;
      }
      if (rowLen > lineWidth && lastSpace != -1) {
        sb.setCharAt(lastSpace, '\n');
        rowLen = i - lastSpace;
        lastSpace = -1;
      }
    }
    return sb.toString();
  }

  /**
   * Writes string into DataOutputStream. First 4 octets tell string length
   * then each character is written with 2 octets
   * @param os the output stream
   * @param str the string that os gets
   * @throws IOException if there is any problem with DataOutputStream
   */
  public static void writeString(final DataOutputStream os, final String str)
      throws IOException {
    if (str != null) {
      os.writeInt(str.length());
      os.writeChars(str);
    } else {
      os.writeInt(0);
    }
  }

  /**
   * Converts 16bits to integer
   * @param hi Higher 8 bits
   * @param lo Lower 8 bits
   * @return integer
   */
  public static int convert16BitsToInt(final int hi, final int lo) {
    return (hi << 8) + lo;
  }

  /**
   * Read 16 bits to int from input stream. It assumes that
   * bits are in hi byte, then lo byte.
   * @param is InputStream where to read
   * @return Integer
   * @throws IOException if reading fail
   */
  public static int read16BitsToInt(final InputStream is) throws IOException {
    int hi = is.read();
    int lo = is.read();
    return convert16BitsToInt(hi, lo);
  }
  /**
   * Converts Integer to 16 bit MSB byte array.
   * @param value Integer to convert
   * @return two byte byte array
   */
  public static byte[] convertIntTo16BitMsb(final int value) {
    byte[] lenBuffer = new byte[2];
    lenBuffer[0] = (byte) ((value & 0xff00) >> 8);
    lenBuffer[1] = (byte) (value & 0x00ff);
    return lenBuffer;
  }
  /**
   * Writes string(as UTF8) into DataOutputStream.
   * First 2 octets tells string length as bytes, MSB as first byte.
   * Then whole string is written as UTF8 encoded byte array.
   * @param os the output stream
   * @param str the string that os gets
   * @throws IOException if there is any problem with OutputStream
   */
  public static void writeUTF8String(final OutputStream os, final String str)
      throws IOException {
    if (str != null) {
      byte[] buffer = str.getBytes(StandardCharsets.UTF_8);
      if (buffer.length > 65535) {
        throw new IOException("String is too long! " + buffer.length);
      }
      os.write(convertIntTo16BitMsb(buffer.length));
      os.write(buffer);
    } else {
      os.write(convertIntTo16BitMsb(0));
    }
  }

  /**
   * Reads string(as UTF8) from OutputStream.
   * First 2 octets tells string length as bytes, MSB as first byte.
   * Then whole string is read as UTF8 encoded byte array.
   * @param is the input stream
   * @return Read string
   * @throws IOException if there is any problem with OutputStream
   */
  public static String readUTF8String(final InputStream is)
      throws IOException {
    byte[] lenBuffer = new byte[2];
    int amount = is.read(lenBuffer);
    if (amount != 2) {
      throw new IOException("Could only read " + amount + " bytes!");
    }
    int len = convert16BitsToInt(lenBuffer[0] & 0xff, lenBuffer[1] & 0xff);
    byte[] buffer = new byte[len];
    int offset = 0;
    do {
      amount = is.read(buffer, offset, len - offset);
      if (amount == -1) {
        int read = amount + offset;
        throw new IOException("Unexpected end of file! Could only read "
            + read + " bytes!");
      }
      offset = offset + amount;
    } while (offset < len);
    return new String(buffer, StandardCharsets.UTF_8);
  }

}
