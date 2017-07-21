package org.openRealmOfStars.starMap.newsCorp;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* News Data class containing one single news and instructions
* how to make image
*
*/
public class NewsData {

  /**
   * Actual news text
   */
  private String newsText;

  /**
   * Image instructions
   */
  private String imageInstructions;

  /**
   * Constructor for creating empty news data
   */
  public NewsData() {
    setNewsText("");
    setImageInstructions("");
  }

  /**
   * Get the news Text
   * @return the newsText
   */
  public String getNewsText() {
    return newsText;
  }

  /**
   * Set news text
   * @param newsText the newsText to set
   */
  public void setNewsText(final String newsText) {
    this.newsText = newsText;
  }

  /**
   * Get the new image instructions
   * @return the imageInstructions
   */
  public String getImageInstructions() {
    return imageInstructions;
  }

  /**
   * Set the image instructions
   * @param imageInstructions the imageInstructions to set
   */
  public void setImageInstructions(final String imageInstructions) {
    this.imageInstructions = imageInstructions;
  }

  @Override
  public String toString() {
    if (newsText.length() > 30) {
      return newsText.substring(0, 29);
    }
    return newsText;
  }
}
