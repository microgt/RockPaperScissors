package com.rockpapperscissors.rps.services;

import com.rockpapperscissors.rps.game.GameSession;
import com.rockpapperscissors.rps.game.PlayerMoves;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public GameSession startGame(int bestOf){
        GameSession gameSession = GameSession.getGameSession();
        gameSession.setBestof(bestOf);
        return gameSession;
    }

    public void registerPlayer(String username){
        GameSession.getGameSession().registerPlayer(username);
    }

    public void move(String username, PlayerMoves playerMoves){
        GameSession.getGameSession().registerPlayerMove(username, playerMoves);
    }

    public PlayerMoves[] getMoves(){
        return GameSession.getGameSession().getMoves();
    }

    public String[] makeDecision() throws Exception {
        return DecisionMaker.getMatchDecision();
    }

    public GameSession getGame(){
        return GameSession.getGameSession();
    }

    public void resetGame() {
        GameSession.getGameSession().resetGame();
    }
}
