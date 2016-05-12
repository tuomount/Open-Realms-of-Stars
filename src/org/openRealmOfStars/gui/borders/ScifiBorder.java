package org.openRealmOfStars.gui.borders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.border.AbstractBorder;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.utilities.IOUtilities;

public class ScifiBorder extends AbstractBorder
{
    /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Corner piece for info panels
   */
  protected final static BufferedImage cornerImage = IOUtilities.loadImage(Tiles.class.getResource(
      "/resources/images/panel-corner.png"));

  /**
   * Title left piece
   */
  protected final static BufferedImage titleLeftImage = IOUtilities.loadImage(Tiles.class.getResource(
      "/resources/images/panel-title-left.png"));

  /**
   * Title center piece
   */
  protected final static BufferedImage titleCenterImage = IOUtilities.loadImage(Tiles.class.getResource(
      "/resources/images/panel-title-center.png"));

  /**
   * Title right piece
   */
  protected final static BufferedImage titleRightImage = IOUtilities.loadImage(Tiles.class.getResource(
      "/resources/images/panel-title-right.png"));

  /**
   * Gap on top
   */
  private int topGap;
  
  /**
   * Gap on left
   */
  private int leftGap;
  /**
   * Gap on right
   */
  private int rightGap;
  /**
   * Gap on bottom
   */
  private int bottomGap;
  
  /**
   * Title text in border
   */
  private String title;
  
  private static final Color MEDIUM_BLUE = new Color(71,73,82);
  private static final Color BRIGHT_BLUE = new Color(185,190,220);
  private static final Color TOP_LIGHT_BLUE = new Color(136,140,157);
  private static final Color TOP_LIGHT_SHADOW_BLUE = new Color(123,127,142);
  private static final Color TOP_DARK_BLUE = new Color(46,47,54);
  
  public ScifiBorder(String title) {
    leftGap = 15;
    rightGap = 15;
    bottomGap = 9;
    if ((title == null) || (title != null && title.isEmpty())) {
      topGap = 9;
      this.title = null;
    } else {
      topGap = 24;
      this.title = title;
    }    
  }

  @Override
  public void paintBorder(Component c,Graphics g,int x,int y,int width,
     int height) {
    Graphics2D g2d = (Graphics2D) g;
    //Left border;
    g2d.setColor(MEDIUM_BLUE);
    g2d.drawLine(x, y, x, y+height);
    g2d.drawLine(x+1, y, x+1, y+height);
    g2d.drawLine(x+3, y, x+3, y+height);
    g2d.drawLine(x+4, y, x+4, y+height);
    g2d.setColor(BRIGHT_BLUE);
    g2d.drawLine(x+2, y, x+2, y+height);

    //Right border;
    g2d.setColor(MEDIUM_BLUE);
    g2d.drawLine(x+width-1, y, x+width-1, y+height);
    g2d.drawLine(x+width-2, y, x+width-2, y+height);
    g2d.drawLine(x+width-4, y, x+width-4, y+height);
    g2d.drawLine(x+width-5, y, x+width-5, y+height);
    g2d.setColor(BRIGHT_BLUE);
    g2d.drawLine(x+width-3, y, x+width-3, y+height);
    
    // Top border
    g2d.setColor(TOP_LIGHT_BLUE);
    g2d.drawLine(x, y, x+width, y);
    g2d.drawLine(x, y+1, x+width, y+1);
    g2d.drawLine(x, y+2, x+width, y+2);
    g2d.setColor(TOP_LIGHT_SHADOW_BLUE);
    g2d.drawLine(x, y+3, x+width, y+3);
    g2d.setColor(BRIGHT_BLUE);
    g2d.drawLine(x, y+4, x+width, y+4);
    g2d.setColor(TOP_DARK_BLUE);
    g2d.drawLine(x, y+5, x+width, y+5);
    g2d.drawLine(x, y+6, x+width, y+6);

    // Bottom border
    g2d.setColor(TOP_LIGHT_BLUE);
    g2d.drawLine(x, y+height-7, x+width, y+height-7);
    g2d.drawLine(x, y+height-6, x+width, y+height-6);
    g2d.drawLine(x, y+height-5, x+width, y+height-5);
    g2d.setColor(TOP_LIGHT_SHADOW_BLUE);
    g2d.drawLine(x, y+height-4, x+width, y+height-4);
    g2d.setColor(BRIGHT_BLUE);
    g2d.drawLine(x, y+height-3, x+width, y+height-3);
    g2d.setColor(TOP_DARK_BLUE);
    g2d.drawLine(x, y+height-2, x+width, y+height-2);
    g2d.drawLine(x, y+height-1, x+width, y+height-1);
    
    //Corners
    
    g2d.drawImage(cornerImage, x, y, null);
    g2d.drawImage(cornerImage, x+width-cornerImage.getWidth(), y, null);
    g2d.drawImage(cornerImage, x, y+height-cornerImage.getHeight(), null);
    g2d.drawImage(cornerImage, x+width-cornerImage.getWidth(), y+height-cornerImage.getHeight(), null);
    
    if (title != null && !title.isEmpty()) {
      int textWidth = (int) GuiStatics.FONT_SMALL.getStringBounds(title,
          g2d.getFontRenderContext()).getWidth();
      BufferedImage centerPiece = new BufferedImage(textWidth, 
          titleCenterImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
      int repeats = textWidth/titleCenterImage.getWidth();
      int lastPieceLen = textWidth % titleCenterImage.getWidth();
      Graphics2D gr = centerPiece.createGraphics();
      for (int i=0;i<repeats;i++) {
        gr.drawImage(titleCenterImage, i*titleCenterImage.getWidth(), 0, null);
      }
      if (lastPieceLen > 0) {
        gr.drawImage(titleCenterImage.getSubimage(0, 0, lastPieceLen, titleCenterImage.getHeight()),
            repeats*titleCenterImage.getWidth(), 0, null);
      }
      g2d.drawImage(titleLeftImage, x+width/2-textWidth/2-titleLeftImage.getWidth(), y, null);
      g2d.drawImage(centerPiece, x+width/2-textWidth/2, y, null);
      g2d.drawImage(titleRightImage, x+width/2-textWidth/2+centerPiece.getWidth(), y, null);

      g2d.setColor(GuiStatics.COLOR_GOLD);
      g2d.setFont(GuiStatics.FONT_SMALL);
      g2d.drawString(title, x+width/2-textWidth/2, y+16);
      
    }

}


    @Override
    public Insets getBorderInsets(Component c)
    {
        return (getBorderInsets(c, new Insets(topGap, leftGap, bottomGap, 
            rightGap)));
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets)
    {
        insets.left = leftGap;
        insets.top = topGap;
        insets.right = rightGap;
        insets.bottom = bottomGap;
        return insets;
    }

    @Override
    public boolean isBorderOpaque()
    {
        return true;
    }
}