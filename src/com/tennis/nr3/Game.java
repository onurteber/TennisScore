package com.tennis.nr3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private String player1Score = "0";
    private String player2Score = "0";
    private int setCount = 0;
    private int player1WinCount = 0;
    private int player2WinCount = 0;
    private boolean endOfGame = false;
    StringBuilder sb = new StringBuilder();
    private final Map<Integer, String> completedSetScores = new HashMap<>();

    private final String player1Name = Main.PLAYER1;
    private final String player2Name = Main.PLAYER2;
    private static HashMap<String, String> scores;

    Game() {
        scores = new HashMap<String, String>();
        scores.put("0", "15");
        scores.put("15", "30");
        scores.put("30", "40");
        scores.put("40", "A");
        scores.put("A", "game");
    }

    public void updateScore(String playerScored, boolean isNewGame) {
        if (isNewGame) {
            player1Score = "0";
            player2Score = "0";
            player1WinCount = 0;
            player2WinCount = 0;
        }

        String player1CurrentScore = currentPlayerScore(player1Name);
        String player2CurrentScore = currentPlayerScore(player2Name);

        boolean player1ScoreIs40 = ScoreIs40(player1Name);
        boolean player2ScoreIs40 = ScoreIs40(player2Name);

        switch (playerScored) {
            case player1Name:
                player1Score(playerScored, player1ScoreIs40, player2ScoreIs40, player1CurrentScore, player2CurrentScore);
                break;
            case player2Name:
                player2Score(playerScored, player1ScoreIs40, player2ScoreIs40, player1CurrentScore, player2CurrentScore);
                break;
            default:
                break;
        }

        completedSetScores.put(setCount, player1WinCount + "-" + player2WinCount);

        gameEnded();
    }

    private void player1Score(String playerScored, boolean player1ScoreIs40, boolean player2ScoreIs40, String player1CurrentScore, String player2CurrentScore) {
        if (playerScored.equals(player1Name)) {
            endOfGame = false;
            if (!player2ScoreIs40) {
                if (player1ScoreIs40) {
                    this.player1Score = "game";
                } else {
                    this.player1Score = scores.get(player1CurrentScore);
                }
                this.player2Score = player2CurrentScore;
            } else {
                if (player1ScoreIs40) {
                    if (player2CurrentScore.equals("A")) {
                        this.player1Score = "40";
                        this.player2Score = "40";
                    } else {
                        this.player1Score = scores.get(player1CurrentScore);
                        this.player2Score = player2CurrentScore;
                    }
                } else {
                    if (player2CurrentScore.equals("A")) {
                        this.player2Score = scores.get(player2CurrentScore);
                        this.player1Score = "40";
                    } else {
                        this.player1Score = scores.get(player1CurrentScore);
                        this.player2Score = player2CurrentScore;
                    }
                }
            }
        }
    }

    private void player2Score(String playerScored, boolean player1ScoreIs40, boolean player2ScoreIs40, String player1CurrentScore, String player2CurrentScore) {
        if(playerScored.equals(player2Name)) {
            endOfGame = false;
            if (!player1ScoreIs40) {
                if (player2ScoreIs40) {
                    this.player2Score = "game";
                } else {
                    this.player2Score = scores.get(player2CurrentScore);
                }
                this.player1Score = player1CurrentScore;
            } else {
                if (player2ScoreIs40) {
                    if (player1CurrentScore.equals("A")) {
                        this.player2Score = "40";
                        this.player1Score = "40";
                    } else {
                        this.player2Score = scores.get(player2CurrentScore);
                        this.player1Score = player1CurrentScore;
                    }
                } else {
                    if (player1CurrentScore.equals("A")) {
                        this.player1Score = scores.get(player1CurrentScore);
                        this.player2Score = "40";
                    } else {
                        this.player2Score = scores.get(player2CurrentScore);
                        this.player1Score = player1CurrentScore;
                    }
                }
            }
        }
    }

    private void isSetFinish() {
        if ((player1WinCount > 5 && player2WinCount < 5) || (player1WinCount < 5 && player2WinCount > 5)) {
            setFinished();
        } else if ((player1WinCount > 5 || player2WinCount > 5) && (player1WinCount - player2WinCount >= 2 || player2WinCount - player1WinCount >= 2)) {
            setFinished();
        }
    }

    private void setFinished() {
        completedSetScores.put(setCount, player1WinCount + "-" + player2WinCount);
        player1WinCount = 0;
        player2WinCount = 0;
        setCount++;
        completedSetScores.put(setCount, String.valueOf(player1WinCount).concat("-").concat(String.valueOf(player2WinCount)));
    }

    private boolean ScoreIs40(String currentPlayer) {
        String currentScore = currentPlayerScore(currentPlayer);
        return currentScore.equals("40") || currentScore.equals("A") || currentScore.equals("game");
    }

    public boolean gameHasEnded() {
        return player1Score.equals("game") || player2Score.equals("game");
    }

    private String currentPlayerScore(String player) {
        if (player.equals(player1Name)) {
            return player1Score;
        } else {
            return player2Score;
        }
    }

    public StringBuilder displayScore() throws IOException {
        if (endOfGame) {
            completedSetScores.forEach((key, value) -> sb.append(value));
            sb.append(System.lineSeparator());
        } else if ((!player1Score.equals("0") || !player2Score.equals("0")) || (player1WinCount != 0 || player2WinCount != 0)) {
            completedSetScores.forEach((key, value) -> sb.append(value).append(" "));
            sb.append(player1Score).append("-").append(player2Score).append(System.lineSeparator());
        } else {
            sb.append(System.lineSeparator());
        }
        completedSetScores.clear();
        return sb;
    }

    private void gameEnded() {
        if (gameHasEnded()) {
            if (player1Score.equals("game")) {
                player1WinCount++;
                player1Score = "0";
                player2Score = "0";
            } else {
                player2WinCount++;
                player2Score = "0";
                player1Score = "0";
            }
            endOfGame = true;
            completedSetScores.put(setCount, player1WinCount + "-" + player2WinCount);
            isSetFinish();
        }
    }
}