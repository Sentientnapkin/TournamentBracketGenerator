package org.BracketTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SingleElimination implements ActionListener {
  private int bracketSize;
  private final int boX; // best of x, the maximum number of games needed to decided who has won
  private final ArrayList<String> players; // list of all the players being given to the RoundRobin Object
  private ArrayList<JPanel> panels; //holds all the matches in the bracket
  JFrame frame;
  JButton refresh;
  JPanel win; //panel that holds label for the winner of the tournament
  JLabel winner;

  public SingleElimination(ArrayList<String> players, int boX) {

    int playerAmount = players.size();

    this.players = players; //the inputted list of players

    int num = 0; //finds the lowest power of 2 that is greater than or equal to the number of players
    while (Math.pow(2, num) < playerAmount) {
      num++;
    }
    this.bracketSize = (int) (Math.pow(2, num));
    if (this.bracketSize> playerAmount) {
      this.bracketSize /=2;
    } //makes sure the bracket size is a power of 2, if the player amount is not a power of 2 it will first round up to the
    // next power of 2, and if it is larger than the player amount it cuts it in half and cuts off the non-used players

    this.boX = boX;

    panels = new ArrayList<>(); //holds all the rounds in an array list

    frame = new JFrame("Single Elimination Bracket");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

    int sizeTemp = bracketSize;
    while (sizeTemp > 0) { //creates the panels for every round of the tournament
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panels.add(panel);
      frame.add(panel);
      sizeTemp /= 2;
      //this divides the size by 2 every time it goes through the loop
      //this is because the number of rounds in the  bracket is the same as log base 2 of the bracket size
    }

    int playersIndex = 0; //populate initial rounds with matches
    while (playersIndex < (bracketSize-1)) {
      Match match = new Match(boX, players.get(playersIndex), players.get(playersIndex + 1));
      panels.get(0).add(match);
      playersIndex += 2;
    }

    int n = bracketSize/2;//populating the rest of the rounds with matches
    if(players.size() >= 2) { //if there are more than 2 players
      for(int i = 1; i < panels.size()-1; i++) { //iterating through the rounds
        for(int games = 0; games<n; games+=2) {
          //iterating through the matches in a round, there are half the rounds in each as the last round
          //and so n is divided by 2 every iteration to half the number of matches in the round
          System.out.println(games);
          Match match = new Match(boX, "","");
          panels.get(i).add(match);
        }
        n /= 2;
      }
    }

    win = new JPanel(); //holds the label that says who won the tournament
    panels.add(win);
    frame.add(win);

    winner = new JLabel("");
    win.add(winner);

    refresh = new JButton("Refresh"); //button to refresh the bracket manually
    refresh.addActionListener(this);
    frame.add(refresh);

    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    if (ae.getActionCommand().equals("Refresh")) {
      for (int panelIndex = 0; panelIndex < panels.size()-2; panelIndex++) { //iterates through the panels
        for (int matchIndex = 0; matchIndex < panels.get(panelIndex).getComponentCount(); matchIndex++) {
          //iterates through the matches in a panel
          if(panels.get(panelIndex).getComponent(matchIndex) instanceof Match) {
            //find the match in the panel
            Match match = (Match) panels.get(panelIndex).getComponent(matchIndex);
            if(match.isFinished()) {
              //if the match is finished find the winner
              String won = match.getWinner();
              if (panelIndex + 1 == panels.size()-2) {
                //if it is the finals then set it as the winner of the tournament
                winner.setText(won+" wins!");
              }
              if (panels.get(panelIndex+1).getComponent(matchIndex/2) instanceof Match) {
                //if the next panel has a match (at matchIndex/2 because there are half the matches in the next panel)
                Match nextMatch = (Match) panels.get(panelIndex+1).getComponent(matchIndex/2);
                //set a team in the next match to the winner of the current match
                if(nextMatch.getTeam1().equals("")) {
                  nextMatch.setTeam1(won);
                } else {
                  nextMatch.setTeam2(won);
                }
              }
            }
          }
        }
      }
    }
  }
}