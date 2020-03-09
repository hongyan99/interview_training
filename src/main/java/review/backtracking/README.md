# Backtracking algorithms

Backtracking is an algorithmic-technique for solving problems recursively by trying to build a solution incrementally.
Recording the partial/in-progress solution in a scratch pad (let's call it solution board). As we increment forward,
if the solution becomes invalid (fails some problem specific constraint), short-circuit the process, and backtrack to 
erase the board, and then try repeat the same process with the next possibility.

```
void solve(level, board, result):
    if(board.isDone(level)) board.store(result)
    for each valid choice:
        board.set(level, choice)
        solve(level+1, board, result)
        board.unSet(level, choice)
```

Note that depends on the problem, the unSet step might not even needed