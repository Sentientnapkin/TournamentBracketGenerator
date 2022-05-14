package org.BracketTypes;

import javax.swing.*;

public class Match extends JPanel {
  private int boX;
  private JPanel team1Panel, team2Panel;
  private JLabel team1, team2;
  private JTextField score1, score2;

  public Match(int boX, String team1, String team2) {
    super(); // equivalent to: JPanel panel1 = new JPanel();
    this.boX = boX;

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    team1Panel = new JPanel();
    this.add(team1Panel);

    team2Panel = new JPanel();
    this.add(team2Panel);

    this.team1 = new JLabel(team1);
    team1Panel.add(this.team1);

    this.score1 = new JTextField(5);
    team1Panel.add(score1);

    this.team2 = new JLabel(team2);
    team2Panel.add(this.team2);

    this.score2 = new JTextField(5);
    team2Panel.add(score2);
  }

  public String getTeam1() {
    return team1.getText();
  }

  public String getTeam2() {
    return team2.getText();
  }

  public void setTeam1(String team1) {
    this.team1.setText(team1);
  }

  public void setTeam2(String team2) {
    this.team2.setText(team2);
  }

  public int getScore1() {
    return Integer.parseInt(score1.getText());
  }

  public int getScore2() {
    return Integer.parseInt(score2.getText());
  }

  public boolean isFinished() {
    return getScore1() >= boX/2+1 || getScore2() >= boX/2+1;
  }

  public String getWinner() {
    if (getTeam1().equals("bye")) {
      return getTeam2();
    }
    else if (getTeam2().equals("bye")) {
      return getTeam1();
    }
    else if (isFinished()) {
      if (getScore1() > getScore2()) {
        return getTeam1();
      } else {
        return getTeam2();
      }
    } else {
      return "";
    }
  }
}
