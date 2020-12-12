package org.openRealmOfStars.gui.labels;

import java.awt.Graphics;
import java.awt.Insets;

import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.utilities.TextUtilities;

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
* Handling label which draws text which is on label.
* HyperLabel fixes Java issue where font is not correct when
* displaying HTML content.
*
*/
public class HyperLabel extends SpaceLabel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Create label.
   * @param text Text to show
   */
  public HyperLabel(final String text) {
    super(text);
  }

  /**
   * Quick and dirty way to convert HTML more understandable format.
   * NOTE: This is not full working conversion.
   * @return Converted string.
   */
  private String convertHtml() {
    String text = getText();
    if (text.startsWith("<html>") && text.endsWith("</html>")) {
      text = TextUtilities.removeLineChanges(text);
      text = text.replace("<html>", "");
      text = text.replace("</html>", "");
      text = text.replace("<br>", "\n");
      text = text.replace("<p>", "\n");
      text = text.replace("</p>", "\n");
    }
    return text;
  }

  @Override
  protected void paintComponent(final Graphics g) {
    g.setColor(GuiStatics.COLOR_SPACE_GREY_BLUE);
    int sx = 0;
    int sy = 0;
    int width = getWidth();
    int height = getHeight();
    if (this.getBorder() != null) {
      Insets inset = this.getBorder().getBorderInsets(this);
      sx = inset.left;
      sy = inset.top;
      width = getWidth() - inset.left - inset.right;
      height = getHeight() - inset.top - inset.bottom;
    }
    g.fillRect(sx, sx, width, height);
    g.setFont(this.getFont());
    if (getText() != null) {
      String text = convertHtml();
      int lastSpace = -1;
      int rowLen = 0;
      int maxRowLen = width / 6;
      int customCharWidth = GuiStatics.getTextWidth(getFont(),
          "EeAaRrIiOoTtSs");
      customCharWidth = customCharWidth / 14;
      if (customCharWidth > 0) {
        maxRowLen = width / customCharWidth;
      }
      StringBuilder sb = new StringBuilder(text);
      for (int i = 0; i < sb.length(); i++) {
        if (sb.charAt(i) == ' ') {
          lastSpace = i;
        }
        if (sb.charAt(i) == '\n') {
          lastSpace = -1;
          rowLen = 0;
        } else {
          rowLen++;
        }
        if (rowLen > maxRowLen) {
          if (lastSpace != -1) {
            sb.setCharAt(lastSpace, '\n');
            rowLen = i - lastSpace;
            lastSpace = -1;
          } else {
            sb.setCharAt(i, '\n');
            lastSpace = -1;
            rowLen = 0;
          }
        }
      }
      String[] texts = sb.toString().split("\n");

      int yTotal = 0;
      int yCur = 0;
      int lines = 0;
      int totalLines = 0;
      for (int i = 0; i < texts.length; i++) {
        int yHeight = GuiStatics.getTextHeight(getFont(), texts[i]);
        if (texts[i].contains("<ht>") && yCur > yTotal) {
          yTotal = yCur;
          totalLines = lines;
          yCur = 0;
          lines = 0;
        }
        yCur = yCur + yHeight;
        lines++;
      }
      if (yCur > yTotal) {
        yTotal = yCur;
        totalLines = lines;
      }
      if (totalLines == 0) {
        totalLines = 1;
      }
      int yAvg = yTotal / totalLines;
      int yOffset = height / 2 - yTotal / 2;
      g.setColor(getForeground());
      int y = 0;
      int x = 0;
      int longestLine = 0;
      for (int i = 0; i < texts.length; i++) {
        String tmpText = texts[i];
        int xOffset = 0;
        if (tmpText.contains("<ht>")) {
          tmpText = tmpText.replace("<ht>", "");
          y = 0;
          x = x + longestLine + 25;
        }
        if (tmpText.contains("<li>")) {
          tmpText = tmpText.replace("<li>", "");
          int xWidth = GuiStatics.getTextWidth(getFont(), " ");
          xOffset = xWidth * 5;
          g.fillOval(x + xOffset - xWidth + sx + 2,
              sy + y * yAvg + 5 + yOffset, 8, 8);
        }
        int lineLength = GuiStatics.getTextWidth(getFont(), tmpText);
        if (lineLength > longestLine) {
          longestLine = lineLength;
        }
        g.drawString(tmpText, x + xOffset + sx + 2,
            sy + y * yAvg + yAvg + yOffset);
        y++;
      }
    }

  }

}
