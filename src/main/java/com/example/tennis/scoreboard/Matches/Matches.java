package com.example.tennis.scoreboard.Matches;

import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Matches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer player1;

    Integer player2;

    String winnerName;

    public Matches(Integer id, Integer player1, Integer player2, String winnerName) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.winnerName = winnerName;
    }

    public Matches(Integer player1, Integer player2, String winnerName) {
        this.player1 = player1;
        this.player2 = player2;
        this.winnerName = winnerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlayer1() {
        return player1;
    }

    public void setPlayer1(Integer player1) {
        this.player1 = player1;
    }

    public Integer getPlayer2() {
        return player2;
    }

    public void setPlayer2(Integer player2) {
        this.player2 = player2;
    }
}
