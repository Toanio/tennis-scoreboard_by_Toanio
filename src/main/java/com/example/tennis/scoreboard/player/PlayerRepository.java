package com.example.tennis.scoreboard.player;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
     Player findByNameIgnoreCase(String name);
}
