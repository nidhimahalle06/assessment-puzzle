# Treasure Hunter - Spring Boot (in-memory)

Simple backend for a 5x5 treasure hunter game. Stores games in memory (HashMap).

Run:

```bash
mvn spring-boot:run
```

APIs:

- POST /game/start
  - Body: { "playerName": "Alice" }
  - Response: { "userId": "..." }

- POST /game/play
  - Body: { "userId": "...", "positions": [{"row":1,"col":2}, ...] }
  - Max 3 positions per request
  - Response: { "results": [...], "totalTurns": 1, "gameCompleted": false }

- GET /game/{userId}
  - Returns current game state (no treasure positions)

- GET /game/leaderboard
  - Returns top players (min turns) for completed games

Notes:
- Board coordinates are 0-based (0..4)
- No DB. All data lost on restart.
