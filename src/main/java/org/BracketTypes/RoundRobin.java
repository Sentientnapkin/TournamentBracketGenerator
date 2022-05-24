package org.BracketTypes;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RoundRobin implements ActionListener {
  int boX;
  ArrayList<String> players;
  JFrame frame;
  JPanel totalScores, matchesPanel;
  JButton refresh;

  public RoundRobin(ArrayList<String> players, int boX) {
    this.players = players;
    this.boX = boX;

    int bracketSize = players.size()-1;

    frame = new JFrame("Round Robin");
    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    totalScores = new JPanel();
    totalScores.setLayout(new BoxLayout(totalScores, BoxLayout.Y_AXIS));
    frame.add(totalScores);

    matchesPanel = new JPanel();
    matchesPanel.setLayout(new BoxLayout(matchesPanel, BoxLayout.Y_AXIS));
    frame.add(matchesPanel);

    for(int playerIndex = 0;playerIndex<players.size();playerIndex++) {
      TournamentScorePanel t = new TournamentScorePanel(players.get(playerIndex));
      totalScores.add(t);

      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
      matchesPanel.add(panel);
      for(int playerIndex2 = playerIndex+1;playerIndex2<players.size();playerIndex2++) {
        Match match = new Match (boX, players.get(playerIndex), players.get(playerIndex2));
        panel.add(match);
      }
    }

    refresh = new JButton("Refresh");
    refresh.addActionListener(this);
    frame.add(refresh);

    frame.pack();
    frame.setVisible(true);
  }

  public void actionPerformed(java.awt.event.ActionEvent e) {
    if(e.getActionCommand().equals("Refresh")) {
      for(int roundIndex = 0;roundIndex<matchesPanel.getComponentCount();roundIndex++) {
        JPanel panel = (JPanel)matchesPanel.getComponent(roundIndex);
        for(int matchIndex = 0;matchIndex<panel.getComponentCount();matchIndex++) {
          Match match = (Match)panel.getComponent(matchIndex);
          if(match.isFinished()) {
            String winner = match.getWinner();
            String loser = match.getLoser();
            TournamentScorePanel winnerScore = (TournamentScorePanel) totalScores.getComponent(players.indexOf(winner));
            winnerScore.addWin();
            TournamentScorePanel loserScore = (TournamentScorePanel) totalScores.getComponent(players.indexOf(loser));
            loserScore.addLoss();
          }
        }
      }
    }
  }
}
