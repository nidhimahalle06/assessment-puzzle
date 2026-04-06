import React, { useEffect, useState } from 'react'

const BOARD_SIZE = 5

function coordKey(r, c) {
  return `${r},${c}`
}

export default function App() {
  const [userId, setUserId] = useState(localStorage.getItem('userId') || '')
  const [playerName, setPlayerName] = useState('')
  const [selected, setSelected] = useState([]) // array of "r,c"
  const [revealed, setRevealed] = useState(new Set())
  const [cellResults, setCellResults] = useState({}) // key -> { isTreasure, distance }
  const [turns, setTurns] = useState(0)
  const [gameCompleted, setGameCompleted] = useState(false)
  const [leaderboard, setLeaderboard] = useState([])

  useEffect(() => {
    if (userId) restoreGame(userId)
  }, [userId])

  async function restoreGame(id) {
    try {
      const res = await fetch(`/game/${id}`)
      if (!res.ok) return
      const data = await res.json()
      setTurns(data.turnCount || 0)
      setGameCompleted(Boolean(data.completed))
      setRevealed(new Set(data.revealed || []))
      // no treasure data returned — only revealed keys
    } catch (e) {
      console.error('restore failed', e)
    }
  }

  async function startGame() {
    if (!playerName) return
    try {
      const res = await fetch('/game/start', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ playerName })
      })
      if (!res.ok) return
      const data = await res.json()
      localStorage.setItem('userId', data.userId)
      setUserId(data.userId)
    } catch (e) {
      console.error(e)
    }
  }

  function toggleSelect(r, c) {
    const k = coordKey(r, c)
    if (revealed.has(k)) return
    setSelected(prev => {
      if (prev.includes(k)) return prev.filter(x => x !== k)
      if (prev.length >= 3) return prev
      return [...prev, k]
    })
  }

  function keyToPos(k) {
    const [r, c] = k.split(',').map(Number)
    return { row: r, col: c }
  }

  async function playTurn() {
    if (!userId) return
    if (selected.length === 0) return
    const positions = selected.map(keyToPos)
    try {
      const res = await fetch('/game/play', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, positions })
      })
      if (!res.ok) {
        const txt = await res.text()
        alert('Play failed: ' + txt)
        return
      }
      const data = await res.json()
      // update revealed and results
      const newRevealed = new Set(revealed)
      const newResults = { ...cellResults }
      for (const r of data.results || []) {
        const k = `${r.position.row},${r.position.col}`
        newRevealed.add(k)
        if (r.treasure || r.isTreasure) {
          newResults[k] = { isTreasure: true }
        } else {
          newResults[k] = { isTreasure: false, distance: r.distance }
        }
      }
      setRevealed(newRevealed)
      setCellResults(newResults)
      setSelected([])
      setTurns(data.totalTurns || turns + 1)
      setGameCompleted(Boolean(data.gameCompleted))
      if (data.gameCompleted) fetchLeaderboard()
    } catch (e) {
      console.error(e)
    }
  }

  async function fetchLeaderboard() {
    try {
      const res = await fetch('/game/leaderboard')
      if (!res.ok) return
      const data = await res.json()
      setLeaderboard(data)
    } catch (e) {
      console.error(e)
    }
  }

  function renderCell(r, c) {
    const k = coordKey(r, c)
    const isSelected = selected.includes(k)
    const isRevealed = revealed.has(k) || cellResults[k]
    const res = cellResults[k]

    let content = ''
    if (isRevealed && res) {
      if (res.isTreasure) content = '💎'
      else content = String(res.distance)
    } else if (isSelected) {
      content = '●'
    }

    return (
      <button
        key={k}
        className={`cell ${isRevealed ? 'revealed' : ''} ${isSelected ? 'selected' : ''}`}
        onClick={() => toggleSelect(r, c)}
        disabled={isRevealed}
      >
        {content}
      </button>
    )
  }

  return (
    <div className="app">
      <h1>Treasure Hunter</h1>
      {!userId && (
        <div className="start">
          <input value={playerName} onChange={e => setPlayerName(e.target.value)} placeholder="Your name" />
          <button onClick={startGame}>Start</button>
        </div>
      )}

      {userId && (
        <div className="game-area">
          <div className="info">
            <div>Player: {playerName || '—'}</div>
            <div>Turns: {turns}</div>
            <div>Found: {Object.values(cellResults).filter(x => x.isTreasure).length}</div>
            <div>Completed: {String(gameCompleted)}</div>
          </div>

          <div className="board">
            {Array.from({ length: BOARD_SIZE }).map((_, r) => (
              <div key={r} className="row">
                {Array.from({ length: BOARD_SIZE }).map((_, c) => renderCell(r, c))}
              </div>
            ))}
          </div>

          <div className="controls">
            <div>Selected: {selected.join(' | ')}</div>
            <button onClick={playTurn} disabled={selected.length === 0 || gameCompleted}>Play Turn</button>
            <button onClick={() => { localStorage.removeItem('userId'); setUserId(''); setPlayerName(''); }}>Reset</button>
          </div>

          {gameCompleted && (
            <div className="leaderboard">
              <h3>Leaderboard</h3>
              <ol>
                {leaderboard.map((e, i) => (
                  <li key={i}>{e.playerName} — {e.turns} turns</li>
                ))}
              </ol>
            </div>
          )}
        </div>
      )}
    </div>
  )
}
