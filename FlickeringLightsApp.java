/**
 * 
 * COSC326 Etude 9 Flickering Lights with GUI 20/05/2019
 * Ben Highsted (5536340), Matthew Neil(7388248).
 * 
 * Generates a GUI which the user can create a light switch circuit.
 * 
 * The Application Class simply creates a frame for the contents in FlickeringLights to be drawn to.
 * 
 */

import javax.swing.*;

public class FlickeringLightsApp {
  public static void main(String[] args){

    JFrame frame = new JFrame("Flickering Lights");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    frame.getContentPane().add(new FlickeringLights());
    
    frame.pack();
    frame.setVisible(true);
  }
} 