package org.BracketTypes;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> players = new ArrayList<>();
        players.add("FaZe");
        players.add("BDS");
        players.add("G2");
        players.add("Moist");
        players.add("SSG");
        players.add("Ghost");
        players.add("Semper");
        players.add("Solary");
        new SingleElimination(players, 5);
        //     new DoubleElimination(players, 5);
        //  new RoundRobin(players, 5);
    }
}
