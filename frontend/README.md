# Treasure Hunter Frontend

Minimal React (Vite) frontend for the Treasure Hunter game.

Install and run:

```bash
cd frontend
npm install
npm run dev
```

By default it expects backend APIs at the same origin:
- POST /game/start
- POST /game/play
- GET /game/{userId}
- GET /game/leaderboard

Notes:
- Simple UI, 5x5 grid, cells are 0-based coordinates.
- LocalStorage stores `userId` after starting game.
