package com.example.tennis.scoreboard.players;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Players {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name")
    String name;

    public Players() {
    }

    public Players(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Players(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
