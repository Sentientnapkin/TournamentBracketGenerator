package org.BracketTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RoundRobin implements ActionListener {
  int boX; // best of x, the maximum number of games needed to decided who has won
  ArrayList<String> players; // list of all the players being given to the RoundRobin Object
  JFrame frame;
  JPanel totalScores, matchesPanel;
  JLabel winner;
  JButton refresh;

  public RoundRobin(ArrayList<String> players, int boX) {
    this.players = players;
    this.boX = boX;

    int bracketSize = players.size() - 1; // number of players in the bracket

    frame = new JFrame("Round Robin");
    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    totalScores = new JPanel(); //this panel will hold the total scores of every team in the event
    totalScores.setLayout(new BoxLayout(totalScores, BoxLayout.Y_AXIS));
    frame.add(totalScores);

    winner = new JLabel(""); //creating the winner label for when a team wins
    frame.add(winner);

    matchesPanel = new JPanel(); //this panel will hold every match that a team will play
    matchesPanel.setLayout(new BoxLayout(matchesPanel, BoxLayout.Y_AXIS));
    frame.add(matchesPanel);

    for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
      TournamentScorePanel t = new TournamentScorePanel(players.get(playerIndex));
      totalScores.add(t); //creates a new panel for every team

      JPanel panel = new JPanel(); //creates a new row of matches that will exist in matchesPanel
      panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
      matchesPanel.add(panel);
      for (int playerIndex2 = playerIndex + 1; playerIndex2 < players.size(); playerIndex2++) { //only accesses matches that have
        //not been played, creating every match for one team, and then creating every match for the next team minus the one against the first team
        //and so on
        Match match = new Match(boX, players.get(playerIndex), players.get(playerIndex2));
        panel.add(match); //adds matches to the above row
      }
    }

    //the refresh button makes updates every match manually, telling the total scores if someone has won or not
    refresh = new JButton("Refresh");
    refresh.addActionListener(this);
    frame.add(refresh);

    frame.pack();
    frame.setVisible(true);
  }

  public void actionPerformed(java.awt.event.ActionEvent e) {
    if (e.getActionCommand().equals("Refresh")) {
      for (int totalScoresIndex = 0; totalScoresIndex < totalScores.getComponentCount(); totalScoresIndex++) {
        //setting every team's total tournament score to zero so that the scores don't overlap every time you refresh
        TournamentScorePanel t = (TournamentScorePanel) totalScores.getComponent(totalScoresIndex);
        t.setScore(0, 0);
      }
      boolean allFinished = true; //this is used to make sure that all the matches are finished
      for (int roundIndex = 0; roundIndex < matchesPanel.getComponentCount(); roundIndex++) {
        JPanel panel = (JPanel) matchesPanel.getComponent(roundIndex);
        //accessing every panel in the matchesPanel
        for (int matchIndex = 0; matchIndex < panel.getComponentCount(); matchIndex++) {
          //accessing every match in every panel in the matchesPanel
          Match match = (Match) panel.getComponent(matchIndex);
          if (match.isFinished()) {
            //if the match is finished, then we add the scores to the total scores
            //if a team won they get 1 win and if they lost they get 1 loss
            String winner = match.getWinner();
            String loser = match.getLoser();
            TournamentScorePanel winnerScore = (TournamentScorePanel) totalScores.getComponent(players.indexOf(winner));
            winnerScore.addWin();
            TournamentScorePanel loserScore = (TournamentScorePanel) totalScores.getComponent(players.indexOf(loser));
            loserScore.addLoss();
          } else {
            allFinished = false; //if a match is not finished, then it sets allFinished to false
          }
        }
      }
      if (allFinished) {//if all the matches are finished it will then look for the winner
        int largestIndex = -1;
        int largestScore = -1;
        boolean tie = false; //this is used to see if there is a tie
        for (int totalScoresIndex = 0; totalScoresIndex < totalScores.getComponentCount(); totalScoresIndex++) {
          //accessing every team in the tournament score panel
          //checking to see which has the biggest win amount and the index of that team
          TournamentScorePanel t = (TournamentScorePanel) totalScores.getComponent(totalScoresIndex);
          if (t.getWins() > largestScore) {
            largestIndex = totalScoresIndex;
            largestScore = t.getWins();
          } else if (t.getWins() == largestScore) { //if two teams have the same amount of wins, then there is a tie
            tie = true;
          }
        }
        if (!tie) { //if there is a tie, there is no tiebreaker, and it simply ends in a draw
          winner.setText(players.get(largestIndex) + " wins!");
        } else winner.setText("There was a Tie :(");
      }
    }
  }
}
