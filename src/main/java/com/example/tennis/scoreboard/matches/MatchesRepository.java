package com.example.tennis.scoreboard.matches;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchesRepository extends JpaRepository<Matches, Long> {

    @Query("""
            SELECT m FROM Matches m 
            WHERE LOWER(m.player1.name) = LOWER(:playerName )
            OR LOWER(m.player2.name) = LOWER(:playerName)
            """)
    Page<Matches> findByPlayerName(@Param("playerName") String playerName, Pageable pageable);

}
