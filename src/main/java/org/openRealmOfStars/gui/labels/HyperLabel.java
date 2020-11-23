package org.openRealmOfStars.gui.labels;

import java.awt.Graphics;
import java.awt.Insets;

import org.openRealmOfStars.gui.utilies.GuiStatics;

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
      String[] texts = convertHtml().split("\n");
      int yTotal = 0;
      for (int i = 0; i < texts.length; i++) {
        int yHeight = GuiStatics.getTextHeight(getFont(), texts[i]);
        yTotal = yTotal + yHeight;
      }
      int yAvg = yTotal / texts.length;
      int yOffset = height / 2 - yTotal / 2;
      g.setColor(getForeground());
      for (int i = 0; i < texts.length; i++) {
        String tmpText = texts[i];
        int xOffset = 0;
        if (tmpText.contains("<li>")) {
          tmpText = texts[i].replace("<li>", "");
          int xWidth = GuiStatics.getTextWidth(getFont(), " ");
          xOffset = xWidth * 5;
          g.fillOval(xOffset - xWidth + sx + 2,
              sy + i * yAvg + 5 + yOffset, 8, 8);
        }
        g.drawString(tmpText, xOffset + sx + 2,
            sy + i * yAvg + yAvg + yOffset);
      }
    }

  }

}
