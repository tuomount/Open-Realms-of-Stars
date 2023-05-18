package org.openRealmOfStars.gui.buttons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JToolTip;
import javax.swing.border.EtchedBorder;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.utilies.GuiStatics;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2017, 2022  Tuomo Untinen
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
* Class for creating cool looking space check box.
*
*/
public class SpaceCheckBox extends JCheckBox {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Normal check box type
   */
  public static final int CHECKBOX_TYPE_NORMAL = 0;
  /**
   * Elder check box type
   */
  public static final int CHECKBOX_TYPE_ELDER = 1;
  /**
   * Space check icon
   */
  private Icon16x16 icon;

  /**
   * Checkbox type
   */
  private int type;
  /**
   * Construct check box with space style
   * @param text to shown in check box.
   */
  public SpaceCheckBox(final String text) {
    super(text);
    type = CHECKBOX_TYPE_NORMAL;
    setSpaceIcon(Icons.getIconByName(Icons.ICON_CHECKBOX));
    setBackground(GuiStatics.getPanelBackground());
    setForeground(GuiStatics.COLOR_GREY_40);
    setBorderPainted(true);
  }

  /**
   * Set type for checkbox.
   * @param type Checkbox type
   */
  public void setType(final int type) {
    this.type = type;
  }

  @Override
  public JToolTip createToolTip() {
    JToolTip toolTip = super.createToolTip();
    toolTip.setForeground(GuiStatics.getCoolSpaceColor());
    toolTip.setBackground(GuiStatics.getCoolSpaceColorDark());
    toolTip.setBorder(BorderFactory
        .createLineBorder(GuiStatics.getCoolSpaceColorDarker()));
    return toolTip;
  }

  /**
   * Set space Icon, Null to disable
   * @param spaceButtonIcon Icon16x16 icon
   */
  private void setSpaceIcon(final Icon16x16 spaceButtonIcon) {
    this.icon = spaceButtonIcon;
    Dimension size = this.getPreferredSize();
    if (this.icon != null) {
      size.width = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
          getText()) + 20 + 16;
      size.height = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(),
          getText()) + 10;
    } else {
      size.width = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
          getText()) + 20;
      size.height = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(),
          getText()) + 10;
    }
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
  }

  @Override
  protected void paintComponent(final Graphics g) {
    if (this.getModel().isPressed()) {
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    } else {
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }
    Insets inset = this.getBorder().getBorderInsets(this);
    int sx = inset.left;
    int sy = inset.top;
    int width = getWidth() - inset.left - inset.right;
    int height = getHeight() - inset.top - inset.bottom;
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(getBackground());
    g2d.fillRect(sx, sy, width, height);

    if (this.isEnabled()) {
      if (this.getModel().isRollover()) {
        g2d.setColor(GuiStatics.getDeepSpaceActivityColor());
      } else {
        g2d.setColor(getForeground());
      }
    } else {
      g2d.setColor(GuiStatics.getCoolSpaceColorDarker());
    }
    String[] texts = getText().split("\n");
    g.setFont(GuiStatics.getFontCubellan());
    int longest = 0;
    for (int i = 0; i < texts.length; i++) {
      if (texts[i].length() > texts[longest].length()) {
        longest = i;
      }
    }
    int textWidth = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
        texts[longest]);
    int textHeight = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(),
        texts[longest]);
    int offsetX = width / 2 - textWidth / 2 + sx;
    if (icon != null) {
      offsetX = offsetX + 16;
      if (this.isSelected()) {
        if (type == CHECKBOX_TYPE_NORMAL) {
          icon = Icons.getIconByName(Icons.ICON_CHECKBOX_TICK);
        }
        if (type == CHECKBOX_TYPE_ELDER) {
          icon = Icons.getIconByName(Icons.ICON_ELDER_BOX_TICK);
        }
      } else {
        if (type == CHECKBOX_TYPE_NORMAL) {
          icon = Icons.getIconByName(Icons.ICON_CHECKBOX);
        }
        if (type == CHECKBOX_TYPE_ELDER) {
          icon = Icons.getIconByName(Icons.ICON_ELDER_BOX);
        }
      }
      icon.draw(g2d, sx, sy);
    }

    if (offsetX < 0) {
      offsetX = sx;
    }
    for (int i = 0; i < texts.length; i++) {
      textWidth = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
          texts[i]);
      offsetX = width / 2 - textWidth / 2 + sx;
      g2d.drawString(texts[i], offsetX, textHeight * (i + 1) + sy);
    }

  }

}
