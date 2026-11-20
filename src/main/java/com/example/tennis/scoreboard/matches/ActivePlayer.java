package com.example.tennis.scoreboard.matches;

public class ActivePlayer {
    private String name;
    private Points points = Points.ZERO;
    private Integer games = 0;
    private Integer sets = 0;
    private Integer tieBreakPoints = null;

    public ActivePlayer() {
    }

    public ActivePlayer(String name) {
        this.name = name;
    }

    public ActivePlayer(String name, Points points, Integer games, Integer sets, Integer tieBreakPoints) {
        this.name = name;
        this.points = points;
        this.games = games;
        this.sets = sets;
        this.tieBreakPoints = tieBreakPoints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Points getPoints() {
        return points;
    }

    public void setPoints() {
        this.points = switch (points) {
            case ZERO -> Points.FIFTEEN;
            case FIFTEEN -> Points.THIRTY;
            case THIRTY -> Points.FORTY;
            case FORTY -> Points.AD;
            case AD -> Points.ZERO;
        };
    }

    public void setPoints(Points points) {
        this.points = points;
    }

    public void resetPoints() {
        this.points = Points.ZERO;
    }

    public void winGame() {
        this.games += 1;
    }

    public Integer getGames() {
        return games;
    }

    public void incrementGames() {
        this.games ++;
    }

    public void resetGames() {
        this.games = 0;
    }

    public Integer getSets() {
        return sets;
    }

    public void incrementSets() {
        this.sets ++;
    }

    public void resetSets() {
        this.sets = 0;
    }

    public Integer getTieBreakPoints() {
        return tieBreakPoints;
    }

    public void setTieBreakPoints(Integer tieBreakPoints) {
        this.tieBreakPoints = tieBreakPoints;
    }

    public void incrementTieBreakPoints() {
        this.tieBreakPoints ++;
    }

    public void resetTieBreakPoints() {
        this.tieBreakPoints = 0;
    }

    @Override
    public String toString() {
        return "ActivePlayer{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", games=" + games +
                ", sets=" + sets +
                ", tieBreakPoints=" + tieBreakPoints +
                '}';
    }
}
