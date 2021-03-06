package me.healpot.scoreboard;

import java.util.ArrayList;

public class FakeTeam {
    private ArrayList<String> players = new ArrayList<String>();
    private String prefix;
    private boolean seeInvisibles;
    private String teamName;
    private String suffix;

    public FakeTeam(String teamName) {
        this.teamName = teamName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void addPlayer(String player) {
        players.add(player);
    }

    public boolean canSeeInvisiblePlayers() {
        return seeInvisibles;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getTeamName() {
        return teamName;
    }

    public void removePlayer(String player) {
        players.remove(player);
    }

    public void setSeeInvisiblePlayers(boolean seeInvisibles) {
        this.seeInvisibles = seeInvisibles;
    }

}