package org.BracketTypes;

import javax.swing.*;

public class GrandFinals extends Match {
  private JTextField scoreReset1, scoreReset2;

  public GrandFinals(int boX, String team1, String team2) {
    //this is made for a double bracket tournament in the case of a bracket reset
    //where the lower bracket team wins the first best of X
    super(boX, team1, team2);

    scoreReset1 = new JTextField(3);
    getTeam1Panel().add(scoreReset1);

    scoreReset2 = new JTextField(3);
    getTeam2Panel().add(scoreReset2);
  }

  public int getScoreReset1() { //holds the score for the bracket reset points of the first team
    String nums = "0123456789";
    if (scoreReset1.getText().length() > 0 && nums.contains(scoreReset1.getText().substring(0, 1))) {
      return Integer.parseInt(scoreReset1.getText());
    } else {
      return 0;
    }
  }

  public int getScoreReset2() { //holds the score for the bracket reset points of the second team
    String nums = "0123456789";
    if (scoreReset2.getText().length() > 0 && nums.contains(scoreReset2.getText().substring(0, 1))) {
      return Integer.parseInt(scoreReset2.getText());
    } else {
      return 0;
    }
  }

  @Override
  public boolean isFinished() {
    //the match is finished if the upper bracket team has won a best of X
    //or the lower bracket team has won both best of X's
    if (getScore1() > getScore2() || getScoreReset1() > getScoreReset2()) {
      return true;
    } else return getScore1() < getScore2() && getScoreReset1() < getScoreReset2();
  }

  @Override
  public String getWinner() {
    //returns the winner of the match
    if (isFinished()) {
      if (getScore1() > getScore2() || getScoreReset1() > getScoreReset2()) {
        return getTeam1();
      } else return getTeam2();
    }else return "";
  }
}
