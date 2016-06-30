package org.openRealmOfStars.gui.buttons;



import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolTip;
import javax.swing.border.EtchedBorder;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.icons.Icon16x16;


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
 * Class for handling IconButton. Separate images for normal, pressing
 * and disabled image.
 *
 */

public class IconButton extends JButton {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Icon when not pressed
   */
  private BufferedImage notPressedImage;
  /**
   * Icon when pressed
   */
  private BufferedImage pressedImage;
  /**
   * Icon when disabled
   */
  private BufferedImage disabledImage;

  /**
   * Is Border present on button
   */
  private boolean border;

  private Component parent;
  
  /**
   * Icon button which has only icon
   * @param notPressedImage BufferedImage
   * @param pressedImage BufferedImage
   * @param border Booolean
   * @param actionCommand String
   */
  public IconButton(Icon16x16 notPressedImage, 
                         Icon16x16 pressedImage, boolean border,
                         String actionCommand,
                         Component parent) {
    super();
    ImageIcon icon = new ImageIcon(notPressedImage.getIcon(), "");
    this.setIcon(icon);
    this.setDisabledIcon(icon);
    this.setNotPressedImage(notPressedImage.getIcon());
    this.setPressedImage(pressedImage.getIcon());
    this.setActionCommand(actionCommand);
    this.setBorder(border);
    this.parent = parent;
    if (border) {
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    } else {
      this.setBorder(BorderFactory.createEmptyBorder());

    }
  }

  
  
  
  @Override
  protected void paintComponent(Graphics g) {
    parent.repaint();
    int x = getWidth()/2;
    int y = getHeight()/2;
    if (this.isEnabled()) {
      if (this.getModel().isPressed()) {
        x = x -getPressedImage().getWidth()/2;
        y = y -getPressedImage().getHeight()/2;
        if(isBorder()) {
          this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));        
          g.drawImage(getPressedImage(), x+2,y+2, null);        
        } else {
          g.drawImage(getPressedImage(), x,y, null);
        }
      } else {
        x = x -getNotPressedImage().getWidth()/2;
        y = y -getNotPressedImage().getHeight()/2;
        if(isBorder()) {
          this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
          g.drawImage(getNotPressedImage(), x+2, y+2, null);
        } else {
          g.drawImage(getNotPressedImage(), x, y, null);
        }
      }
    } else {
      x = x -getPressedImage().getWidth()/2;
      y = y -getPressedImage().getHeight()/2;
      if(isBorder()) {
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));        
        g.drawImage(getDisabledImage(), x+2,y+2, null);        
      } else {
        g.drawImage(getDisabledImage(), x,y, null);
      }
     
    }
  }

  @Override
  public JToolTip createToolTip()
  {
      JToolTip toolTip = super.createToolTip();
      toolTip.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
      toolTip.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
      toolTip.setBorder(BorderFactory.createLineBorder(GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER));
      return toolTip;
  }

  public void setNotPressedImage(BufferedImage notPressedImage) {
    this.notPressedImage = notPressedImage;
  }


  public BufferedImage getNotPressedImage() {
    return notPressedImage;
  }


  public void setPressedImage(BufferedImage pressedImage) {
    this.pressedImage = pressedImage;
  }


  public BufferedImage getPressedImage() {
    return pressedImage;
  }


  public void setBorder(boolean border) {
    this.border = border;
  }


  public boolean isBorder() {
    return border;
  }




  public BufferedImage getDisabledImage() {
    if (disabledImage == null) {
      return pressedImage;
    } 
    return disabledImage;
  }




  public void setDisabledImage(BufferedImage disabledImage) {
    this.disabledImage = disabledImage;
  }
  
}
