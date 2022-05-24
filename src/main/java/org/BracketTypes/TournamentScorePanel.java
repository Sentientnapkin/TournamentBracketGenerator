package org.BracketTypes;

import javax.swing.*;

public class TournamentScorePanel extends JPanel {
  private JLabel teamName, score;

  public TournamentScorePanel(String teamName) {
    this.teamName = new JLabel(teamName);
    this.score = new JLabel("0 - 0");
    this.add(this.teamName);
    this.add(this.score);
  }

  public void setScore(int wins, int losses) {
    this.score.setText(wins + " - " + losses);
  }

  public int getWins(){
    String[] temp = this.score.getText().split("-");
    return Integer.parseInt(temp[0]);
  }

  public int getLosses(){
    String[] temp = this.score.getText().split("-");
    return Integer.parseInt(temp[1]);
  }

  public void addWin() {
    this.score.setText(this.getWins() + 1 + " - " + this.getLosses());
  }

  public void addLoss() {
    this.score.setText(this.getWins() + " - " + this.getLosses() + 1);
  }
}
