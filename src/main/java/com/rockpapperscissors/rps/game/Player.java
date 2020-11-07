package com.rockpapperscissors.rps.game;

import lombok.Data;

@Data
public class Player {
    private String username;
    private PlayerMoves move;
    private boolean matched;

    public Player(String username){
        this.username = username;
    }

    public PlayerMoves getMove() {
        if(this.move !=null)
            return this.move;
        return null;
    }
}
