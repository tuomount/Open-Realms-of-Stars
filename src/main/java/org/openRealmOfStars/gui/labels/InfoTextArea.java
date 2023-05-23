package org.openRealmOfStars.gui.labels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTextArea;

import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.utilies.GuiStatics;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2018,2020  Tuomo Untinen
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
 * Customized Text Area
 *
 */

public class InfoTextArea extends JTextArea {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  /**
  * Y axel offset where to draw text
  */
  private static final int Y_OFFSET = 10;
  /**
   * Is cursor blinking
   */
  private boolean blinking = false;
  /**
   * Does text have auto scroll on
   */
  private boolean autoScroll = false;
  /**
   * Arrays of string used when scrolling the text
   */
  private String[] scrollText;

  /**
   * Text to show in InfoTextArea
   */
  private String textToShow;
  /**
   * Number of lines to shown auto scrolling the text
   */
  private int numberOfLines = 0;
  /**
   * Current line when scrolling
   */
  private int currentLine = 0;
  /**
   * Is smooth scrolling on or non smooth
   */
  private boolean smoothScroll = false;
  /**
   * Smooth scrolling for single line done and ready to move next line
   */
  private boolean smoothScrollNextRow = false;

  /**
   * Text has shadow.
   */
  private boolean textShadow = true;
  /**
   * Is smooth scrolling enabled
   * @return True if smooth scrolling enabled
   */
  public boolean isSmoothScroll() {
    return smoothScroll;
  }

  /**
   * Set smooths crolling.
   * @param smoothScroll True for smooth scrolling.
   */
  public void setSmoothScroll(final boolean smoothScroll) {
    this.smoothScroll = smoothScroll;
  }

  /**
   * Offset for smooth scrolling
   */
  private int smoothScrollY = 0;

  /**
   * Shadow color
   */
  private Color shadowColor;

  /**
   * Highlight shadow color
   */
  private Color highlightShadowColor;
  /**
   * Highlight color
   */
  private Color highlightColor;
  /**
   * Text for highlighting.
   */
  private String highlightText;
  /**
   * Construct InfoTextArea with default but set size on rows and
   * columns.
   * @param rows Number of rows
   * @param columns Number of columns
   */
  public InfoTextArea(final int rows, final int columns) {
    super(rows, columns);
    this.setFont(GuiStatics.getFontCubellanSC());
    this.setForeground(GuiStatics.getInfoTextColor());
    this.setTextColor(GuiStatics.getInfoTextColor(),
        GuiStatics.getInfoTextColorDark());
    this.setTextHighlightColor(GuiStatics.getCoolSpaceColor(),
        GuiStatics.getCoolSpaceColorDark());
    this.setBackground(Color.BLACK);
    autoScroll = false;
    this.setBorder(new SimpleBorder());
  }

  /**
   * Construct InfoTextArea with defaults.
   */
  public InfoTextArea() {
    super(17, 10);
    this.setFont(GuiStatics.getFontCubellanSC());
    this.setForeground(GuiStatics.getInfoTextColor());
    this.setTextColor(GuiStatics.getInfoTextColor(),
        GuiStatics.getInfoTextColorDark());
    this.setTextHighlightColor(GuiStatics.getCoolSpaceColor(),
        GuiStatics.getCoolSpaceColorDark());
    this.setBackground(Color.BLACK);
    autoScroll = false;
    this.setBorder(new SimpleBorder());
  }

  /**
   * Construct InfoTextArea with certain text.
   * @param text the text to be displayed, or null
   */
  public InfoTextArea(final String text) {
    super(text);
    this.setFont(GuiStatics.getFontCubellanSC());
    textToShow = text;
    this.setForeground(GuiStatics.getInfoTextColor());
    this.setTextColor(GuiStatics.getInfoTextColor(),
        GuiStatics.getInfoTextColorDark());
    this.setTextHighlightColor(GuiStatics.getCoolSpaceColor(),
        GuiStatics.getCoolSpaceColorDark());
    this.setBackground(Color.BLACK);
    autoScroll = false;
    this.setBorder(new SimpleBorder());
  }

