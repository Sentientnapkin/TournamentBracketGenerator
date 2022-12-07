package org.BracketTypes;

import javax.swing.*;

public class Match extends JPanel { //functions much like two LTPanels in one spot
  private int boX; // best of x, the maximum number of games needed to decided who has won
  private JPanel team1Panel, team2Panel; //separates the contents of the panels in to two smaller panels
  private JLabel team1, team2;
  private JTextField score1, score2;

  public Match(int boX, String team1, String team2) {
    super(); // equivalent to: JPanel panel1 = new JPanel();
    this.boX = boX;

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //sets the entire matche to be vertically sorted

    team1Panel = new JPanel(); //creating the two panels
    this.add(team1Panel);

    team2Panel = new JPanel();
    this.add(team2Panel);

    this.team1 = new JLabel(team1); //adding the team names and their scores as labels and text fields to each team panel
    team1Panel.add(this.team1);

    this.score1 = new JTextField(3);
    team1Panel.add(score1);

    this.team2 = new JLabel(team2);
    team2Panel.add(this.team2);

    this.score2 = new JTextField(3);
    team2Panel.add(score2);
  }

  public String getTeam1() { //returns name of team 1
    return team1.getText();
  }

  public String getTeam2() { //returns name of team 2
    return team2.getText();
  }

  public void setTeam1(String team1) { //sets the name of team 1
    this.team1.setText(team1);
  }

  public void setTeam2(String team2) { //sets the name of team 2
    this.team2.setText(team2);
  }

  public JPanel getTeam1Panel(){ //returns the team 1 panel
    return team1Panel;
  }

  public JPanel getTeam2Panel(){ //returns the team 2 panel
    return team2Panel;
  }

  public int getScore1() { //returns the score of team 1
    String nums = "0123456789";
    if(score1.getText().length()>0 && nums.contains(score1.getText().substring(0,1))){ //checks if the score is a number
      return Integer.parseInt(score1.getText());
    }
    else { //if it is not a number then it assumes it is zero
      return 0;
    }
  }

  public int getScore2() { //returns the score of team 2
    String nums = "0123456789";
    if(score2.getText().length()>0 && nums.contains(score2.getText().substring(0,1))){ //checks if the score is a number
      return Integer.parseInt(score2.getText());
    }
    else { //if it is not a number then it assumes it is zero
      return 0;
    }
  }


  public boolean isFinished() {
    //checks if the match is finished by checking if either score is greater than
    // the best of x number divided by 2 and plus 1
      return getScore1() >= boX / 2 + 1 || getScore2() >= boX / 2 + 1;
  }

  public String getWinner() {
    if (isFinished()) {
      if (getScore1() > getScore2()) {
        return getTeam1();
      } else {
        return getTeam2();
      }
    } else {
      return "";
    }
  }

  public String getLoser() {
    if (isFinished()) {
      if (getScore1() > getScore2()) {
        return getTeam2();
      } else {
        return getTeam1();
      }
    } else {
      return "";
    }
  }
}
