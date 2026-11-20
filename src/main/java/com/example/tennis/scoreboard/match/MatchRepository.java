package com.example.tennis.scoreboard.match;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("""
            SELECT m FROM Match m 
            WHERE LOWER(m.player1.name) = LOWER(:playerName )
            OR LOWER(m.player2.name) = LOWER(:playerName)
            """)
    Page<Match> findByPlayerName(@Param("playerName") String playerName, Pageable pageable);

}
