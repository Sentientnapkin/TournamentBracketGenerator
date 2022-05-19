package org.BracketTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SingleElimination implements ActionListener {
  private int bracketSize;
  private final int boX;
  private final ArrayList<String> players;
  private ArrayList<JPanel> panels;
  JFrame frame;
  JButton refresh;
  JPanel win;
  JLabel winner;

  public SingleElimination(ArrayList<String> players, int boX) {

    int playerAmount = players.size();

    this.players = players;

    int num = 0;
    while (Math.pow(2, num) < playerAmount) {
      num++;
    }
    this.bracketSize = (int) (Math.pow(2, num));
    if (this.bracketSize> playerAmount) {
      this.bracketSize /=2;
    }

    this.boX = boX;

    panels = new ArrayList<>();

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
    }

    int playersIndex = 0; //populate initial rounds with matches
    while (playersIndex < (bracketSize-1)) {
      Match match = new Match(boX, players.get(playersIndex), players.get(playersIndex + 1));
      panels.get(0).add(match);
      playersIndex += 2;
    }
    if (playersIndex < (players.size() - 1)) {
      Match match = new Match(boX, players.get(playersIndex), "bye");
      panels.get(0).add(match);
    }

    int n = bracketSize/2;//populating the rest of the rounds with matches
    if(players.size() >= 2) {
      for(int i = 1; i < panels.size()-1; i++) {
        for(int games = 0; games<n; games+=2) {
          System.out.println(games);
          Match match = new Match(boX, "","");
          panels.get(i).add(match);
        }
        n /= 2;
      }
    }

    win = new JPanel();
    panels.add(win);
    frame.add(win);

    winner = new JLabel("");
    win.add(winner);

    refresh = new JButton("Refresh");
    refresh.addActionListener(this);
    frame.add(refresh);

    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    if (ae.getActionCommand().equals("Refresh")) {
      for (int panelIndex = 0; panelIndex < panels.size()-2; panelIndex++) {
        for (int matchIndex = 0; matchIndex < panels.get(panelIndex).getComponentCount(); matchIndex++) {
          System.out.println(panelIndex);
          if(panels.get(panelIndex).getComponent(matchIndex) instanceof Match) {
            Match match = (Match) panels.get(panelIndex).getComponent(matchIndex);
            if(match.isFinished()) {
              String won = match.getWinner();
              if (panelIndex + 1 == panels.size()-2) {
                winner.setText(won+" wins!");
              }
              if (panels.get(panelIndex+1).getComponent(matchIndex/2) instanceof Match) {
                Match nextMatch = (Match) panels.get(panelIndex+1).getComponent(matchIndex/2);
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