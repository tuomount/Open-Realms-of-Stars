package org.openRealmOfStars.gui.scrollPanel;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.utilies.GuiStatics;

/**
 *
 * Open Realm of Stars game project
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
 * Customized scrollbar UI to match Space theme
 *
 */
public class SpaceScrollBarUI extends BasicScrollBarUI {

  /**
   * Creates a new createUI for component
   *
   * @param c JComponent
   * @return SpaceScrollBarUI
   */
  public static ComponentUI createUI(final JComponent c) {
    return new SpaceScrollBarUI();
  }

  /**
   * Constructor for new UI
   */
  public SpaceScrollBarUI() {
    super();
  }

  @Override
  protected JButton createDecreaseButton(final int orientation) {
    if (orientation == SwingConstants.NORTH) {
      JButton result = new IconButton(Icons.getIconByName(Icons.ICON_SCROLL_UP),
          Icons.getIconByName(Icons.ICON_SCROLL_UP_PRESSED), false, "", null);
      return result;
    }
    JButton result = new IconButton(Icons.getIconByName(Icons.ICON_SCROLL_LEFT),
        Icons.getIconByName(Icons.ICON_SCROLL_LEFT_PRESSED), false, "", null);
    return result;
  }

  @Override
  protected JButton createIncreaseButton(final int orientation) {
    if (orientation == SwingConstants.SOUTH) {
      JButton result = new IconButton(
          Icons.getIconByName(Icons.ICON_SCROLL_DOWN),
          Icons.getIconByName(Icons.ICON_SCROLL_DOWN_PRESSED), false, "", null);
      return result;
    }
    JButton result = new IconButton(
        Icons.getIconByName(Icons.ICON_SCROLL_RIGHT),
        Icons.getIconByName(Icons.ICON_SCROLL_RIGHT_PRESSED), false, "", null);
    return result;
  }

  /**
   * How many single middle part width
   */
  private static final int BAR_LEN = 32;
  @Override
  protected void paintThumb(final Graphics g, final JComponent c,
      final Rectangle thumbBounds) {
    if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
      int y = incrButton.getHeight() + 1;
      int distance = scrollbar.getHeight() - incrButton.getHeight()
          - decrButton.getHeight();
      y = y + distance * scrollbar.getValue()
          / (scrollbar.getMaximum() - scrollbar.getMinimum());
      int picHeight = GuiStatics.IMAGE_SCROLL_BAR_THUMB.getHeight();
      int endHeight = thumbBounds.height / 2;
      int bars = 0;
      if (endHeight > picHeight / 2) {
        endHeight = thumbBounds.height / 3;
        if (endHeight > BAR_LEN) {
          bars = thumbBounds.height / BAR_LEN;
          bars = bars - 2;
          endHeight = (thumbBounds.height - bars * BAR_LEN) / 2;
        } else {
          bars = 1;
        }
      }
      if (endHeight > GuiStatics.IMAGE_SCROLL_BAR_THUMB.getHeight() - 16) {
        endHeight = GuiStatics.IMAGE_SCROLL_BAR_THUMB.getHeight() - 16;
      }

      BufferedImage top = GuiStatics.IMAGE_SCROLL_BAR_THUMB.getSubimage(0, 0,
          GuiStatics.IMAGE_SCROLL_BAR_THUMB.getWidth(), endHeight);
      BufferedImage bottom = GuiStatics.IMAGE_SCROLL_BAR_THUMB.getSubimage(0,
          GuiStatics.IMAGE_SCROLL_BAR_THUMB.getHeight() - endHeight,
          GuiStatics.IMAGE_SCROLL_BAR_THUMB.getWidth(), endHeight);
      BufferedImage middle = GuiStatics.IMAGE_SCROLL_BAR_THUMB.getSubimage(0,
          16, GuiStatics.IMAGE_SCROLL_BAR_THUMB.getWidth(), BAR_LEN);
      g.drawImage(top, 0, y, null);
      if (bars > 0) {
        for (int i = 0; i < bars; i++) {
          g.drawImage(middle, 0, y + endHeight + i * BAR_LEN, null);

        }
      }
      g.drawImage(bottom, 0, y + endHeight + bars * BAR_LEN - 1, null);
    } else {
      int x = incrButton.getWidth() + 1;
      int distance = scrollbar.getWidth() - incrButton.getWidth()
          - decrButton.getWidth();
      x = x + distance * scrollbar.getValue()
          / (scrollbar.getMaximum() - scrollbar.getMinimum());
      int picWidth = GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getHeight();
      int endWidth = thumbBounds.width / 2;
      int bars = 0;
      if (endWidth > picWidth / 2) {
        endWidth = thumbBounds.width / 3;
        if (endWidth > BAR_LEN) {
          bars = thumbBounds.width / BAR_LEN;
          bars = bars - 2;
          endWidth = (thumbBounds.width - bars * BAR_LEN) / 2;
        } else {
          bars = 1;
        }
      }
      if (endWidth > GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getWidth() - 16) {
        endWidth = GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getWidth() - 16;
      }

      BufferedImage left = GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getSubimage(0, 0,
          endWidth, GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getHeight());
      BufferedImage right = GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getSubimage(
          GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getWidth() - endWidth, 0, endWidth,
          GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getHeight());
      BufferedImage middle = GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getSubimage(16,
          0, BAR_LEN, GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getHeight());
      g.drawImage(left, x, 0, null);
      if (bars > 0) {
        for (int i = 0; i < bars; i++) {
          g.drawImage(middle, x + endWidth + i * BAR_LEN, 0, null);

        }
      }
      g.drawImage(right, x + endWidth + bars * BAR_LEN - 1, 0, null);
    }
  }

  @Override
  protected void paintTrack(final Graphics g, final JComponent c,
      final Rectangle trackBounds) {
    if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
      GradientPaint gradient = new GradientPaint(0, 0, GuiStatics.COLOR_GREY_80,
          trackBounds.width, trackBounds.height, GuiStatics.COLOR_GREY_40,
          true);
      Graphics2D g2d = (Graphics2D) g;
      g2d.setPaint(gradient);
      g2d.fillRect(0, 0, scrollbar.getWidth(), scrollbar.getHeight());
      g2d.fillRect(0, incrButton.getHeight(), trackBounds.width,
          trackBounds.height);
    } else {
      GradientPaint gradient = new GradientPaint(0, 0, GuiStatics.COLOR_GREY_80,
          trackBounds.width, trackBounds.height, GuiStatics.COLOR_GREY_40,
          true);
      Graphics2D g2d = (Graphics2D) g;
      g2d.setPaint(gradient);
      g2d.fillRect(0, 0, scrollbar.getWidth(), scrollbar.getHeight());
      g2d.fillRect(0, incrButton.getHeight(), trackBounds.width,
          trackBounds.height);
    }

  }

}
