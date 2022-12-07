package org.BracketTypes;

import javax.swing.*;

public class Match extends JPanel {
    private final int boX;
    private final JPanel team1Panel;
    private final JPanel team2Panel;
    private final JLabel team1;
    private final JLabel team2;
    private final JTextField score1;
    private final JTextField score2;

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

        this.score1 = new JTextField(3);
        team1Panel.add(score1);

        this.team2 = new JLabel(team2);
        team2Panel.add(this.team2);

        this.score2 = new JTextField(3);
        team2Panel.add(score2);
    }

    public String getTeam1() {
        return team1.getText();
    }

    public void setTeam1(String team1) {
        this.team1.setText(team1);
    }

    public String getTeam2() {
        return team2.getText();
    }

    public void setTeam2(String team2) {
        this.team2.setText(team2);
    }

    public JPanel getTeam1Panel() {
        return team1Panel;
    }

    public JPanel getTeam2Panel() {
        return team2Panel;
    }

    public int getScore1() {
        String nums = "0123456789";
        if (score1.getText().length() > 0 && nums.contains(score1.getText().substring(0, 1))) {
            return Integer.parseInt(score1.getText());
        }
        return 0;
    }

    public int getScore2() {
        String nums = "0123456789";
        if (score2.getText().length() > 0 && nums.contains(score2.getText().substring(0, 1))) {
            return Integer.parseInt(score2.getText());
        }
        return 0;
    }

    public boolean isNotFinished() {
        return getScore1() < boX / 2 + 1 && getScore2() < boX / 2 + 1;
    }

    public String getWinner() {
        if (isNotFinished()) return "";
        return getScore1() <= getScore2() ? getTeam2() : getTeam1();
    }

    public String getLoser() {
        if (isNotFinished()) {
            return "";
        }
        return getScore1() <= getScore2() ? getTeam1() : getTeam2();
    }
}
