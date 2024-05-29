package com.example.player.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.player.model.Player;
import com.example.player.service.PlayerH2Service;

import java.util.*;

@RestController
public class PlayerController {
    @Autowired
    public PlayerH2Service ps;

    @GetMapping("/players")
    public ArrayList<Player> getPlayer() {
        return ps.getPlayer();
    }

    @GetMapping("/players/{playerId}")
    public Player getPlayer(@PathVariable("playerId") int playerId) {
        return ps.getPlayer(playerId);
    }

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player playing) {
        return ps.addPlayer(playing);
    }

    @PutMapping("/players/{playerId}")
    public Player updatePlayer(@PathVariable("playerId") int playerId, @RequestBody Player player) {
        return ps.updatePlayer(playerId, player);
    }

    @DeleteMapping("/players/{playerId}")
    public ArrayList<Player> deletePlayer(@PathVariable("playerId") int playerId) {
        return ps.deletePlayer(playerId);
    }
}