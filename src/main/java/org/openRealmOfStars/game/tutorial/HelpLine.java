package org.openRealmOfStars.game.tutorial;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2019  Tuomo Untinen
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
* HelpLine for single tutorial help.
*
*/
public class HelpLine {

  /**
   * Tutorial index
   */
  private int index;

  /**
   * Tutorial category
   */
  private String category;

  /**
   * Tutorial line title
   */
  private String title;

  /**
   * Tutorial text itself.
   */
  private String text;


  /**
   * Constructor for help line
   * @param index Help line index
   */
  public HelpLine(final int index) {
    this.index = index;
    category = "";
    title = "";
    text = "";
  }

  /**
   * Get help line category
   * @return Category as String
   */
  public String getCategory() {
    return category;
  }

  /**
   * Set help line category
   * @param category Category as string
   */
  public void setCategory(final String category) {
    this.category = category;
  }

  /**
   * Get Help line title
   * @return Title as a String
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set help line title.
   * @param title Title for help line.
   */
  public void setTitle(final String title) {
    this.title = title;
  }

  /**
   * Get Help line text
   * @return Text as string
   */
  public String getText() {
    return text;
  }

  /**
   * Set help line text.
   * @param text Text for help line.
   */
  public void setText(final String text) {
    this.text = text;
  }

  /**
   * Get Help line index
   * @return Index
   */
  public int getIndex() {
    return index;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(index);
    sb.append(": ");
    sb.append(getCategory());
    sb.append(" - ");
    sb.append(getTitle());
    return sb.toString();
  }

}
