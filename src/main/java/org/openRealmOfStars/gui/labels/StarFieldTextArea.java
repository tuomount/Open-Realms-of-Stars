package org.openRealmOfStars.gui.labels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.borders.SimpleBorder;

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
 * Customized Text Area with star field background
 * 
 */

public class StarFieldTextArea extends JTextArea {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
  * Y axel offset where to draw text
  */
  private static final int Y_OFFSET = 18;
  /**
   * Does text have autoscroll on
   */
  private boolean autoScroll = false;
  /**
   * Arrays of string used when scorlling the text
   */
  private String[] scrollText;
  
  /**
   * Text to show in InfoTextArea
   */
  private String textToShow;
  /**
   * Number of lines to shown auto scrolling the text
   */
  private int numberOfLines=0;
  /**
   * Current line when scrolling
   */
  private int currentLine= 0;
  /**
   * Is smooth scrolling on or non smooth
   */
  private boolean smoothScroll=false;
  /**
   * Smooth scrolling for single line done and ready to move next line
   */
  private boolean smoothScrollNextRow=false;
  
  public boolean isSmoothScroll() {
    return smoothScroll;
  }


  public void setSmoothScroll(boolean smoothScroll) {
    this.smoothScroll = smoothScroll;
  }

  /**
   * Offset for smooth scrolling
   */
  private int smoothScrollY=0;
  

  /**
   * Construct InfoTextArea with default but set size on rows and
   * columns.
   * @param rows Number of rows
   * @param columns Number of columns
   */
  public StarFieldTextArea(int rows, int columns) {
    super(rows,columns);
    this.setForeground(Color.white);
    this.setBackground(Color.BLACK);
    autoScroll = false;
    this.setBorder(new SimpleBorder());
    this.addMouseListener(new CreditMouseListener());

  }

  /**
   * Construct InfoTextArea with defaults.
   */
  public StarFieldTextArea() {
    super(17,10);
    this.setForeground(Color.white);
    this.setBackground(Color.BLACK);
    autoScroll = false;
    this.setBorder(new SimpleBorder());
    this.addMouseListener(new CreditMouseListener());
  }
  
  /**
   * Construct InfoTextArea with certain text.
   * @param text the text to be displayed, or null
   */
  public StarFieldTextArea(String text){
    super(text);
    textToShow = text;
    this.setForeground(Color.white);
    this.setBackground(Color.BLACK);
    autoScroll = false;
    this.setBorder(new SimpleBorder());
    this.addMouseListener(new CreditMouseListener());
  }
  
  
  
  @Override
  public String getText() {
    return textToShow;
  }


  @Override
  public void setText(String t) {
    textToShow = t;
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
    if(currentLine >= scrollText.length) {
      currentLine =0;
    }
  }

  /**
   * Scroll to previous line
   */
  public void getPrevLine() {
    currentLine--;
    if(currentLine < 0) {
      currentLine =scrollText.length-1;
    }
  }

