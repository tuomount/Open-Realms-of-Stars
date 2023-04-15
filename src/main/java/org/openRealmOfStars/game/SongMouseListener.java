package org.openRealmOfStars.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2023 Tuomo Untinen
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
* Class for hiding song name when moving mouse on area.
*
*/
public class SongMouseListener implements MouseListener {

  /**
   * Is mouse in listener area?
   */
  private boolean mouseInArea;

  /**
   * Opacity Count;
   */
  private int opacityCount;

@Override
  public void mouseClicked(final MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mousePressed(final MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseEntered(final MouseEvent e) {
    mouseInArea = true;
  }

  @Override
  public void mouseExited(final MouseEvent e) {
    mouseInArea = false;
  }

  /**
   * Is Mouse in area?
   *
   * @return True if mouse is in area, otherwise boolean;
   */
  public boolean isMouseInArea() {
    return mouseInArea;
  }

  /**
   * Get opacity counter value.
   * @return the opacityCount
   */
  public int getOpacityCount() {
    return opacityCount;
  }

  /**
   * Set Opacity counter.
   * @param opacityCount the opacityCount to set
   */
  public void setOpacityCount(final int opacityCount) {
    this.opacityCount = opacityCount;
  }

}
