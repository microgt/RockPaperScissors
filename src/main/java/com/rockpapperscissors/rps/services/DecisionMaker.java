package com.rockpapperscissors.rps.services;

import com.rockpapperscissors.rps.game.GameOutcomes;
import com.rockpapperscissors.rps.game.GameSession;
import com.rockpapperscissors.rps.game.Player;
import com.rockpapperscissors.rps.game.PlayerMoves;

import java.util.ArrayList;

public class DecisionMaker {

    public static String[] getMatchDecision() throws Exception {
        Player playerOne = GameSession.getGameSession().getPlayerOne();
        Player playerTwo = GameSession.getGameSession().getPlayerTwo();
        PlayerMoves playerOneMove = GameSession.getGameSession().getMoves()[0];
        PlayerMoves playerTwoMove = GameSession.getGameSession().getMoves()[1];

        ArrayList<String> arrayList = new ArrayList<>();
        for (PlayerMoves p : PlayerMoves.values()) {
            arrayList.add(p.name());
        }

        if(!arrayList.contains(playerOneMove.toString()) && !arrayList.contains(playerTwoMove.toString())) throw new Exception("Invalid Move");

        if (playerOneMove == playerTwoMove) {
            return assignMatchOutcome(playerOne, playerTwo, GameOutcomes.DRAW, GameOutcomes.DRAW);
        }
        if (playerOneMove == PlayerMoves.ROCK && playerTwoMove == PlayerMoves.PAPER) {
            return assignMatchOutcome(playerOne, playerTwo, GameOutcomes.LOSE, GameOutcomes.WIN);
        } else if (playerOneMove == PlayerMoves.PAPER && playerTwoMove == PlayerMoves.ROCK) {
            return assignMatchOutcome(playerOne, playerTwo, GameOutcomes.WIN, GameOutcomes.LOSE);
        } else if (playerOneMove == PlayerMoves.ROCK && playerTwoMove == PlayerMoves.SCISSORS) {
            return assignMatchOutcome(playerOne, playerTwo, GameOutcomes.WIN, GameOutcomes.LOSE);
        } else if (playerOneMove == PlayerMoves.SCISSORS && playerTwoMove == PlayerMoves.ROCK
        ) {
            return assignMatchOutcome(playerOne, playerTwo, GameOutcomes.LOSE, GameOutcomes.WIN);
        } else if (playerOneMove == PlayerMoves.SCISSORS && playerTwoMove == PlayerMoves.PAPER
        ) {
            return assignMatchOutcome(playerOne, playerTwo, GameOutcomes.WIN, GameOutcomes.LOSE);
        } else if (playerOneMove == PlayerMoves.PAPER && playerTwoMove == PlayerMoves.SCISSORS) {
            return assignMatchOutcome(
                    playerOne,
                    playerTwo,
                    GameOutcomes.LOSE,
                    GameOutcomes.WIN
            );
        }
        return null;
    }

    private static String[] assignMatchOutcome(Player playerOne, Player playerTwo, GameOutcomes playerOneOutcome, GameOutcomes playerTwoOutcome) {
        String[] strings = new String[2];
        strings[0] = (playerOne.getUsername() + "-" + playerOneOutcome);
        strings[1] = (playerTwo.getUsername() + "-" + playerTwoOutcome);
        return strings;
    }
}
