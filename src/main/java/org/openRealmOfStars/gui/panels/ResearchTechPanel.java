package org.openRealmOfStars.gui.panels;

import java.awt.Component;
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
 * Class for handling research tech
 *
 */

public class ResearchTechPanel extends InvisiblePanel {

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
   * Label to show tech focus
   */
  private IconLabel label;

  /**
   * Level Label to show
   */
  private IconLabel lvlLabel;

  /**
   * upgrade button
   */
  private IconButton btnUpgrade;

  /**
   * Slider for research panel
   */
  private JSlider slider;

  /**
   * Create Research Tech panel with - and + buttons and up arrow to
   * upgrade tech level.
   * @param parent Parent component to draw opaque
   * @param actionMinus ActionCommand for minus button
   * @param actionPlus ActionCommand for plus button
   * @param iconName Icon name to show
   * @param text Text for tech focus label
   * @param text2 Text for tech level label
   * @param actionUpgrade ActionCommand for upgrade button
   * @param sliderValue slider value
   * @param actionSlider slider action command
   * @param listener Action Listener
   */
  public ResearchTechPanel(final Component parent, final String actionMinus,
      final String actionPlus, final String iconName, final String text,
      final String text2, final String actionUpgrade, final int sliderValue,
      final String actionSlider, final ActionListener listener) {
    super(parent);
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    btnMinus = new IconButton(Icons.getIconByName(Icons.ICON_MINUS),
        Icons.getIconByName(Icons.ICON_MINUS_PRESSED), false, actionMinus,
        this);
    btnMinus.addActionListener(listener);
    this.add(Box.createRigidArea(new Dimension(5, 5)));
    this.add(btnMinus);
    InvisiblePanel invisible = new InvisiblePanel(this);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));

    label = new IconLabel(this, Icons.getIconByName(iconName), text);
    invisible.add(label);
    slider = new JSlider(0, 100, sliderValue);
    slider.setMinorTickSpacing(4);
    slider.setMajorTickSpacing(20);
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
    invisible.add(slider);
    lvlLabel = new IconLabel(this, Icons.getIconByName(Icons.ICON_EMPTY),
        text2);
    invisible.add(lvlLabel);

    this.add(invisible);

    btnUpgrade = new IconButton(Icons.getIconByName(Icons.ICON_ARROWUP),
        Icons.getIconByName(Icons.ICON_ARROWUP_PRESSED), false, actionUpgrade,
        this);
    btnUpgrade.setDisabledImage(
        Icons.getIconByName(Icons.ICON_ARROWUP_DISABLED).getIcon());
    btnUpgrade.addActionListener(listener);
    btnUpgrade.setEnabled(false);

    this.add(btnUpgrade);

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

  /**
   * Enabled or disable upgrade button
   * @param value true to enable and false to disable
   */
  public void setEnableUpgradeButton(final boolean value) {
    btnUpgrade.setEnabled(value);
  }

  /**
   * Set Upgrade button text to text
   * @param text Text for button
   */
  public void setUpgadeBtnText(final String text) {
    lvlLabel.setText(text);
  }

  /**
   * Set Upgrade button tool tip text
   * @param text Text for tool tip
   */
  public void setUpgadeBtnToolTip(final String text) {
    btnUpgrade.setToolTipText(text);
  }

}
