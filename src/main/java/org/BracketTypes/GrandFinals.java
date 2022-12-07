package org.BracketTypes;

import javax.swing.*;

public class GrandFinals extends Match {
    private final JTextField scoreReset1;
    private final JTextField scoreReset2;

    public GrandFinals(int boX, String team1, String team2) {
        super(boX, team1, team2);

        scoreReset1 = new JTextField(3);
        getTeam1Panel().add(scoreReset1);

        scoreReset2 = new JTextField(3);
        getTeam2Panel().add(scoreReset2);
    }

    public int getScoreReset1() {
        String nums = "0123456789";
        if (scoreReset1.getText().length() > 0
                && nums.contains(scoreReset1.getText().substring(0, 1))) {
            return Integer.parseInt(scoreReset1.getText());
        }
        return 0;
    }

    public int getScoreReset2() {
        String nums = "0123456789";
        if (scoreReset2.getText().length() > 0
                && nums.contains(scoreReset2.getText().substring(0, 1))) {
            return Integer.parseInt(scoreReset2.getText());
        }
        return 0;
    }

    @Override
    public boolean isNotFinished() {
        if (getScore1() > getScore2() || getScoreReset1() > getScoreReset2()) {
            return false;
        }
        return getScore1() >= getScore2() || getScoreReset1() >= getScoreReset2();
    }

    @Override
    public String getWinner() {
        if (isNotFinished()) return "";
        if (getScore1() <= getScore2() && getScoreReset1() <= getScoreReset2()) {
            return getTeam2();
        }
        return getTeam1();
    }
}
