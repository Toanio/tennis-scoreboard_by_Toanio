package com.example.tennis.scoreboard.matches;

import com.example.tennis.scoreboard.players.Players;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Matches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "player1")
    Players player1;

    @ManyToOne
    @JoinColumn(name = "player2")
    Players player2;

    @ManyToOne
    @JoinColumn(name = "winner")
    Players winner;

    public Matches() {
    }

    public Matches(Integer id, Players player1, Players player2, Players winner) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

    public Matches(Players player1, Players player2, Players winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Players getPlayer1() {
        return player1;
    }

    public void setPlayer1(Players player1) {
        this.player1 = player1;
    }

    public Players getPlayer2() {
        return player2;
    }

    public void setPlayer2(Players player2) {
        this.player2 = player2;
    }

    public Players getWinner() {
        return winner;
    }

    public void setWinner(Players winner) {
        this.winner = winner;
    }
}
