package org.openRealmOfStars.gui.panels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2026 Tuomo Untinen
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
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.util.GraphRoutines;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.gui.util.UIScale;
import org.openRealmOfStars.utilities.FileIo.IOUtilities;

/**
 *
 * News Image panel for news corporation view.
 *
 */

public class NewsImagePanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Original image's X coordinate.
   */
  private static final int ORIGINAL_NEWS_IMAGE_TOP_X = 1215;
  /**
   * Original image's Y coordinate.
   */
  private static final int ORIGINAL_NEWS_IMAGE_TOP_Y = 53;
  /**
   * Original image's news image width.
   */
  private static final int ORIGINAL_NEWS_IMAGE_WIDTH = 2464;
  /**
   * Original image's news image height
   */
  private static final int ORIGINAL_NEWS_IMAGE_HEIGHT = 1345;
  /**
   * picture to draw
   */
  private BufferedImage image;

  /**
   * Background image
   */
  private BufferedImage backgroundImage;

  /**
   * News image to show
   */
  private BufferedImage newsImage;
  /**
   * News Image X coordinate after scaling
   */
  private int newsImageX;
  /**
   * News Image Y coordinate after scaling
   */
  private int newsImageY;
  /**
   * News Image width after scaling
   */
  private int newsImageWidth;
  /**
   * News Image height after scaling
   */
  private int newsImageHeight;
  /**
   * Text to show in news
   */
  private String text;

  /**
   * Create picture frame from frame size
   * @param width frame width
   * @param height frame height
   */
  public NewsImagePanel(final int width, final int height) {
    super();
    image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
    backgroundImage = IOUtilities.loadImage(
        "/resources/images/news_room_in_action.png");
    Dimension origSize = new Dimension(backgroundImage.getWidth(),
        backgroundImage.getHeight());
    backgroundImage = GraphRoutines.scaleImage(backgroundImage,
        image.getWidth(), image.getHeight());
    Dimension scaledSize = new Dimension(backgroundImage.getWidth(),
        backgroundImage.getHeight());
    newsImageX = ORIGINAL_NEWS_IMAGE_TOP_X * scaledSize.width / origSize.width;
    newsImageY = ORIGINAL_NEWS_IMAGE_TOP_Y * scaledSize.width / origSize.width;
    newsImageWidth =
        ORIGINAL_NEWS_IMAGE_WIDTH * scaledSize.width / origSize.width;
    newsImageHeight =
        ORIGINAL_NEWS_IMAGE_HEIGHT * scaledSize.width / origSize.width;
    text = null;
    Dimension size = new Dimension(image.getWidth(), image.getHeight());
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setBorder(new SimpleBorder());
  }

  /**
   * Get news image width.
   * @return Width in pixels.
   */
  public int getNewsImageWidth() {
    return newsImageWidth;
  }
  /**
   * Get news image height.
   * @return height in pixels.
   */
  public int getNewsImageHeight() {
    return newsImageHeight;
  }
  /**
   * Change news image
   * @param picture News Picture to show
   */
  public void setNewsImage(final BufferedImage picture) {
    newsImage = picture;
  }

  /**
   * Text to show instead of picture. Set to null to show picture instead.
   * @param textToShow In picture frame
   */
  public void setText(final String textToShow) {
    text = textToShow;
  }

  /**
   * Get Text to set to show. If null then image is shown.
   * @return Text or null
   */
  public String getText() {
    return text;
  }
  /**
   * Get news image.
   * @return Image from frame
   */
  public BufferedImage getImage() {
    return newsImage;
  }
  @Override
  protected void paintComponent(final Graphics g) {
    GradientPaint gradient = new GradientPaint(this.getWidth() / 2, 0,
        Color.BLACK, this.getWidth() / 2, this.getHeight(),
        GuiStatics.COLOR_GREY_40, true);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setPaint(gradient);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    int offsetX = (this.getWidth() - backgroundImage.getWidth()) / 2;
    int offsetY = (this.getHeight() - backgroundImage.getHeight()) / 2;
    g2d.drawImage(backgroundImage, offsetX, offsetY, null);
    if (newsImage != null) {
      g2d.drawImage(newsImage, offsetX + newsImageX, offsetY + newsImageY,
          null);
    }
    if (text != null) {
      int sx = offsetX + newsImageX + UIScale.scale(5);
      int sy = offsetY + newsImageHeight + UIScale.scale(30);
      g2d.setFont(GuiFonts.getFontCubellan());
      g2d.setColor(GuiStatics.COLOR_DAMAGE_ALMOST_DESTROYED);

      int lastSpace = -1;
      int rowLen = 0;
      // Use actual font metrics to determine character width
      int defaultCharWidth = GuiStatics.getTextWidth(getFont(), "M");
      if (defaultCharWidth < 1) {
        defaultCharWidth = UIScale.scale(6);
      }
      int maxRowLen = (newsImageWidth - UIScale.scale(5)) / defaultCharWidth;
      StringBuilder sb = new StringBuilder(this.getText());
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
        if (rowLen >= maxRowLen) {
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
      for (int i = 0; i < texts.length; i++) {
        int yHeight = GuiStatics.getTextHeight(getFont(), texts[i]) + 2;
        g2d.drawString(texts[i], sx, sy + i * yHeight + yHeight);
      }
    }
  }
}
