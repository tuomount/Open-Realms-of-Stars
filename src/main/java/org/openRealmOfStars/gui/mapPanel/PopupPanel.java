package org.openRealmOfStars.gui.mapPanel;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.utilies.GraphRoutines;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.anomaly.SpaceAnomaly;
import org.openRealmOfStars.player.combat.Combat;


/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Class for handling popups on map panel
*
*/
public class PopupPanel {

  /**
   * Text to display on panel
   */
  private String text;

  /**
   * Title to display on panel
   */
  private String title;

  /**
   * Image to shop on panel
   */
  private BufferedImage image;
  /**
   * Scaled down image
   */
  private BufferedImage scaledImage;

  /**
   * Is popup panel dismissed and ready for state change.
   */
  private boolean dismissed;

  /**
   * Combat after popup
   */
  private Combat combat;
  /**
   * Constructor for PopupPanel
   * @param text Text to show
   * @param title Title to show
   */
  public PopupPanel(final String text, final String title) {
    this.text = text;
    this.title = title;
    image = null;
    dismissed = false;
    combat = null;
  }

  /**
   * Create Popup panel based on space anomaly
   * @param anomaly Space anomaly
   */
  public PopupPanel(final SpaceAnomaly anomaly) {
    text = anomaly.getText();
    image = anomaly.getImage();
    this.combat = anomaly.getCombat();
    switch (anomaly.getType()) {
      case CREDIT: {
        title = "Credit cache!";
        break;
      }
      case DEEP_SPACE_ANCHOR: {
        title = "Deep space anchor!";
        break;
      }
      case TECH: {
        title = "Tech discovered!";
        break;
      }
      case LAIR: {
        title = "Pirate station found!";
        break;
      }
      case PIRATE: {
        title = "Pirate ship encoutered!";
        break;
      }
      case MONSTER: {
        title = "Monster encoutered!";
        break;
      }
      case MAP: {
        title = "Old probe found!";
        break;
      }
      case SHIP: {
        title = "Ship with crew!";
        break;
      }
      case WORMHOLE: {
        title = "Space anomaly!";
        break;
      }
      default: {
        title = "Space anomaly!";
        break;
      }
    }
  }

  /**
   * Change popup panel text.
   * @param text where to change.
   */
  public void setText(final String text) {
    this.text = text;
  }

  /**
   * Get popup panel text.
   * @return Text of panel
   */
  public String getText() {
    return this.text;
  }

  /**
   * Change popup panel title.
   * @param title where to change.
   */
  public void setTitle(final String title) {
    this.title = title;
  }

  /**
   * Get popup panel title.
   * @return Title of panel
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Chnage popup panel image
   * @param image Where to change
   */
  public void setImage(final BufferedImage image) {
    this.image = image;
    this.scaledImage = null;
  }

  /**
   * Get popup panel image. This can be null.
   * @return BufferedImage
   */
  public BufferedImage getImage() {
    return this.image;
  }

  /**
   * Split text to rows
   * @param width Maximum row length in pixel
   * @param charWidth Average character size in pixel
   * @return Splitted rows
   */
  protected String[] splitText(final int width, final int charWidth) {
    StringBuilder sb = new StringBuilder(text);
    int lastSpace = -1;
    int maxRowLen = width / charWidth;
    int rowLen = 0;
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
    return sb.toString().split("\n");
  }

