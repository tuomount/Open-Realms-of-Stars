package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.labels.StarFieldTextArea;
import org.openRealmOfStars.utilities.IOUtilities;

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
 * Credits view for Open Realm of Stars
 * 
 */

public class CreditsView extends BlackPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Text area to show credits
   */
  private StarFieldTextArea textArea;
  
  public CreditsView(ActionListener listener,String title, String version) throws IOException {
    String creditsText = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
        "\n\n\n\n\n\n\n\n\n\n\n"+
        "#"+title+" "+version+"\n\n"+
        "#Credits\n\n" +
        "#Programming and Design by\n\n" +
        "Tuomo Untinen\n\n" +
        "All graphics under CC-BY-SA 3.0 license.\n"+
        "See http://creativecommons.org/licenses/by-sa/3.0/\n"+
        "#Graphics by\n\n" +
        "Moons and planet made by Unnamed (Viktor.Hahn@web.de)\n"+
        "(http://opengameart.org/content/16-planet-sprites)\n\n"+
        "SunRed by Priest865 (http://opengameart.org/content/space-assets)\n\n"+
        "141 Military Icons Set by AngryMeteor.com - http://opengameart.org/content/140-military-icons-set-fixed\n\n"+
        "Nebulae, star field and space panel by Tuomo Untinen\n\n"+
        "Route dot by Tuomo Untinen\n\n"+
        "Space ships are done with Surt's modular ships\n\n"+
        "Fonts are under SIL Open Font License, Version 1.1.\n"+
        "#Fonts by\n\n"+
        "Cubellan font by Jyrki Ihalainen (yardan74@gmail.com)\n\n";
    InputStream is = CreditsView.class.getResourceAsStream("/resources/GPL2.txt");
    BufferedInputStream bis = new BufferedInputStream(is);
    DataInputStream dis = new DataInputStream(bis);
    String gpl2License="";
    try {
      gpl2License = IOUtilities.readTextFile(dis);      
    } finally {
      dis.close();
    }
    is = CreditsView.class.getResourceAsStream("/resources/fonts/Cubellan_v_0_7/Cubellan_License_SIL_OFL.txt");
    bis = new BufferedInputStream(is);
    dis = new DataInputStream(bis);
    String cubellanLicense="";
    try {
      cubellanLicense = IOUtilities.readTextFile(dis);
    } finally {
      dis.close();
    }
    creditsText = creditsText+"\n\n"+"#GNU GENERAL PUBLIC LICENSE Version 2, June 1991\n"+
        gpl2License+"\n\n"+"#SIL Open Font License, Version 1.1.\n\n"+cubellanLicense;
    this.setLayout(new BorderLayout());
    textArea = new StarFieldTextArea();
    textArea.setScrollText(creditsText,45);
    textArea.setText(creditsText);
    textArea.setSmoothScroll(true);
    textArea.setEditable(false);
    this.add(textArea,BorderLayout.CENTER);
    
    SpaceButton btn = new SpaceButton("OK", GameCommands.COMMAND_OK);
    btn.addActionListener(listener);
    this.add(btn,BorderLayout.SOUTH);


  }
  
   
  /**
   * Update Text area
   */
  public void updateTextArea() {
    if (textArea.getSmoothScrollNextRow()) {
      textArea.getNextLine();
    }
    repaint();
  }


}
