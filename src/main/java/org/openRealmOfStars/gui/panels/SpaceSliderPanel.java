package org.openRealmOfStars.gui.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.labels.IconLabel;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2018  Tuomo Untinen
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
 * Class for Space style slider
 *
 */

public class SpaceSliderPanel extends SpaceGreyPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Minus button
   */
  private IconButton btnMinus;

  /**
   * Plus button
   */
  private IconButton btnPlus;

  /**
   * Label to show text on slider
   */
  private IconLabel label;

  /**
   * Slider for research panel
   */
  private JSlider slider;

  /**
   * Create space slider panel with - and + buttons and label
   * @param actionMinus ActionCommand for minus button
   * @param actionPlus ActionCommand for plus button
   * @param iconName Icon name to show
   * @param text Text for label
   * @param minValue Minimum value in slider
   * @param maxValue Maximum value in slider
   * @param sliderValue slider value
   * @param actionSlider slider action command
   * @param listener Action Listener
   */
  public SpaceSliderPanel(final String actionMinus,
      final String actionPlus, final String iconName, final String text,
      final int minValue, final int maxValue, final int sliderValue,
      final String actionSlider, final ActionListener listener) {
    super();
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    btnMinus = new IconButton(Icons.getIconByName(Icons.ICON_MINUS),
        Icons.getIconByName(Icons.ICON_MINUS_PRESSED), false, actionMinus,
        this);
    btnMinus.addActionListener(listener);
    this.add(Box.createRigidArea(new Dimension(5, 5)));
    this.add(btnMinus);
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    label = new IconLabel(null, Icons.getIconByName(iconName), text);
    panel.add(label);
    slider = new JSlider(minValue, maxValue, sliderValue);
    slider.setMinorTickSpacing(1);
    slider.setMajorTickSpacing(10);
    slider.setPaintTicks(true);
    slider.setSnapToTicks(true);
    slider.setBackground(GuiStatics.COLOR_GREYBLUE);
    slider.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    slider.addKeyListener(null);
    slider.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(final ChangeEvent e) {
        if (e.getSource() instanceof JSlider) {
          JSlider slide = (JSlider) e.getSource();
          if (slide.getValue() % 4 == 0) {
            listener.actionPerformed(new ActionEvent(e, 0, actionSlider));
          }
        }
      }
    });
    panel.add(slider);

    this.add(panel);
    btnPlus = new IconButton(Icons.getIconByName(Icons.ICON_PLUS),
        Icons.getIconByName(Icons.ICON_PLUS_PRESSED), false, actionPlus, this);
    btnPlus.addActionListener(listener);
    this.add(btnPlus);
    this.add(Box.createRigidArea(new Dimension(5, 5)));
  }

  /**
   * Set slider value
   * @param value Slider value to set
   */
  public void setSliderValue(final int value) {
    slider.setValue(value);
  }

  /**
   * Get slider value
   * @return Slider value
   */
  public int getSliderValue() {
    return slider.getValue();
  }

  /**
   * Set slider minor ticks. Default is 1.
   * @param ticks Ticks to set
   */
  public void setSliderMinorTick(final int ticks) {
    slider.setMinorTickSpacing(ticks);
  }

  /**
   * Set slider major ticks. Default is 10.
   * @param ticks Ticks to set
   */
  public void setSliderMajorTick(final int ticks) {
    slider.setMinorTickSpacing(ticks);
  }

  /**
   * Has user changed the slider value
   * @return True if slider value has been changed
   */
  public boolean isSliderChanged() {
    return slider.getValueIsAdjusting();
  }

  /**
   * Set label text
   * @param text to show
   */
  public void setText(final String text) {
    label.setText(text);
  }

  /**
   * Set tool tip for label itself
   * @param text Tool tip text
   */
  public void setLabelToolTip(final String text) {
    label.setToolTipText(text);
  }

  /**
   * Set Minus button tool tip
   * @param text Tool tip to show
   */
  public void setMinusToolTip(final String text) {
    btnMinus.setToolTipText(text);
  }

  /**
   * Set Plus button tool tip
   * @param text Tool tip to show
   */
  public void setPlusToolTip(final String text) {
    btnPlus.setToolTipText(text);
  }

}
