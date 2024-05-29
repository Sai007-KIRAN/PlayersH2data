package com.example.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import org.springframework.dao.EmptyResultDataAccessException;
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

        // Player upPlay = db.queryForObject("SELECT * FROM TEAM where playerId = ?",
        // new PlayerRowMapper(), playerId);
        // if (upPlay == null) {
        // throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // }

        try {
            Player existing = db.queryForObject("SELECT * FROM TEAM WHERE playerId = ?", new PlayerRowMapper(),
                    playerId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (player.getPlayerName() != null) {
            db.update("UPDATE TEAM  SET playerName = ? where playerId =? ", player.getPlayerName(), playerId);
        }
        if (player.getJerseyNumber() != 0) {
            db.update("UPDATE TEAM  SET jerseyNumber = ? where playerId =?", player.getJerseyNumber(), playerId);
        }
        if (player.getRole() != null) {
            db.update("UPDATE TEAM  SET role = ? where playerId =?", player.getRole(), playerId);
        }
        // return db.queryForObject("SELECT * FROM TEAM WHERE playerId = ?", new
        // PlayerRowMapper(), playerId);
        return getPlayer(playerId);
    }

    @Override
    public void deletePlayer(int playerId) {
        db.update("delete from team where playerId = ?", playerId);
    }
}
