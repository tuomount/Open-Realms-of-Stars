package org.openRealmOfStars.gui.borders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.border.AbstractBorder;

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
    bottomGap = 10;
    if ((title == null) || (title != null && title.isEmpty())) {
      topGap = 10;
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
    g2d.drawLine(x+leftGap, y+topGap, x+leftGap, y+topGap+height-bottomGap);
    g2d.drawLine(x+leftGap-1, y+topGap, x+leftGap-1, y+topGap+height-bottomGap);
    g2d.drawLine(x+leftGap-3, y+topGap, x+leftGap-3, y+topGap+height-bottomGap);
    g2d.drawLine(x+leftGap-4, y+topGap, x+leftGap-4, y+topGap+height-bottomGap);
    g2d.setColor(BRIGHT_BLUE);
    g2d.drawLine(x+leftGap-2, y+topGap, x+leftGap-2, y+topGap+height-bottomGap);
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