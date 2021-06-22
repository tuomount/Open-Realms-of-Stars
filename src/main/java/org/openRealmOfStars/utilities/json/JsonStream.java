package org.openRealmOfStars.utilities.json;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020,2021 Tuomo Untinen
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
   * Begin array aka [ left square bracket as string.
   */
  public static final String CH_BEGIN_ARRAY = "[";
  /**
   * Begin object aka  { left curly bracket
   */
  public static final int BEGIN_OBJECT = 123;
  /**
   * Begin object aka  { left curly bracket as String
   */
  public static final String CH_BEGIN_OBJECT = "{";
  /**
   * End array aka ] right square bracket
   */
  public static final int END_ARRAY = 93;
  /**
   * End array aka ] right square bracket as string.
   */
  public static final String CH_END_ARRAY = "]";
  /**
   * End object aka } right curly bracket
   */
  public static final int END_OBJECT = 125;
  /**
   * End object aka } right curly bracket as String.
   */
  public static final String CH_END_OBJECT = "}";
  /**
   * Name separator aka : colon
   */
  public static final int NAME_SEPARATOR = 58;
  /**
   * Name separator aka : colon as String.
   */
  public static final String CH_NAME_SEPARATOR = ":";
  /**
   * value separator aka , comma
   */
  public static final int VALUE_SEPARATOR = 44;
  /**
   * value separator aka , comma as String.
   */
  public static final String CH_VALUE_SEPARATOR = ",";
  /**
   * String start and end aka " double qoute
   */
  public static final int DOUBLE_QOUTE = 34;
  /**
   * String start and end aka " double qoute as String
   */
  public static final String CH_DOUBLE_QOUTE = "\"";
  /**
   * Escape charater aka \.
   */
  public static final int ESCAPE = 92;
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
    if (buffer == null || buffer.length == 0) {
      closed = true;
    }
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
   * Count how many bytes something is until character is encountered.
   * Escaped characters are skipped.
   * @param character Character to encounter.
   * @return number of bytes until character is met.
   * @throws IOException If stream is closed.
   */
  private int countUntil(final int character) throws IOException {
    int count = 0;
    if (isReadable()) {
      byte value = buffer[offset];
      while (value != character) {
        boolean escape = false;
        if (value == ESCAPE) {
          escape = true;
        }
        count++;
        if (offset + count < buffer.length) {
          value = buffer[offset + count];
        } else {
          return count - 1;
        }
        if (escape && value == DOUBLE_QOUTE) {
          count++;
          if (offset + count < buffer.length) {
            value = buffer[offset + count];
          } else {
            return count - 1;
          }
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
      offset = offset + count + 1;
      return new String(temp, StandardCharsets.UTF_8);
    }
    return null;
  }

  /**
   * Read Json number from stream.
   * @return Number as string
   *         or null if no number at current position of stream.
   * @throws IOException If Stream is closed.
   */
  public String readNumber() throws IOException {
    boolean firstDecimal = true;
    boolean exponent = false;
    boolean exponentDigit = false;
    boolean fracPoint = false;
    boolean fracDigit = false;
    boolean stop = false;
    int startZero = 0;
    boolean invalid = false;
    // 0 start, 1 minus, 2 int, 3 frac, 4 frac-digit,
    // 5 exp-e, 6 exp-sign, 7 exp-digit
    int state = 0;
    int count = 0;
    if (offset + count >= buffer.length) {
      return null;
    }
    do  {
      byte value = buffer[offset + count];
      if (state == 0) {
        // start
        if (value == 45) {
          state = 1;
          count++;
        } else if (value >= 48 && value <= 57) {
          count++;
          state = 2;
          if (value == 48) {
            startZero++;
          } else {
            firstDecimal = false;
          }
        } else {
          // Did not start with minus or digit
          return null;
        }
      } else if (state == 1) {
        // Negative sign
        if (value >= 48 && value <= 57) {
          count++;
          state = 2;
          if (value == 48) {
            startZero++;
          } else {
            firstDecimal = false;
          }
        } else {
          // next wasn't digit
          return null;
        }
      } else if (state == 2) {
        // Integer part
        if (value >= 48 && value <= 57) {
          count++;
          state = 2;
          if (value == 48 && firstDecimal) {
            startZero++;
          } else {
            firstDecimal = false;
          }
        } else {
          state = 3;
        }
      } else if (state == 3) {
        // Frac decimal
        if (value == 46) {
          count++;
          state = 4;
          fracPoint = true;
        } else {
          state = 5;
        }
      } else if (state == 4) {
        // Frac digit
        if (value >= 48 && value <= 57) {
          count++;
          state = 4;
          fracDigit = true;
        } else {
          state = 5;
        }
      } else if (state == 5) {
        // Exp e
        if (value == 69 || value == 101) {
          count++;
          state = 6;
          exponent = true;
        } else {
          stop = true;
        }
      } else if (state == 6) {
        // Exp sign
        if (value == 45 || value == 43) {
          count++;
          state = 7;
        }
        state = 7;
      } else if (state == 7) {
        // Frac digit
        if (value >= 48 && value <= 57) {
          count++;
          state = 7;
          exponentDigit = true;
        } else {
          stop = true;
        }
      }
      if (offset + count >= buffer.length) {
        stop = true;
      }
    } while (!stop);
    if (fracDigit != fracPoint) {
      invalid = true;
    }
    if (exponent != exponentDigit) {
      invalid = true;
    }
    if (startZero > 1) {
      invalid = true;
    }
    if (!invalid) {
      byte[] temp = new byte[count];
      System.arraycopy(buffer, offset, temp, 0, count);
      offset = offset + count;
      return new String(temp, StandardCharsets.UTF_8);
    }
    return null;
  }
  /**
   * Read certain US ascii string away from stream
   * @param text String to read away.
   * @return True if stream contained string at position
   */
  public boolean readAway(final String text) {
    int limit = text.length();
    if (offset + limit > buffer.length) {
      limit = buffer.length - offset;
    }
    byte[] buf = new byte[limit];
    System.arraycopy(buffer, offset, buf, 0, limit);
    String str = new String(buf, StandardCharsets.US_ASCII);
    if (str.equals(text)) {
      offset = offset + limit;
      return true;
    }
    return false;
  }
}
