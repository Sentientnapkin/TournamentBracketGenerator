package org.BracketTypes;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    ArrayList<String> players = new ArrayList<String>();
    players.add("FaZe");
    players.add("BDS");
    players.add("G2");
    players.add("Moist Esports");
    players.add("SSG");
    players.add("Ghost");
    players.add("Semper");
    players.add("Solary");
    //SingleElimination b1 = new SingleElimination(players, 5);
    DoubleElimination b2 = new DoubleElimination(players, 5);
  }
}

