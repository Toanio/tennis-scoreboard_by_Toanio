package com.example.tennis.scoreboard.players;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayersRepository extends JpaRepository<Players, Long> {
     Players findByNameIgnoreCase(String name);
}
