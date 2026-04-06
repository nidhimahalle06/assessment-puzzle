package com.example.treasurehunter.controller;

import com.example.treasurehunter.model.CellResult;
import com.example.treasurehunter.model.Game;
import com.example.treasurehunter.model.Position;
import com.example.treasurehunter.model.dto.*;
import com.example.treasurehunter.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // Start Game
    @PostMapping("/start")
    public ResponseEntity<StartGameResponse> startGame(@RequestBody StartGameRequest req) {
        if (req == null || req.getPlayerName() == null || req.getPlayerName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String userId = gameService.startGame(req.getPlayerName());
        return ResponseEntity.ok(new StartGameResponse(userId));
    }

    // Play Turn
    @PostMapping("/play")
    public ResponseEntity<?> play(@RequestBody PlayRequest req) {
        if (req == null || req.getUserId() == null) return ResponseEntity.badRequest().build();
        List<Position> positions = req.getPositions();
        if (positions == null) positions = new ArrayList<>();
        if (positions.size() > 3) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Max 3 positions allowed");

        Game g = gameService.getGame(req.getUserId());
        if (g == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");

        List<CellResult> results = gameService.playTurn(req.getUserId(), positions);
        PlayResponse resp = new PlayResponse(results, g.getTurnCount(), g.isCompleted());
        return ResponseEntity.ok(resp);
    }

    // Get game state
    @GetMapping("/{userId}")
    public ResponseEntity<?> getState(@PathVariable String userId) {
        GameStateResponse s = gameService.getGameState(userId);
        if (s == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        return ResponseEntity.ok(s);
    }

    // Leaderboard
    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntry>> leaderboard() {
        return ResponseEntity.ok(gameService.leaderboard());
    }
}
