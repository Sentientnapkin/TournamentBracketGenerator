package org.BracketTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DoubleElimination implements ActionListener {
  private int bracketSize;
  private final int boX; // best of x, the maximum number of games needed to decided who has won
  private final ArrayList<String> players; // list of all the players being given to the RoundRobin Object
  private ArrayList<JPanel> upperPanels, lowerPanels;
  //split into the upper and lower bracket - this holds all the rounds that
  //exist in the upper and lower brackets
  JFrame frame;
  JButton refresh;
  JPanel win, upper, lower; //the panel that holds the panels in the upper bracket and lower bracket
  JLabel winner, divide; //labels for the winner of the tournament and the dividing line

  public DoubleElimination(ArrayList<String> players, int boX) {

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

    upperPanels = new ArrayList<>(); //holds all the upper bracket rounds
    lowerPanels = new ArrayList<>(); //holds all the lower bracket rounds

    frame = new JFrame("Double Elimination Bracket");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

    upper = new JPanel(); //the panel that holds the upper bracket
    upper.setLayout(new BoxLayout(upper, BoxLayout.X_AXIS));
    frame.add(upper);

    divide = new JLabel("--------------------------------------------");
    frame.add(divide); //this dividing line just makes it look nice splitting the two brackets

    lower = new JPanel(); //the panel that holds the lower bracket
    lower.setLayout(new BoxLayout(lower, BoxLayout.X_AXIS));
    frame.add(lower);

    int sizeTemp = bracketSize+1;
    while (sizeTemp > 0) { //creates the panels for every upper bracket round
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      upperPanels.add(panel);
      upper.add(panel);
      sizeTemp /= 2;
      //this divides the size by 2 every time it goes through the loop
      //this is because the number of rounds in the upper bracket is the same as log base 2 of the bracket size
    }

    int playersIndex = 0; //populate initial rounds with matches
    while (playersIndex < (bracketSize-1)) { //goes so that the initial round has the same amount of matches as the bracketSize
      Match match = new Match(boX, players.get(playersIndex), players.get(playersIndex + 1));
      upperPanels.get(0).add(match);
      playersIndex += 2; //adds two as there are two teams per match
    }

    int n = bracketSize/2;//populating the rest of the rounds with matches
    if(players.size() >= 2) { //if there are more than 2 players
      for(int i = 1; i < upperPanels.size()-1; i++) { //iterating through the rounds
        for(int games = 0; games<n; games+=2) {
          //iterating through the matches in a round, there are half the rounds in each as the last round
          //and so n is divided by 2 every iteration to half the number of matches in the round
          Match match = new Match(boX, "","");
          upperPanels.get(i).add(match);
        }
        n /= 2;
      }
      GrandFinals grandFinals = new GrandFinals(boX, "",""); //adds a grand final match to the end of the bracket
      upperPanels.get(upperPanels.size()-1).add(grandFinals);
    }

    sizeTemp = bracketSize/2;
    while(sizeTemp >0) {//creates the panels for every lower bracket round
      for(int i = 0;i<2;i++) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        lowerPanels.add(panel);
        lower.add(panel);
      }
      sizeTemp /= 2;
    }

    //populate lower bracket rounds with matches
    int bracketIndex = 0;
    n=2;
    while(n<bracketSize){
      for(int d = 0;d<2;d++) {
        //there are two rounds with the same amount of matches in a row consistently so there is a for loop that does it twice
        for (int j = bracketSize / n; j > 0; j -= 2) {
          //n begins at 2 and is multiplied by 2 every iteration so that there are half the rounds in each as the last round
          Match match = new Match(boX, "", "");
          lowerPanels.get(bracketIndex).add(match);
        }
        bracketIndex++;
      }
      n*=2;
    }

    win = new JPanel(); //holds the label that says who won the tournament
    upperPanels.add(win);
    upper.add(win);

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
      refreshLower(); //runs the two methods seperately so that it looks nicer
      refreshUpper();
    }
  }

  public void refreshUpper () {
    for (int panelIndex = 0; panelIndex < upperPanels.size()-1; panelIndex++) { //iterates through the upper panels
      for (int matchIndex = 0; matchIndex < upperPanels.get(panelIndex).getComponentCount(); matchIndex++) {
        //iterates through the matches in a panel
        if(upperPanels.get(panelIndex).getComponentCount()==1&&panelIndex!=upperPanels.size()-2) {
          //if the match is the winners finals
          Match match = (Match) upperPanels.get(panelIndex).getComponent(matchIndex);
          if(match.isFinished()){
            //if the match is finished then the next rounds is populated with the winner
            //and the loser is moved to the lower bracket finals
            Match loser = (Match) lowerPanels.get(panelIndex+1).getComponent(0);
            Match nextMatch = (Match) upperPanels.get(panelIndex+1).getComponent(matchIndex);
            loser.setTeam2(match.getLoser());
            nextMatch.setTeam1(match.getWinner());
          }
        }
        else if(upperPanels.get(panelIndex).getComponent(matchIndex) instanceof Match) {
          //if the match is not the winners finals but is a match
          Match match = (Match) upperPanels.get(panelIndex).getComponent(matchIndex);
          if(match.isFinished()) {
            //if the matche is finished then the winner and loser are found
            String won = match.getWinner();
            String lost = match.getLoser();
            if (panelIndex + 1 == upperPanels.size()-1) { //if the match is the grandfinals then the winner is the winner of the tournament
              winner.setText(won+" wins!");
            }
            if (upperPanels.get(panelIndex+1).getComponent(matchIndex/2) instanceof Match) {
              //if there is a match in the next upper bracket round
              //it is matchIndex/2 because there are half the matches in the next round
              Match nextMatch = (Match) upperPanels.get(panelIndex+1).getComponent(matchIndex/2);
              if(nextMatch.getTeam1().equals("")) {
                //if the next match first team is empty it sets the winner of the last
                //match to the first team of the next match
                nextMatch.setTeam1(won);
              } else { //else it is set to team 2
                nextMatch.setTeam2(won);
              }
              if(lowerPanels.get(panelIndex).getComponentCount()==upperPanels.get(panelIndex).getComponentCount()){
                //if the lower bracket match exists
                Match loserMatch = (Match) lowerPanels.get(panelIndex).getComponent(matchIndex);
                //set the loser of the match to the lower bracket
                if(loserMatch.getTeam1().equals("")) {
                  loserMatch.setTeam1(lost);
                } else {
                  loserMatch.setTeam2(lost);
                }
              } else { //if a match is not found at the same index - move it to half the index
                Match loserMatch = (Match) lowerPanels.get(panelIndex).getComponent(matchIndex / 2);
                if (loserMatch.getTeam1().equals("")) {
                  loserMatch.setTeam1(lost);
                } else {
                  loserMatch.setTeam2(lost);
                }
              }
            }
          }
        }
      }
    }
  }

  public void refreshLower () {
    for (int panelIndex = 0; panelIndex < lowerPanels.size(); panelIndex++) { //iterates through lower bracket panels
      for (int matchIndex = 0; matchIndex < lowerPanels.get(panelIndex).getComponentCount(); matchIndex++) {
        //iterates through lower bracket matches
        if(panelIndex!=lowerPanels.size()-4&&lowerPanels.get(panelIndex).getComponentCount()==1) {
          //if the panel is the lower bracket finals
          Match match = (Match) lowerPanels.get(panelIndex).getComponent(matchIndex);
          if(match.isFinished()){ //set the winner to the grand finals
            Match grandFinals = (Match) upperPanels.get(upperPanels.size()-2).getComponent(0);
            grandFinals.setTeam2(match.getWinner());
          }
        }
        else if(lowerPanels.get(panelIndex).getComponent(matchIndex) instanceof Match) {
          //if the lower panel match exists
          Match match = (Match) lowerPanels.get(panelIndex).getComponent(matchIndex);
          if(match.isFinished()) {
            //if it is finished then find the winner
            String won = match.getWinner();
            if(lowerPanels.get(panelIndex+1).getComponentCount()<=matchIndex){
              //if the next lower bracket round does not have the same amount of matches
              Match nextMatch = (Match) lowerPanels.get(panelIndex+1).getComponent(matchIndex/2);
              //set the winner to the matchIndex/2
              if(nextMatch.getTeam1().equals("")) {
                nextMatch.setTeam1(won);
              } else if (nextMatch.getTeam2().equals("")) {
                nextMatch.setTeam2(won);
              }
            }
            else if (lowerPanels.get(panelIndex+1).getComponent(matchIndex) instanceof Match) {
              //if the next lower bracket round does have the same amount of matches
              Match nextMatch = (Match) lowerPanels.get(panelIndex+1).getComponent(matchIndex);
              //set the winner to the match at matchIndex
              if(nextMatch.getTeam1().equals("")) {
                nextMatch.setTeam1(won);
              } else if (nextMatch.getTeam2().equals("")){
                nextMatch.setTeam2(won);
              }
            }
          }
        }
      }
    }
  }
}
