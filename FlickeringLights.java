/**
 * 
 * COSC326 Etude 9 Flickering Lights with GUI 20/05/2019
 * Ben Highsted (5536340), Matthew Neil(7388248).
 * 
 * Generates a GUI which the user can create a light switch circuit.
 * After the circuit has been created, the user can solve it themselves by clicking on the switches,
 * click a button which shows what order the buttons need to be pressed, or click a button which will solve the
 * circuit for you.
 * There are also options to reset all the lights to on (which is the default) and to display the circuit (connecting
 * lines to represent how the switches are connected).
 * 
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.util.Scanner;
import java.awt.event.*;

public class FlickeringLights extends JPanel{  
  
  public boolean circuitOn = false;//most definitions are public as they are used and changed in multiple methods
  public JPanel lights;
  public JPanel lightsPanel;
  public LightsPanel graphics;
  public JButton visual, solve, options, instructions, circuit, example1, example2;
  public JLabel defineLights, defineEdges, textInstructions, textSolve, directions, directions2, directions3, directions4, errorLights, errorEdges;
  public JTextField textLights, textEdges;
  public int[][] switches;
  public String[] lightsArray;
  public int[] lightsOn;
  public JButton[] lightsButtons;
  public String[] instructionList;
  public int position = 0;
  public int[] xval = {275, 440, 520, 520, 440, 275, 110, 30, 30, 110};
  public int[] yval = {10, 96, 222, 328, 454, 540, 454, 328, 222, 96};
  public Color colorBrown = new Color(165, 42, 42);
  //public Color[] colors = {Color.black, Color.red, Color.blue, Color.orange, Color.green, Color.gray, Color.pink, colorBrown, Color.magenta, Color.cyan};
  public Color[] colors = {new Color(0, 0, 0), new Color(240, 163, 255), new Color(0, 117, 255), new Color(153, 63, 0), new Color(76, 0, 92), //26 unique colors
    new Color(25, 25, 25), new Color(0, 92, 49), new Color(53, 206, 72), new Color(255, 204, 153), new Color(128, 128, 128), new Color(148, 255, 181), 
    new Color(143, 124, 0), new Color(157, 204, 9), new Color(192, 0, 136), new Color(0, 51, 128), new Color(255, 164,  5), new Color(255, 168, 187), 
    new Color(66, 102, 0), new Color(255, 0, 16), new Color(94, 241, 242), new Color(0, 153, 143), new Color(224, 255, 102), new Color(116, 10, 255), 
    new Color(153, 0, 0), new Color(224, 255, 102), new Color(116, 10, 255), new Color(153, 0, 0), new Color(255, 255, 128), new Color(255, 255, 0), 
    new Color(255, 80, 5)};
  public String[] circuitEdges;
  public boolean new_size = false;
  public ButtonListener listener;
  public int[] x, y;
  
  public FlickeringLights(){ 
    listener = new ButtonListener();
    
    defineLights = new JLabel();//code below creates buttons and text fields and sets their locations
    textLights = new JTextField(30);
    defineEdges = new JLabel();
    textEdges = new JTextField(30);
    textInstructions = new JLabel();
    textSolve = new JLabel();
    example1 = new JButton();
    example2 = new JButton();
    errorLights = new JLabel();
    errorEdges = new JLabel();
    directions = new JLabel();
    directions2 = new JLabel();
    directions3 = new JLabel();
    directions4 = new JLabel();
    
    example1.setText("A");
    example2.setText("A");
    example1.setBackground(Color.yellow);
    example1.setOpaque(true);
    example1.setBounds(5, 498, 50, 50);
    example2.setBounds(5, 550, 50, 50);
    
    defineLights.setText("Define lights:");
    errorLights.setText("");
    defineLights.setBounds(10, 2, 395, 40);
    textLights.setBounds(5, 35, 395, 31);
    errorLights.setBounds(102, 2, 280, 40);
    
    defineEdges.setText("Define Set of Edges:");
    errorEdges.setText("");
    defineEdges.setBounds(10, 60, 395, 31);
    textEdges.setBounds(5, 85, 395, 31);
    errorEdges.setBounds(145, 55, 280, 40);
    
    directions.setText("When a switch has a yellow border, its turned on.");
    directions.setBounds(56, 510, 340, 31);
    
    directions2.setText("<html><p>When a switch has no border, its <u><b>not</u> turned on.");
    directions2.setBounds(56, 559, 340, 31);
    
    directions3.setText("<html><p>Lights must be single character (a-Z) inputs, each with a <br>space inbetween.<br>"
                          + "Edges must be no more than two entered at a time (i.e. AB) but each light can have "
                          + "multiple edges (i.e. AB AC BA).</html>");
    directions3.setBounds(5, 210, 390, 70);
    
    directions4.setText("<html><p>After entering the lights and edges, <u>Visualise</u> will create the circuit on the right "
                          + "side of the screen.<br><br><u>Show circuit</u> will generate lines that show how each switch affects "
                          + "the other lights. Each line color matches the color of the switches text.<br>"
                          + "<u>Reset lights</u> will return all lights to their turned on state.<br><u>Instructions</u> will "
                          + "return the order lights must be pressed to turn all/the maximum lights off. <br>"
                          + "<u>Solve Step by Step</u> will complete the problem for you step by step, so that everytime "
                          + "you press it, it will solve one step for you. It will also display what step it took.");
    directions4.setBounds(5, 250, 390, 250);
    
    visual = new JButton("Visualise"); 
    solve = new JButton("Solve Step by Step");
    options = new JButton("Reset Lights");
    instructions = new JButton("Instructions");
    circuit = new JButton("Show Circuit");
    
    solve.setEnabled(false);//sets the buttons to false so that they cant be pressed before visualise
    options.setEnabled(false);
    instructions.setEnabled(false);
    circuit.setEnabled(false);
    
    textEdges.addActionListener(listener);//adds listeners for text boxes
    textLights.addActionListener(listener);

    visual.addActionListener(listener);
    solve.addActionListener(listener);
    options.addActionListener(listener);
    instructions.addActionListener(listener);
    circuit.addActionListener(listener);
    example1.addActionListener(listener);
    
    visual.setBounds(2, 120, 140, 30);
    circuit.setBounds(132, 120, 140, 30);
    options.setBounds(262, 120, 140, 30);
    
    instructions.setBounds(65, 145, 140, 30);
    solve.setBounds(195, 145, 140, 30);
    
    textInstructions.setText("Enter some lights to get started.");//default text that fills white space
    textInstructions.setBounds(5, 160, 395, 60);
    textSolve.setBounds(2, 170, 395, 30);
    
    lights = new JPanel();//creates a new panel and adds all the previously created items to it
    lights.setLayout(null);
    setPreferredSize(new Dimension(1010, 610));
    lights.setPreferredSize(new Dimension(400, 600)); 
    
    lights.add(defineLights);
    lights.add(textLights);
    lights.add(defineEdges);
    lights.add(textEdges);
    lights.add(visual);
    lights.add(circuit);
    lights.add(options);
    lights.add(instructions);
    lights.add(solve);
    lights.add(textInstructions); 
    lights.add(directions);
    lights.add(directions2);
    lights.add(directions3);
    lights.add(directions4);
    lights.add(example1);
    lights.add(example2);
    lights.add(errorLights);
    lights.add(errorEdges);
    
    add(lights);
    
    lightsPanel = new JPanel();
    
    graphics = new LightsPanel();//adds the paint component to a JPanel
    graphics.setBounds(0, 0, 600, 600);
    lightsButtons = new JButton[0];
    lightsPanel.setLayout(null);//null layout so we can draw over it
    lightsPanel.setPreferredSize(new Dimension(600, 600));
    
    
    lightsPanel.setVisible(true);
    lightsPanel.add(graphics);
    add(lightsPanel);
    
  }
  
  private class ButtonListener implements ActionListener {
    
    public int[][] switches;
    public int[] smallest;
    public boolean completed = false;
    public int maxLights;
    
    /**
     * Method recursively trys all possible solutions
     * From https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
     */
    public void combinationUtil(int arr[], int data[], int start, int end, int index, int r, int expected){
      if (index == r){ 
        for(int i = 0; i < data.length; i++){
          for(int j = 0; j < switches[data[i]].length; j++){
            if(switches[data[i]][j] == 1){
              if(lightsOn[j] == 1){
                lightsButtons[j].setOpaque(false);
                lightsOn[j] = 0;
              } else {
                lightsButtons[j].setOpaque(true);
                lightsOn[j] = 1;
              }
            }
          }
        }
        int numLightsOn = 0;
        for(int i = 0; i < lightsOn.length; i++){
          if(lightsOn[i] == 1){
            numLightsOn++;
          }
        }
        if(numLightsOn == expected){
          instructionList = new String[data.length];
          smallest = new int[data.length];
          for(int i = 0; i < data.length; i++){
            smallest[i] = data[i];
            instructionList[i] = lightsArray[data[i]];
            maxLights = expected;
            completed = true;
          }
        } 
        
        for(int i = 0; i<lightsArray.length; i++){
          lightsOn[i] = 1;
        }
        position = 0;
        return;
        
      } 
      
      for (int i = start; i <= end && end - i + 1 >= r - index; i++) { 
        data[index] = arr[i]; 
        combinationUtil(arr, data, i + 1, end, index + 1, r, expected); 
      } 
    } 
    
    public void printCombination(int arr[], int n, int r, int e){ 
      int data[] = new int[r]; 
      combinationUtil(arr, data, 0, n-1, 0, r, e); 
    } 
    
    public void solveStepByStep(){
      int maximum = lightsOn.length;
      int[]combos = new int[lightsOn.length];
      for(int i=0; i < maximum; i++){
        combos[i] = i;
      }
      int e = 0;
      while(true){
        for(int r = lightsOn.length; r > 0; r--){
          printCombination(combos, maximum, r, e);
        }
        if(completed){
          completed = false;
          break;
        }else{
          e++;
        }
      }
      for(int i = 0; i<lightsArray.length; i++){
        lightsOn[i] = 1;
        lightsButtons[i].setOpaque(true);//if the lights are on, set the background color to visible
      } 
    }
    
    public void actionPerformed(ActionEvent buttonPressed) {
      //now allows enter to trigger the visual event
      if (buttonPressed.getSource().equals(visual) || buttonPressed.getSource().equals(textLights) || buttonPressed.getSource().equals(textEdges)){//if Visualise button is pressed
        String input = textLights.getText(); 
        boolean errors = false;
        
        input = input.trim();
        input = input.toUpperCase();
        input = input.replaceAll(" +", " ");
        lightsArray = input.split(" ");
        solve.setEnabled(false);
        options.setEnabled(false);
        instructions.setEnabled(false);
        circuit.setEnabled(false);
        while(true){//some basic validation to ensure the buttons and edges entered are okay
          if(input.length() == 0){
            errorLights.setText("<html><font color='red'>Lights field is empty!</font></html>");
            errors = true;
            break;
          } 
          if (input.split(" ").length > 10){
            errorLights.setText("<html><font color='red'>More than 10 lights are entered!</font></html>");
            //errors = true;
            //break;
          }
          for(int i=0; i<lightsArray.length; i++){
            if(lightsArray[i] == ""){
              errorLights.setText("<html><font color='red'>Light name must be a single letter!</font></html>");
              errors = true;
              break;
            } else if(!(Character.isLetter(lightsArray[i].charAt(0)))){
              errorLights.setText("<html><font color='red'>Light name must be a single letter!</font></html>");
              errors = true;
              break;
            }
            if(lightsArray[i].length() > 1){
              errorLights.setText("<html><font color='red'>Light name must be a single letter!</font></html>");
              errors = true;
              break;
            }
          }
          
          
          for(int i=0; i<lightsArray.length; i++){
            for(int j=0; j<lightsArray.length; j++){
              if(i != j){
                if(lightsArray[i].equals(lightsArray[j])){
                  errorLights.setText("<html><font color='red'>Light names must all be unique!</font></html>");
                  errors = true;
                }
              }
            }
          }
          break;
        }
        
        
        if(!errors){
          errorLights.setText("");
          
          int firstPos = 0;
          int secondPos = 0;
          int size = lightsArray.length;
          switches = new int[size][size];
          
          input = textEdges.getText();
          input = input.trim();
          input = input.toUpperCase();
          input = input.replaceAll(" +", " ");
          
          circuit.setEnabled(false);
          circuitOn = false;
          
          if(input.length() != 0){
            String[] edgesArray = input.split(" ");
            circuitEdges = new String[edgesArray.length];
            
            while(true){
              for(int i = 0; i < edgesArray.length; i++){
                if(edgesArray[i].length() != 2){
                  errorEdges.setText("<html><font color='red'>Each edge must have 2 defined lights!</font></html>");
                  errors = true;                
                  break;
                }
              }
              if(!errors){
                boolean found = false;
                for(int i = 0; i < edgesArray.length; i++){
                  found = false;
                  for(int j = 0; j < lightsArray.length; j++){
                    if(Character.toString(edgesArray[i].charAt(0)).equals(lightsArray[j])){
                      found = true;
                    }
                    
                    if(!found && j == lightsArray.length - 1){
                      errorEdges.setText("<html><font color='red'>A light entered is not defined!</font></html>");
                      errors = true;
                      break;
                    }
                    
                  }
                }
                found = false;
                for(int i = 0; i < edgesArray.length; i++){
                  found = false;
                  for(int j = 0; j < lightsArray.length; j++){
                    if(Character.toString(edgesArray[i].charAt(1)).equals(lightsArray[j])){
                      found = true;
                    }
                    if(!found && j == lightsArray.length - 1){
                      errorEdges.setText("<html><font color='red'>A light entered is not defined!</font></html>");
                      errors = true;
                      break;
                    }
                    
                  }
                }
              }
              break;
            }
            if(!errors){
              errorEdges.setText("");//sets the edges
              circuit.setEnabled(true);
              circuitEdges = new String[edgesArray.length];
              for(int i = 0; i < edgesArray.length; i++){
                char first = edgesArray[i].charAt(0);
                char second = edgesArray[i].charAt(1);
                for(int j = 0; j < size; j++){
                  if (lightsArray[j].charAt(0) == first){
                    firstPos = j;
                  } else if (lightsArray[j].charAt(0) == second){
                    secondPos = j;
                  }
                }
                switches[firstPos][secondPos] = 1;     
              }
              for(int x = 0; x < circuitEdges.length; x++){
                circuitEdges[x] = edgesArray[x];//array is used in paint method to show the circuit
              }
            }
          }
          double buttons = lightsArray.length;
          int buttonsInt = lightsArray.length;
          x = new int[buttonsInt];
          y = new int[buttonsInt];
          double gaps = 360.0 / buttons;
          lightsPanel.removeAll();
          double newgaps = 180;
          for(int i=0; i<buttons; i++){
            double radgaps = Math.toRadians(newgaps);
            //System.out.println(radgaps);
            double endX   = 280 + 260 * Math.sin(radgaps);//calculates the positions for the buttons
            double endY   = 280 + 260 * Math.cos(radgaps);//based on how many are entered
            newgaps += gaps;
            x[i] = (int) endX;
            y[i] = (int) endY;
          }
          lightsButtons = new JButton[lightsArray.length];
          for(int i = 0; i < lightsButtons.length; i++){
            lightsButtons[i] = new JButton("null");
            lightsButtons[i].setEnabled(false);
            lightsButtons[i].addActionListener(listener);     
            lightsButtons[i].setBounds(x[i],y[i], 40,40);
            //lightsButtons[i].setVisible(false);
            lightsPanel.add(lightsButtons[i]);
          }
          repaint();
          add(lightsPanel);
          lightsPanel.add(graphics);
          
          for(int i = 0; i < size; i++){
            switches[i][i] = 1;
          }
          lightsOn = new int[lightsArray.length];
          for(int i = 0; i < lightsArray.length; i++){
            lightsOn[i] = 1;
          }
          for(int i = 0; i < lightsButtons.length; i++){
            if(i < lightsArray.length){
              lightsButtons[i].setText(lightsArray[i]);//sets the button to the specified name by user
              lightsButtons[i].setEnabled(true);
              lightsButtons[i].setBackground(Color.YELLOW);//sets background to yellow to represent on
              lightsButtons[i].setOpaque(true);
              lightsButtons[i].setVisible(true);
            } else {
              lightsButtons[i].setText("null");
              lightsButtons[i].setEnabled(false);//button not active so left null with no background
              lightsButtons[i].setOpaque(false);
              lightsButtons[i].setVisible(false);
            }
          }
          solve.setEnabled(true);
          options.setEnabled(true);
          instructions.setEnabled(true);//sets buttons to usable since valid input was entered
          
          solveStepByStep();//now that its validated can call solve it
          textInstructions.setText("");
        }
        
      } else if (buttonPressed.getSource().equals(solve)){//if Solve step by step is pressed
        int moves = 0;
        textInstructions.setText("Turned all lights back on.");//if its pressed when no lights are on this is displayed
        
        boolean anyLightsOff = false;
        if(position < smallest.length){
          for(int i = 0; i < lightsOn.length; i++){
            if(lightsOn[i] == 0){
              anyLightsOff = true;
            }
          }
          if(position == 0 && anyLightsOff == true){
            for(int i = 0; i < lightsArray.length; i++){
              lightsOn[i] = 1;
              lightsButtons[i].setOpaque(true);
            }
          } else {
            int pos = smallest[position];
            textInstructions.setText(lightsArray[pos] + ", pressed!");//displays what was done when the button was pressed
            position++;
            for(int j = 0; j < switches[pos].length; j++){//sets the lights affected by the solve button to on/off
              if(switches[pos][j] == 1){
                if(lightsOn[j] == 1){
                  lightsButtons[j].setOpaque(false);
                  lightsOn[j] = 0;
                } else {
                  lightsButtons[j].setOpaque(true);
                  lightsOn[j] = 1;
                }
              }
            }
            if(position == smallest.length){
              moves = position;
              position = 0;
            }
            if(moves != 0){//solved so display that it has, and the amount of moves it took
              textInstructions.setText(lightsArray[pos] + ", pressed!" + " Solved in " + moves + " moves.");
            }
          }
          
        } 
      } else if (buttonPressed.getSource().equals(options)){//user pressed Reset Lights button
        
        if(lightsArray != null){
          for(int i = 0; i < lightsArray.length; i++){//simply resets all lights to their on state
            lightsOn[i] = 1;
            lightsButtons[i].setOpaque(true);
          }
          position = 0;
        }
        
      }else if(buttonPressed.getSource().equals(instructions)){//user has pressed Instructions button
        
        String instruct = "<html>Click ";
        
        for(int i = 0; i < instructionList.length; i++){//simple method to append the instructions to a string then
          instruct = instruct + instructionList[i] + ", ";//display it on the screen
        }
        if(maxLights == 0){
          instruct = instruct + "to turn off all lights.</html>";
        } else if (maxLights == 1) {
          instruct = instruct + "to turn off all but " + maxLights + " light.</html>";
        } else {
          instruct = instruct + "to turn off all but " + maxLights + " lights.</html>";
        }
        textInstructions.setText(instruct);
        
      }else if(buttonPressed.getSource().equals(circuit)){//the Show Circuit button was pressed
        if(circuitOn == true){
          circuitOn = false;
          for(int i = 0; i < lightsButtons.length; i++){
            lightsButtons[i].setForeground(Color.black);//if the circuit is not displaying anymore set button text to black
          }
        }else{
          circuitOn = true;
          for(int i = 0; i < lightsButtons.length; i++){
            lightsButtons[i].setForeground(colors[i]);//set the button text to their line color as its showing the circuit
          }
        }
        
        lightsPanel.add(graphics);//add graphics which now has the circuit drawn as well
        
      }else{//if someone clicks a light this will adjust the display to turn off/on the ones needed
        for(int i=0; i<lightsButtons.length; i++){
          if (buttonPressed.getSource().equals(lightsButtons[i])){
            for(int j = 0; j<switches[i].length; j++){
              if(switches[i][j] == 1){
                if(lightsOn[j] == 1){
                  lightsButtons[j].setOpaque(false);
                  lightsOn[j] = 0;
                } else {
                  lightsButtons[j].setOpaque(true);
                  lightsOn[j] = 1;
                }
              }
            }
          }
        }
      }
      repaint();
    } 
  }
  
  private class LightsPanel extends JPanel {
    
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      g.drawLine(0, 0, 0, 600);
      
      int firstx = 0, firsty = 0, secondx = 0, secondy = 0;
      if(circuitOn == true){//true so runs through code used to draw the circuit of the buttons
        String[] firsts = new String[circuitEdges.length];
        String[] seconds = new String[circuitEdges.length];
        for(int i = 0; i < circuitEdges.length; i++){
          String first = Character.toString(circuitEdges[i].charAt(0));
          firsts[i] = first;
          String second = Character.toString(circuitEdges[i].charAt(1));
          seconds[i] = second;
          int buttonColor = 0;
          int button = 0;
          
          boolean secondLine = false;
          
          for(int j = 0; j < lightsButtons.length; j++){
            if(first.equals(lightsButtons[j].getText())){
              firstx = x[j];
              firsty = y[j];
              buttonColor = j;
              button = j;
            }
            if(second.equals(lightsButtons[j].getText())){
              secondx = x[j];
              secondy = y[j];
            }
          }
          for(int x = 0; x < firsts.length; x++){
            if(first.equals(seconds[x]) && second.equals(firsts[x])){
              button += 3;
            }
          }
          g.setColor(colors[buttonColor]);
          g.drawLine(firstx + 20 + button, firsty + 20, secondx + 20 + button, secondy + 20);
        }
      }else{
        for(int i = 0; i < lightsButtons.length; i++){
          lightsButtons[i].setForeground(Color.black);//if the circuit is not displaying anymore set button text to black
        }
      }
    }
    
    public LightsPanel(){
      setPreferredSize(new Dimension(600, 600));
    }   
  }
}