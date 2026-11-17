package com.example.tennis.scoreboard.Matches;

import com.example.tennis.scoreboard.Players.Players;

import java.util.NoSuchElementException;

public class ActiveMatch {
    private final ActivePlayer player1;
    private final ActivePlayer player2;
    String winnerName = null;
    boolean isTieBreak = false;
    final int SET_TO_WIN = 2;

    public ActiveMatch(ActivePlayer player1, ActivePlayer player2) {
        this.player1 = player1;
        this.player2 = player2;

        if (player1.getGames() == 6 && player2.getGames() == 6) {
            isTieBreak = true;
        }
    }

    public void addPoint(String playerName) {
        ActivePlayer player = findPlayer(playerName);
        ActivePlayer opponent = getOpponent(player);

        if (isTieBreak) {
            player.incrementTieBreakPoints();
            checkTieBreakPoints(player, opponent);
        } else {
            scoreRegularGame(player, opponent);
        }
    }

    private void scoreRegularGame(ActivePlayer player, ActivePlayer opponent) {
        if (checkDeuce(player, opponent) ) {
            player.setPoints(Points.AD);
        } else if (isOpponentAD(opponent)) {
            player.setPoints(Points.FORTY);
            opponent.setPoints(Points.FORTY);
        } else if (isWinningPoint(player, opponent) || isPlayerAD(player)){
            player.incrementGames();
            checkSets(player, opponent);
            resetPoints(player, opponent);
        } else {
            player.setPoints();
        }
    }

    private void checkSets(ActivePlayer player, ActivePlayer opponent) {
        if (player.getGames() >= 6 && player.getGames() - opponent.getGames() >= 2){
            player.incrementSets();
            checkMatchWinner(player);
            resetGames(player, opponent);
        } else if (player.getGames() == 6 && opponent.getGames() == 6) {
            isTieBreak = true;
        }
    }

    private void checkTieBreakPoints(ActivePlayer player, ActivePlayer opponent) {
        if (player.getTieBreakPoints() >= 7 && player.getTieBreakPoints() - opponent.getTieBreakPoints() >= 2 ) {
            isTieBreak = false;
            player.incrementSets();
            checkMatchWinner(player);
            resetPoints(player, opponent);
            resetGames(player, opponent);
            resetTieBreakPoints(player, opponent);
        }
    }

    private void checkMatchWinner(ActivePlayer player) {
        if (player.getSets() == SET_TO_WIN) {
            winnerName = player.getName();
        }
    }

    private boolean checkDeuce(ActivePlayer player, ActivePlayer opponent) {
        return opponent.getPoints() == Points.FORTY && player.getPoints() == Points.FORTY;
    }

    private boolean isOpponentAD(ActivePlayer opponent) {
        return opponent.getPoints() == Points.AD;
    }

    private boolean isPlayerAD(ActivePlayer player) {
        return player.getPoints() == Points.AD;
    }

    private boolean isWinningPoint(ActivePlayer player, ActivePlayer opponent) {
        return player.getPoints() == Points.FORTY
                && opponent.getPoints() != Points.FORTY
                && opponent.getPoints() != Points.AD;
    }

    private ActivePlayer findPlayer(String playerName) {
        if (playerName.equals(player1.getName())){
            return player1;
        }

        if (playerName.equals(player2.getName())){
            return player2;
        }

        throw new NoSuchElementException("Player " + playerName +"not found");
    }

    private ActivePlayer getOpponent(ActivePlayer player) {
        if (player == player1) {
            return player2;
        }
        return player1;
    }

    public void resetPoints(ActivePlayer player, ActivePlayer opponent) {
        player.resetPoints();
        opponent.resetPoints();
    }

    public void resetGames(ActivePlayer player, ActivePlayer opponent) {
        player.resetGames();
        opponent.resetGames();
    }

    public void resetTieBreakPoints(ActivePlayer player, ActivePlayer opponent) {
        player.resetTieBreakPoints();
        opponent.resetTieBreakPoints();
    }

    public ActivePlayer getPlayer1() {
        return player1;
    }

    public ActivePlayer getPlayer2() {
        return player2;
    }
}
