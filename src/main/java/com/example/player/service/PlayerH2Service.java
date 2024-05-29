package com.example.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.player.model.PlayerRowMapper;
import com.example.player.model.Player;
import com.example.player.repository.PlayerRepository;

@Service
public class PlayerH2Service implements PlayerRepository {
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Player> getPlayer() {
        List<Player> plays = db.query("SELECT * FROM TEAM", new PlayerRowMapper());
        ArrayList<Player> playerArray = new ArrayList<>(plays);
        return playerArray;
    }

    @Override
    public Player getPlayer(int playerId) {
        try {
            Player play = db.queryForObject("Select * From TEAM WHERE playerId = ?", new PlayerRowMapper(), playerId);
            return play;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Player addPlayer(Player playing) {
        db.update("insert into TEAM(playername, jerseyNumber, role) VALUES(?, ?, ?)", playing.getPlayerName(),
                playing.getJerseyNumber(), playing.getRole());
        Player played = db.queryForObject("select * from TEAM where playerName = ? and jerseyNumber = ? and role = ?",
                new PlayerRowMapper(), playing.getPlayerName(), playing.getJerseyNumber(), playing.getRole());
        return played;
    }

    @Override
    public Player updatePlayer(int playerId, Player player) {

        Player upPlay = db.queryForObject("SELECT * FROM TEAM WHERE PlayerId = ?", new PlayerRowMapper(), playerId);
        if (upPlay == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (player.getPlayerName() != null) {
            db.update("UPDATE TEAM  SET playerName = ?", player.getPlayerName(), playerId);
        }
        if (player.getJerseyNumber() >= 0) {
            db.update("UPDATE TEAM  SET jerseyNumber = ?", player.getJerseyNumber(), playerId);
        }
        if (player.getRole() != null) {
            db.update("UPDATE TEAM  SET role = ?", player.getRole(), playerId);
        }
        return getPlayer(playerId);
    }

    @Override
    public ArrayList<Player> deletePlayer(int playerId) {
        db.update("DELETE FROM TEAM WHERE playerId = ?", playerId);
        List<Player> delPlayer = db.query("SELECT * FROM TEAM", new PlayerRowMapper());
        ArrayList<Player> deleting = new ArrayList<>(delPlayer);
        return deleting;
    }
}