  @Override
  public String getText() {
    return textToShow;
  }

  /**
   * Set text for highlight.
   * @param t Text to highligth.
   */
  public void setHighlightText(final String t) {
    highlightText = t;
  }
  @Override
  public void setText(final String t) {
    textToShow = t;
  }

  /**
   * Set text colors: Foreground and shadow
   * @param foreground Foreground color
   * @param shadow Shadow color
   */
  public void setTextColor(final Color foreground, final Color shadow) {
    setForeground(foreground);
    shadowColor = shadow;
  }
  /**
   * Set text colors: Highlight and highlight shadow
   * @param highlight Foreground color
   * @param hightlightShadow Shadow color
   */
  public void setTextHighlightColor(final Color highlight,
      final Color hightlightShadow) {
    highlightColor = highlight;
    highlightShadowColor = hightlightShadow;
  }
  /**
   * disable auto scroll
   */
  public void disableScrollText() {
    autoScroll = false;
  }

  /**
   * Scroll to next line
   */
  public void getNextLine() {
    currentLine++;
    if (currentLine >= scrollText.length) {
      currentLine = 0;
    }
  }

  /**
   * Scroll to previous line
   */
  public void getPrevLine() {
    currentLine--;
    if (currentLine < 0) {
      currentLine = scrollText.length - 1;
    }
  }

  /**
   * Set Scrollable text
   * @param text String
   * @param lines number of shown lines
   */
  public void setScrollText(final String text, final int lines) {
    if (text != null) {
      scrollText = text.split("\n");
      autoScroll = true;
      numberOfLines = lines;
      currentLine = 0;
    }
  }

  /**
   * Custom single character width
   */
  private int customCharWidth = -1;

  /**
   * Set custom character width.
   * @param width the character width
   */
  public void setCharacterWidth(final int width) {
    if (width < 1) {
      customCharWidth = -1;
    } else {
      customCharWidth = width;
      if (GuiStatics.isLargerFonts()) {
        customCharWidth = customCharWidth + 2;
      }
    }
  }

  @Override
  public void paintImmediately(final int x, final int y, final int w,
      final int h) {
    super.paintImmediately(0, 0, getWidth(), getHeight());
  }

