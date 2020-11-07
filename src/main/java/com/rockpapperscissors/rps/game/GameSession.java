package com.rockpapperscissors.rps.game;

import lombok.Data;
import lombok.Setter;

@Data
public class GameSession {
    private Player playerOne;
    private Player playerTwo;
    private static GameSession gameSession = null;
    private int bestof;

   private GameSession(){
   }

   public static GameSession getGameSession(){
       if(gameSession == null) gameSession = new GameSession();
       return gameSession;
   }

   public void registerPlayerMove(String username, PlayerMoves playerMoves) {
        if (username.equalsIgnoreCase(this.playerOne.getUsername())) {
            this.playerOne.setMove(playerMoves);
        } else {
            this.playerTwo.setMove(playerMoves);
        }
   }

   public void registerPlayer(String username){
       if(this.playerOne == null) this.playerOne = new Player(username);
       else if(this.playerTwo == null) this.playerTwo = new Player(username);
       else System.out.println("Players Exist");
   }


   public PlayerMoves[] getMoves(){
       PlayerMoves[] playerMoves = new PlayerMoves[2];
       if(playerOne != null && playerTwo != null) {
           playerMoves[0] = playerOne.getMove();
           playerMoves[1] = playerTwo.getMove();
       }
       return playerMoves;
   }

    public void resetGame() {
       playerOne = null;
       playerTwo = null;
       bestof = 0;
    }
}
