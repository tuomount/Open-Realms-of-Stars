package org.openRealmOfStars.utilities.json;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
* JsonStream for parsing Json text file.
*
*/
public class JsonStream extends InputStream {

  /**
   * Begin array aka [ left square bracket
   */
  public static final int BEGIN_ARRAY = 91;
  /**
   * Begin object aka  { left curly bracket
   */
  public static final int BEGIN_OBJECT = 123;
  /**
   * End array aka ] right square bracket
   */
  public static final int END_ARRAY = 93;
  /**
   * End object aka } right curly bracket
   */
  public static final int END_OBJECT = 125;
  /**
   * Name separator aka : colon
   */
  public static final int NAME_SEPARATOR = 58;
  /**
   * value separator aka , comma
   */
  public static final int VALUE_SEPARATOR = 44;
  /**
   * String start and end aka " double qoute
   */
  public static final int DOUBLE_QOUTE = 34;

  /**
   * Buffer where json is read.
   */
  private byte[] buffer;

  /**
   * Internal offset in stream.
   */
  private int offset;
  /**
   * Is stream closed?
   */
  private boolean closed;
  /**
   * Constructor for initializing stream.
   * @param buffer Byte array containing JSON.
   */
  public JsonStream(final byte[] buffer) {
    this.buffer = buffer;
    offset = 0;
    closed = false;
  }

  /**
   * Is stream readable? Is it open and offset is still less than buffer limit.
   * @return True if read is possible
   * @throws IOException If stream is closed.
   */
  private boolean isReadable() throws IOException {
    if (closed) {
      throw new IOException("Stream has been closed and cannot be read.");
    }
    if (offset < buffer.length) {
      return true;
    }
    return false;
  }
  @Override
  public int read() throws IOException {
    if (isReadable()) {
      int value = buffer[offset];
      offset++;
      return value;
    }
    return -1;
  }

  @Override
  public int available() throws IOException {
    if (closed) {
      throw new IOException("Stream has been closed and cannot be read.");
    }
    return buffer.length - offset;
  }

  @Override
  public long skip(final long n) throws IOException {
    if (closed) {
      throw new IOException("Stream has been closed and cannot be read.");
    }
    long result = n;
    if (result + offset >= buffer.length) {
      result = available();
      offset = buffer.length;
    } else {
      offset = (int) (offset + result);
    }
    return result;
  }

  @Override
  public void close() throws IOException {
    closed = true;
    buffer = null;
  }

  @Override
  public String toString() {
    if (closed) {
      return "Closed stream";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("Length: ");
    sb.append(buffer.length);
    sb.append(" Offset: ");
    sb.append(offset);
    sb.append(" Next: ");
    if (offset < buffer.length) {
      sb.append("'");
      sb.append(Character.toString((char) buffer[offset]));
      sb.append("' ");
      sb.append(Integer.valueOf((int) (buffer[offset] & 0xff)));
    } else {
      sb.append("EOS");
    }
    return sb.toString();
  }

  /**
   * Read JSON whitespace characters away from current position.
   * @throws IOException If stream is closed.
   */
  public void readWhiteSpace() throws IOException {
    if (isReadable()) {
      byte value = buffer[offset];
      while (value == 32 || value == 9 || value == 10 || value == 13) {
        offset++;
        if (offset < buffer.length) {
          value = buffer[offset];
        } else {
          value = -1;
        }
      }
    }
  }

  /**
   * Checks if current position has certain special character. If true
   * then offset is increased by one.
   * @param character Special character to check.
   * @return True current character is special one
   * @throws IOException If stream is closed.
   */
  public boolean isCurrent(final int character) throws IOException {
    if (isReadable()) {
      byte value = buffer[offset];
      if (value == character) {
        offset++;
        return true;
      }
    }
    return false;
  }

  /**
   * Count how many bytes something is until character is encountered
   * @param character Character to encounter.
   * @return number of bytes until character is met.
   * @throws IOException If stream is closed.
   */
  private int countUntil(final int character) throws IOException {
    int count = 0;
    if (isReadable()) {
      byte value = buffer[offset];
      while (value != character) {
        count++;
        if (offset + count < buffer.length) {
          value = buffer[offset + count];
        } else {
          value = -1;
        }
      }
    }
    return count;
  }

  /**
   * Read string from stream.
   * @return String or null if not inside double qoutes.
   * @throws IOException If stream is closed.
   */
  public String readString() throws IOException {
    if (isCurrent(DOUBLE_QOUTE)) {
      int count = countUntil(DOUBLE_QOUTE);
      byte[] temp = new byte[count];
      System.arraycopy(buffer, offset, temp, 0, count);
      offset = offset + count;
      return new String(temp, StandardCharsets.UTF_8);
    }
    return null;
  }
}
