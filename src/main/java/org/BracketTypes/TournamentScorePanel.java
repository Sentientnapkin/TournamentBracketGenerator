package org.BracketTypes;

import javax.swing.*;

public class TournamentScorePanel extends JPanel {
  private JLabel teamName, score; //simply holds the team name and the team score (W and L)

  public TournamentScorePanel(String teamName) {
    this.teamName = new JLabel(teamName);
    this.score = new JLabel("0 - 0"); //hold it in a Wins - Losses format
    this.add(this.teamName);
    this.add(this.score);
  }

  public void setScore(int wins, int losses) { //set the score to the given wins and losses
    this.score.setText(wins + " - " + losses);
  }

  public int getWins(){ //gets the wins by splitting it at the "-"
    String[] temp = this.score.getText().split("-");
      return Integer.parseInt(temp[0].substring(0, temp[0].length() - 1)); //needs to make sure not to include the space, and then returns the parsed int
  }

  public int getLosses(){ //gets the losses by splitting at the other end of the "-"
    String[] temp = this.score.getText().split("-");
    return Integer.parseInt(temp[1].substring(1)); //needs to make sure not to include the space, and then returns the parsed int
  }

  public void addWin() { //a fast method for when it only needs to add one win
    this.score.setText(this.getWins() + 1 + " - " + this.getLosses());
  }

  public void addLoss() { //a fast method for when it only needs to add one loss
    this.score.setText(this.getWins() + " - " + (1+this.getLosses())); //one and loss count are in parentheses because of order of operations
  }
}
