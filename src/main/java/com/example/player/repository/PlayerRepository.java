// Write your code here

package com.example.player.repository;

import java.util.*;
import com.example.player.model.Player;

public interface PlayerRepository {
    ArrayList<Player> getPlayer();

    Player getPlayer(int playerId);

    Player addPlayer(Player playing);

    Player updatePlayer(int playerId, Player player);

    ArrayList<Player> deletePlayer(int playerId);
}