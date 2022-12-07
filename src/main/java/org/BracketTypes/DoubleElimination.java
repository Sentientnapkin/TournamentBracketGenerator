package org.BracketTypes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class DoubleElimination implements ActionListener {
    private final ArrayList<JPanel> upperPanels;
    private final ArrayList<JPanel> lowerPanels;
    JFrame frame;
    JButton refresh;
    JPanel win, upper, lower;
    JLabel winner, divide;

    public DoubleElimination(ArrayList<String> players, int boX) {

        int playerAmount = players.size();

        int num = 0;
        while (Math.pow(2, num) < playerAmount) {
            num++;
        }
        int bracketSize = (int) (Math.pow(2, num));
        if (bracketSize > playerAmount) {
            bracketSize /= 2;
        }

        upperPanels = new ArrayList<>();
        lowerPanels = new ArrayList<>();

        frame = new JFrame("Double Elimination Bracket");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        upper = new JPanel();
        upper.setLayout(new BoxLayout(upper, BoxLayout.X_AXIS));
        frame.add(upper);

        divide = new JLabel("--------------------------------------------");
        frame.add(divide);

        lower = new JPanel();
        lower.setLayout(new BoxLayout(lower, BoxLayout.X_AXIS));
        frame.add(lower);

        createUpperBracketPanels(bracketSize);

        populateInitialMatches(players, boX, bracketSize);

        populateOtherMatches(players, boX, bracketSize);

        createLowerBracketPanels(bracketSize);

        populateLowerBracketMatches(boX, bracketSize);

        win = new JPanel();
        upperPanels.add(win);
        upper.add(win);

        winner = new JLabel("");
        win.add(winner);

        refresh = new JButton("Refresh");
        refresh.addActionListener(this);
        frame.add(refresh);

        frame.pack();
        frame.setVisible(true);
    }

    private void populateLowerBracketMatches(int boX, int bracketSize) {
        // populate lower bracket rounds with matches
        int bracketIndex = 0;
        int n = 2;
        while (n < bracketSize) {
            for (int d = 0; d < 2; d++) {
                for (int j = bracketSize / n; j > 0; j -= 2) {
                    Match match = new Match(boX, "", "");
                    lowerPanels.get(bracketIndex).add(match);
                }
                bracketIndex++;
            }
            n *= 2;
        }
    }

    private void createLowerBracketPanels(int bracketSize) {
        int sizeTemp;
        sizeTemp = bracketSize / 2;
        while (sizeTemp > 0) { // creates the panels for every lower bracket round
            for (int i = 0; i < 2; i++) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                lowerPanels.add(panel);
                lower.add(panel);
            }
            sizeTemp /= 2;
        }
    }

    private void populateOtherMatches(ArrayList<String> players, int boX, int bracketSize) {
        int n = bracketSize / 2; // populating the rest of the rounds with matches
        if (players.size() < 2) {
            return;
        }
        for (int i = 1; i < upperPanels.size() - 1; i++) {
            for (int games = 0; games < n; games += 2) {
                Match match = new Match(boX, "", "");
                upperPanels.get(i).add(match);
            }
            n /= 2;
        }
        GrandFinals grandFinals = new GrandFinals(boX, "", "");
        upperPanels.get(upperPanels.size() - 1).add(grandFinals);
    }

    private void populateInitialMatches(ArrayList<String> players, int boX, int bracketSize) {
        int playersIndex = 0; // populate initial rounds with matches
        while (playersIndex < (bracketSize - 1)) {
            Match match = new Match(boX, players.get(playersIndex), players.get(playersIndex + 1));
            upperPanels.get(0).add(match);
            playersIndex += 2;
        }
    }

    private void createUpperBracketPanels(int bracketSize) {
        int sizeTemp = bracketSize + 1;
        while (sizeTemp > 0) { // creates the panels for every upper bracket round
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            upperPanels.add(panel);
            upper.add(panel);
            sizeTemp /= 2;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (!ae.getActionCommand().equals("Refresh")) {
            return;
        }
        refreshLower();
        refreshUpper();
    }

    public void refreshUpper() {
        for (int panelIndex = 0; panelIndex < upperPanels.size() - 1; panelIndex++) {
            for (int matchIndex = 0;
                    matchIndex < upperPanels.get(panelIndex).getComponentCount();
                    matchIndex++) {
                if (upperPanels.get(panelIndex).getComponentCount() == 1
                        && panelIndex != upperPanels.size() - 2) {
                    Match match = (Match) upperPanels.get(panelIndex).getComponent(matchIndex);
                    if (match.isNotFinished()) continue;
                    Match loser = (Match) lowerPanels.get(panelIndex + 1).getComponent(0);
                    Match nextMatch =
                            (Match) upperPanels.get(panelIndex + 1).getComponent(matchIndex);
                    loser.setTeam2(match.getLoser());
                    nextMatch.setTeam1(match.getWinner());
                } else if (upperPanels.get(panelIndex).getComponent(matchIndex) instanceof Match) {
                    Match match = (Match) upperPanels.get(panelIndex).getComponent(matchIndex);
                    if (match.isNotFinished()) continue;
                    String won = match.getWinner();
                    String lost = match.getLoser();
                    if (panelIndex + 1 == upperPanels.size() - 1) winner.setText(won + " wins!");
                    if (!(upperPanels.get(panelIndex + 1).getComponent(matchIndex / 2)
                            instanceof Match)) return;
                    Match nextMatch =
                            (Match) upperPanels.get(panelIndex + 1).getComponent(matchIndex / 2);
                    if (nextMatch.getTeam1().equals("")) nextMatch.setTeam1(won);
                    else nextMatch.setTeam2(won);
                    Match loserMatch;
                    if (lowerPanels.get(panelIndex).getComponentCount()
                            == upperPanels.get(panelIndex).getComponentCount()) {
                        loserMatch = (Match) lowerPanels.get(panelIndex).getComponent(matchIndex);
                    } else {
                        loserMatch =
                                (Match) lowerPanels.get(panelIndex).getComponent(matchIndex / 2);
                    }
                    if (loserMatch.getTeam1().equals("")) loserMatch.setTeam1(lost);
                    else loserMatch.setTeam2(lost);
                }
            }
        }
    }

    public void refreshLower() {
        for (int panelIndex = 0; panelIndex < lowerPanels.size(); panelIndex++) {
            for (int matchIndex = 0;
                    matchIndex < lowerPanels.get(panelIndex).getComponentCount();
                    matchIndex++) {
                if (panelIndex == lowerPanels.size() - 4) {
                    updateMatch(panelIndex, matchIndex);
                } else {
                    if (lowerPanels.get(panelIndex).getComponentCount() != 1) {
                        updateMatch(panelIndex, matchIndex);
                    } else {
                        Match match = (Match) lowerPanels.get(panelIndex).getComponent(matchIndex);
                        if (match.isNotFinished()) continue;
                        Match grandFinals =
                                (Match) upperPanels.get(upperPanels.size() - 2).getComponent(0);
                        grandFinals.setTeam2(match.getWinner());
                    }
                }
            }
        }
    }

    private void updateMatch(int panelIndex, int matchIndex) {
        if (lowerPanels.get(panelIndex).getComponent(matchIndex) instanceof Match) {
            Match match = (Match) lowerPanels.get(panelIndex).getComponent(matchIndex);
            if (match.isNotFinished()) {
                return;
            }
            String won = match.getWinner();
            if (lowerPanels.get(panelIndex + 1).getComponentCount() <= matchIndex) {
                Match nextMatch =
                        (Match) lowerPanels.get(panelIndex + 1).getComponent(matchIndex / 2);
                if (nextMatch.getTeam1().equals("")) nextMatch.setTeam1(won);
                else if (nextMatch.getTeam2().equals("")) {
                    nextMatch.setTeam2(won);
                }
            } else if (lowerPanels.get(panelIndex + 1).getComponent(matchIndex) instanceof Match) {
                Match nextMatch = (Match) lowerPanels.get(panelIndex + 1).getComponent(matchIndex);
                if (nextMatch.getTeam1().equals("")) {
                    nextMatch.setTeam1(won);
                } else if (nextMatch.getTeam2().equals("")) {
                    nextMatch.setTeam2(won);
                }
            }
        }
    }
}
