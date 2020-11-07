package com.rockpapperscissors.rps.controllers;

import com.rockpapperscissors.rps.game.*;
import com.rockpapperscissors.rps.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class MainController {


    @Autowired
    GameService gameService;

    @MessageMapping("/start")
    @SendTo("/topic/start")
    public GameSession register(int bestOf){
        return gameService.startGame(bestOf);
    }

    @MessageMapping("/getGame")
    @SendTo("/topic/gameDetails")
    public GameSession getGameData(){
        return gameService.getGame();
    }

    @MessageMapping("/registerPlayer")
    @SendTo("/topic/registered")
    public String registered(String username){
        gameService.registerPlayer(username);
        return "Player: " +username+ "registered";
    }

    @MessageMapping("/makeMove")
    @SendTo("topic/move")
    public PlayerMoves moves(InputData inputData){
        PlayerMoves playerMove = null;
        for (PlayerMoves p : PlayerMoves.values()) {
            if(p.equals(inputData.getMove())){
                playerMove = p;
                break;
            }
        }
        gameService.move(inputData.getUsername(), playerMove);
        return playerMove;
    }

    @MessageMapping("/getWinner")
    @SendTo("/topic/getWinner")
    public String[] getResult() throws Exception {
        return gameService.makeDecision();
    }

    @MessageMapping("/resetGame")
    @SendTo("/topic/resetGame")
    public void resetGame(){
        gameService.resetGame();
    }

}
