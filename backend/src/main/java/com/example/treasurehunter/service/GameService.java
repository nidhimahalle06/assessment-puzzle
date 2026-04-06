package com.example.treasurehunter.service;

import com.example.treasurehunter.model.CellResult;
import com.example.treasurehunter.model.Game;
import com.example.treasurehunter.model.Position;
import com.example.treasurehunter.model.dto.GameStateResponse;
import com.example.treasurehunter.model.dto.LeaderboardEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private final Map<String, Game> games = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private static final int BOARD_SIZE = 5;
    private static final int TREASURE_COUNT = 3;

    public String startGame(String playerName) {
        String userId = UUID.randomUUID().toString();
        List<Position> treasures = generateTreasures();
        Game game = new Game(userId, playerName, treasures);
        games.put(userId, game);
        return userId;
    }

    private List<Position> generateTreasures() {
        Set<String> seen = new HashSet<>();
        List<Position> list = new ArrayList<>();
        while (list.size() < TREASURE_COUNT) {
            int r = random.nextInt(BOARD_SIZE);
            int c = random.nextInt(BOARD_SIZE);
            String key = r + "," + c;
            if (seen.add(key)) {
                list.add(new Position(r, c));
            }
        }
        return list;
    }

    public Game getGame(String userId) {
        return games.get(userId);
    }

    public List<CellResult> playTurn(String userId, List<Position> positions) {
        Game game = games.get(userId);
        if (game == null) return Collections.emptyList();

        // increase turn count for this play request
        game.setTurnCount(game.getTurnCount() + 1);

        List<CellResult> results = new ArrayList<>();

        for (Position p : positions) {
            if (p == null) continue;
            String key = p.getRow() + "," + p.getCol();
            boolean already = game.getRevealed().contains(key);
            boolean isTreasure = containsPosition(game.getTreasures(), p);
            if (already) {
                // do not change state
                if (isTreasure) {
                    results.add(new CellResult(p, true, null));
                } else {
                    int d = distanceToNearest(game.getTreasures(), p);
                    results.add(new CellResult(p, false, d));
                }
                continue;
            }

            // mark revealed
            game.getRevealed().add(key);

            if (isTreasure) {
                game.setFoundCount(game.getFoundCount() + 1);
                results.add(new CellResult(p, true, null));
            } else {
                int d = distanceToNearest(game.getTreasures(), p);
                results.add(new CellResult(p, false, d));
            }
        }

        if (game.getFoundCount() >= TREASURE_COUNT) {
            game.setCompleted(true);
        }

        return results;
    }

    private boolean containsPosition(List<Position> list, Position p) {
        for (Position t : list) {
            if (t.getRow() == p.getRow() && t.getCol() == p.getCol()) return true;
        }
        return false;
    }

    private int distanceToNearest(List<Position> treasures, Position p) {
        int best = Integer.MAX_VALUE;
        for (Position t : treasures) {
            int d = Math.abs(t.getRow() - p.getRow()) + Math.abs(t.getCol() - p.getCol());
            if (d < best) best = d;
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }

    public GameStateResponse getGameState(String userId) {
        Game g = games.get(userId);
        if (g == null) return null;
        GameStateResponse s = new GameStateResponse();
        s.setUserId(g.getUserId());
        s.setPlayerName(g.getPlayerName());
        s.setRevealed(g.getRevealed());
        s.setTurnCount(g.getTurnCount());
        s.setCompleted(g.isCompleted());
        s.setFoundCount(g.getFoundCount());
        return s;
    }

    public List<LeaderboardEntry> leaderboard() {
        // collect completed games and compute min turns per player
        Map<String, Integer> best = new HashMap<>();
        for (Game g : games.values()) {
            if (!g.isCompleted()) continue;
            String player = g.getPlayerName();
            int turns = g.getTurnCount();
            best.put(player, Math.min(best.getOrDefault(player, Integer.MAX_VALUE), turns));
        }

        List<LeaderboardEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Integer> e : best.entrySet()) {
            entries.add(new LeaderboardEntry(e.getKey(), e.getValue()));
        }

        entries.sort(Comparator.comparingInt(LeaderboardEntry::getTurns));
        if (entries.size() > 10) return entries.subList(0, 10);
        return entries;
    }
}