  /**
   * Draw gradient border
   * @param gr Graphics2D used for drawing
   * @param x X coordinate where to start
   * @param y Y coordinate where to start
   * @param width border width
   * @param height border height
   */
  protected void drawGradientBorder(final Graphics2D gr, final int x,
      final int y, final int width, final int height) {
    GradientPaint gradient = new GradientPaint(x, y,
        GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER, x + width, y + width,
        GuiStatics.COLOR_COOL_SPACE_BLUE, false);
    gr.setPaint(gradient);
    int borderSize = 3;
    gr.fillRect(x, y, width, borderSize);
    gr.fillRect(x, y + borderSize, borderSize, height - borderSize);
    gr.fillRect(x + borderSize, y + height - borderSize, width - borderSize,
        borderSize);
    gr.fillRect(x + width - borderSize, y + borderSize, borderSize,
        height - borderSize);
  }
  /**
   * Draw popup with text and possible image
   * @param screen Screen where to draw pop up
   */
  public void drawPopup(final BufferedImage screen) {
    Graphics2D gr = screen.createGraphics();
    int x = screen.getWidth() / 10;
    int y = screen.getHeight() / 10;
    int width = screen.getWidth() - (screen.getWidth() / 5);
    int height = screen.getHeight() - (screen.getHeight() / 5);
    gr.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS);
    gr.fillRect(x, y, width, height);
    int borderSize = 3;
    drawGradientBorder(gr, x, y, width, height);
    int textX = x + width / 20;
    int textY = y + height / 7;
    int textWidth = width - (width / 10);
    int textHeight = height - (height / 4);
    if (image != null) {
      int imageX = x + width / 20;
      int imageY = textY;
      int imageWidth = textWidth;
      int imageHeight = textHeight * 3 / 4;
      textHeight = textHeight / 4;
      textY = textY + imageHeight + borderSize * 2;
      drawGradientBorder(gr, imageX, imageY, imageWidth, imageHeight);
      gr.setPaint(Color.BLACK);
      gr.fillRect(imageX + borderSize, imageY + borderSize,
          imageWidth - borderSize * 2, imageHeight - borderSize * 2);
      if (scaledImage == null) {
        scaledImage = GraphRoutines.scaleImage(image,
            imageWidth - 2 * borderSize, imageHeight - 2 * borderSize);
      }
      gr.drawImage(scaledImage,
          imageX + imageWidth / 2 - scaledImage.getWidth() / 2,
          imageY + borderSize, null);
    }
    drawGradientBorder(gr, textX, textY, textWidth, textHeight);
    gr.setPaint(GuiStatics.COLOR_VERY_DARK_GREY_TRANS);
    gr.fillRect(textX + borderSize, textY + borderSize,
        textWidth - borderSize * 2, textHeight - borderSize * 2);
    gr.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    gr.setFont(GuiStatics.getFontCubellanBoldBig());
    int h = GuiStatics.getTextHeight(gr.getFont(), title);
    int w = GuiStatics.getTextWidth(gr.getFont(), title);
    w = width / 2  - w / 2;
    gr.drawString(title, x + w - 1, y + h + borderSize);
    gr.drawString(title, x + w + 1, y + h + borderSize);
    gr.drawString(title, x + w, y + h + borderSize - 1);
    gr.drawString(title, x + w, y + h + borderSize + 1);
    gr.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE);
    gr.drawString(title, x + w, y + h + borderSize);
    gr.setFont(GuiStatics.getFontCubellanSmaller());
    w = GuiStatics.getTextWidth(gr.getFont(), "AveragemiuM");
    w = w / 11;
    h = GuiStatics.getTextHeight(gr.getFont(), "AveragemiuM");
    String[] rows = splitText(textWidth, w);
    gr.setColor(GuiStatics.COLOR_GREEN_TEXT);
    for (int i = 0; i < rows.length; i++) {
      w = GuiStatics.getTextWidth(gr.getFont(), rows[i]);
      w = textWidth / 2  - w / 2;
      gr.drawString(rows[i], textX + w, textY + h * 2 + h * i);
    }
    gr.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    gr.setFont(GuiStatics.getFontCubellanSC());
    String hint = "Press enter/space bar or click to continue";
    h = GuiStatics.getTextHeight(gr.getFont(), hint);
    w = GuiStatics.getTextWidth(gr.getFont(), hint);
    w = width / 2  - w / 2;
    gr.drawString(hint, x + w - 1, y + height - h - borderSize * 2);
    gr.drawString(hint, x + w + 1, y + height - h - borderSize * 2);
    gr.drawString(hint, x + w, y + 1 + height - h - borderSize * 2);
    gr.drawString(hint, x + w, y - 1 + height - h - borderSize * 2);
    gr.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE);
    gr.drawString(hint, x + w,  y + height - h - borderSize * 2);
    gr.dispose();
  }

  /**
   * Dismiss popup panel and ready for state change
   */
  public void dismiss() {
    dismissed = true;
  }

  /**
   * Is popup panel dismissed?
   * @return True if ready to dismissing and state change.
   */
  public boolean isDismissed() {
    return dismissed;
  }

  /**
   * Get Combat after popup
   * @return Combat or null
   */
  public Combat getCombat() {
    return combat;
  }
}