  /**
   * Set Scrollable text
   * @param text String
   * @param lines number of shown lines
   */
  public void setScrollText(String text, int lines) {
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
   * @param width The character width
   */
  public void setCharacterWidth(int width) {
    if (width < 1) {
      customCharWidth = -1;
    } else {
      customCharWidth = width;
    }
  }
  
  @Override
  public void paintImmediately(int x, int y, int w, int h) {
    super.paintImmediately(0, 0, getWidth(), getHeight());
  }


  @Override
  protected void paintComponent(Graphics g) {
    this.setCaretPosition(this.getDocument().getLength());  
    g.setColor(Color.black);
    Insets inset = this.getBorder().getBorderInsets(this);
    int sx = inset.left;
    int sy = inset.top;
    int width = getWidth()-inset.left-inset.right;
    int height = getHeight()-inset.top-inset.bottom;    
    g.fillRect(sx, sx, width, height);
    Graphics2D g2d = (Graphics2D) g;
    g2d.drawImage(GuiStatics.starFieldImage,-25,-25,null);

    g.setFont(GuiStatics.getFontCubellan());
    if (getText() != null ) {
      StringBuilder sb = new StringBuilder();
      if (autoScroll == false) {
        sb = new StringBuilder(this.getText());
      } else {
        for (int i=0;i<numberOfLines;i++) {
          if (i+currentLine<scrollText.length) {
            sb.append(scrollText[i + currentLine]).append("\n");
          }
        }
      }
      if (this.getLineWrap()== true) {
        int lastSpace = -1;
        int rowLen = 0;
        int maxRowLen = width/6;
        if (customCharWidth > 0) {
          maxRowLen = width/customCharWidth;
        } 
        for (int i=0;i<sb.length();i++) {
          if (sb.charAt(i)==' ') {
            lastSpace = i;
          }
          if (sb.charAt(i)=='\n') {
            lastSpace = -1;
            rowLen=0;
          } else {
            rowLen++;
          }
          if (rowLen > maxRowLen) {
            if (lastSpace != -1) {
              sb.setCharAt(lastSpace, '\n');
              rowLen=i-lastSpace;
              lastSpace = -1;
              
            } else {
              sb.setCharAt(i, '\n');
              lastSpace = -1;
              rowLen=0;
            }
          }
        }
      }
      String[] texts = sb.toString().split("\n");
      for (int i=0;i<texts.length;i++) {
        g.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS);
        boolean bigFont = false;
        
        if (!smoothScroll) {
          g.drawString(texts[i], sx+3, sy+i*Y_OFFSET+Y_OFFSET);
          g.drawString(texts[i], sx+1, sy+i*Y_OFFSET+Y_OFFSET);
          g.drawString(texts[i], sx+2, sy+i*Y_OFFSET-1+Y_OFFSET);
          g.drawString(texts[i], sx+2, sy+i*Y_OFFSET+1+Y_OFFSET);
          g.setColor(GuiStatics.COLOR_GREEN_TEXT);
          g.drawString(texts[i], sx+2, sy+i*Y_OFFSET+Y_OFFSET);
        } else {
          String line = texts[i];
          if (line.startsWith("#")) {
            g.setFont(GuiStatics.getFontCubellanBold());
            line = line.substring(1);
            bigFont = true;
          } else {
            g.setFont(GuiStatics.getFontCubellan());
          }
          int w = GuiStatics.getTextWidth(g.getFont(), line);
          w = this.getWidth()/2+sx-w/2;
          if (bigFont) {
            g.setColor(GuiStatics.COLOR_SPACE_YELLOW_DARK);
          }
          g.drawString(line, w+3, sy+i*Y_OFFSET+Y_OFFSET-smoothScrollY);
          g.drawString(line, w+1, sy+i*Y_OFFSET+Y_OFFSET-smoothScrollY);
          g.drawString(line, w+2, sy+i*Y_OFFSET-1+Y_OFFSET-smoothScrollY);
          g.drawString(line, w+2, sy+i*Y_OFFSET+1+Y_OFFSET-smoothScrollY);
          if (bigFont) {
            g.setColor(GuiStatics.COLOR_SPACE_YELLOW);
          } else {
            g.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE_TRANS);
          }
          g.drawString(line, w+2, sy+i*Y_OFFSET+Y_OFFSET-smoothScrollY);
        }
        
      }
    }
    smoothScrollY++;
    if (smoothScrollY==Y_OFFSET) {
      smoothScrollY=0;
      smoothScrollNextRow = true;
    }

    
  }
  
  public boolean getSmoothScrollNextRow() {
    if (smoothScrollNextRow) {
      smoothScrollNextRow = false;
      return true;
    }
    return false;
  }
  
  private class CreditMouseListener extends MouseAdapter {

    
    
    @Override
    public void mouseClicked(MouseEvent e) {
      getNextLine();
      getNextLine();
      getNextLine();
      getNextLine();
      getNextLine();
    }
  }


}