  /**
   * Draw string with specific graphics.
   *
   * @param g Graphics
   * @param text String to draw
   * @param x X coordinate
   * @param y Y coordinate
   * @param highlightLocation boolean array if location should be highlighted.
   *                          This can be null and highlight won't be used.
   */
  private void drawString(final Graphics g, final String text,
      final int x, final int y, final boolean[] highlightLocation) {
    if (highlightLocation != null) {
      int offset = 0;
      for (int i = 0; i < text.length(); i++) {
        StringBuilder sb = new StringBuilder();
        sb.append(text.charAt(i));
        if (highlightLocation[i]) {
          g.setColor(highlightShadowColor);
          if (isTextShadow()) {
            g.drawString(sb.toString(), x + 3 + offset, y);
          }
          if (getFont() == GuiStatics.getFontCubellanSC() && isTextShadow()) {
            g.drawString(sb.toString(), x + 1 + offset, y);
            g.drawString(sb.toString(), x + 2 + offset, y - 1);
          }
          if (isTextShadow()) {
            g.drawString(sb.toString(), x + 2 + offset, y + 1);
          }
          g.setColor(highlightColor);
          g.drawString(sb.toString(), x + 2 + offset, y);
        } else {
          g.setColor(shadowColor);
          if (isTextShadow()) {
            g.drawString(sb.toString(), x + 3 + offset, y);
          }
          if (getFont() == GuiStatics.getFontCubellanSC() && isTextShadow()) {
            g.drawString(sb.toString(), x + 1 + offset, y);
            g.drawString(sb.toString(), x + 2 + offset, y - 1);
          }
          if (isTextShadow()) {
            g.drawString(sb.toString(), x + 2 + offset, y + 1);
          }
          g.setColor(getForeground());
          g.drawString(sb.toString(), x + 2 + offset, y);
        }
        offset = offset + GuiStatics.getTextWidth(getFont(), sb.toString());
      }
    } else {
      g.setColor(shadowColor);
      if (isTextShadow()) {
        g.drawString(text, x + 3, y);
      }
      if (getFont() == GuiStatics.getFontCubellanSC() && isTextShadow()) {
        g.drawString(text, x + 1, y);
        g.drawString(text, x + 2, y - 1);
      }
      if (isTextShadow()) {
        g.drawString(text, x + 2, y + 1);
      }
      g.setColor(getForeground());
      g.drawString(text, x + 2, y);
    }
  }
  @Override
  protected void paintComponent(final Graphics g) {
    this.setCaretPosition(this.getDocument().getLength());
    g.setColor(getBackground());
    Insets inset = this.getBorder().getBorderInsets(this);
    int sx = inset.left;
    int sy = inset.top;
    int width = getWidth() - inset.left - inset.right;
    int height = getHeight() - inset.top - inset.bottom;
    g.fillRect(sx, sx, width, height);
    g.setFont(this.getFont());
    if (getText() != null) {
      StringBuilder sb = new StringBuilder();
      if (!autoScroll) {
        sb = new StringBuilder(this.getText());
      } else {
        for (int i = 0; i < numberOfLines; i++) {
          if (i + currentLine < scrollText.length) {
            sb.append(scrollText[i + currentLine]).append("\n");
          }
        }
      }
      if (this.getLineWrap()) {
        int lastSpace = -1;
        int rowLen = 0;
        int maxRowLen = width / 6;
        if (customCharWidth > 0) {
          maxRowLen = width / customCharWidth;
        }
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
      }
      if (this.hasFocus() && this.isEditable()) {
        if (blinking) {
          blinking = false;
        } else {
          blinking = true;
          sb.insert(this.getCaretPosition(), '|');
        }
      }
      String[] texts = sb.toString().split("\n");
      for (int i = 0; i < texts.length; i++) {
        boolean[] highlightLocation = null;
        if (highlightText != null) {
          String tmp = texts[i];
          int index = tmp.toLowerCase().indexOf(highlightText.toLowerCase());
          int offset = 0;
          if (index != -1) {
            highlightLocation = new boolean[texts[i].length()];
            while (index != -1) {
              for (int j = 0; j < highlightText.length(); j++) {
                highlightLocation[offset + index + j] = true;
              }
              offset = offset + index + highlightText.length();
              tmp = tmp.substring(index + highlightText.length());
              index = tmp.toLowerCase().indexOf(highlightText.toLowerCase());
            }
          }
        }
        g.setColor(shadowColor);
        int yHeight = GuiStatics.getTextHeight(getFont(), texts[i]);
        if (!smoothScroll) {
          drawString(g, texts[i], sx, sy + i * yHeight + yHeight,
              highlightLocation);
        } else {
          drawString(g, texts[i], sx,
              sy + i * Y_OFFSET + Y_OFFSET - smoothScrollY,
              highlightLocation);
        }

      }
    }
    smoothScrollY++;
    if (smoothScrollY == 18) {
      smoothScrollY = 0;
      smoothScrollNextRow = true;
    }

  }

  /**
   * Should scroll to next row
   * @return True if enough smooth scrolling for one row.
   */
  public boolean getSmoothScrollNextRow() {
    if (smoothScrollNextRow) {
      smoothScrollNextRow = false;
      return true;
    }
    return false;
  }

  /**
   * Has Text shadow
   * @return the textShadow
   */
  public boolean isTextShadow() {
    return textShadow;
  }

  /**
   * Set text shadow.
   * @param textShadow the textShadow to set
   */
  public void setTextShadow(final boolean textShadow) {
    this.textShadow = textShadow;
  }

}
