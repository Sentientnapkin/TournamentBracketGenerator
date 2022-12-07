package org.BracketTypes;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    ArrayList<String> players = new ArrayList<String>();
    players.add("Zara");
    players.add("Rohan");
    players.add("Jacob");
    players.add("Bruno");
    players.add("Adrian");
    players.add("Sebastian");
    players.add("Savonnah");
    players.add("Nathan");
    //SingleElimination b1 = new SingleElimination(players, 5);
    //DoubleElimination b2 = new DoubleElimination(players, 5);
    RoundRobin b3 = new RoundRobin(players, 5);
  }
}

