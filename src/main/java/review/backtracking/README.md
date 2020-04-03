# Backtracking algorithms

Backtracking is an algorithmic-technique for solving problems recursively by trying to build a solution incrementally.
Recording the partial/in-progress solution in a scratch pad (let's call it solution board). As we increment forward,
if a solution is found, or the solution for the current trial becomes invalid (fails some problem specific constraint), 
short-circuit the process, and backtrack to erase the board, and then try repeat the same process with the next 
possibility.

```
void solve(level, board, result):
	// 1. Exit condition
    if(board.isDone(level)) {
        board.store(result)
        return;    	
   	}
    // 2. Try all the options within the constraint
    for each valid choice: 
    	// 2.1. update/set the solution board for the choice
        board.set(level, choice) // conditional
    	// 2.2. drill down to the next level until reaching the bottom (Exit condition)
        solve(level+1, board, result)
        board.unSet(level, choice) // conditional
    	// 2.3. revert/unset the solution board so to try out the next choice
```  

## Discussions
The backtracking algorithm pattern covers a broad ranges of problems. The pattern is 
simple but the actual implementations vary a lot from problem to problem and thus it is 
easy for one to be overwhelmed. In order to master this pattern, it is import to study 
the differences by applying the pattern to the various problems, compare the differences
in the implementations so that in practice, a solution can be relatively easily attained
with the proper structure.

Here, what we are trying to achieve is NOT something that is the most concise or clever, 
but rather, it is a well defined structure that can be easily applied to new problems 
that can be solved by this approach. 

In this algorithm pattern, the key points to consider are:

* The *solution board*. A solution board should keep the state of the on-going solution, 
and it should also keep the variables that allow you to determine whether you have found 
a solution. And finally, you should be able to extract the solutions found for each trial. 
In the case where you only care about whether there is a solution, you can short-circuit 
the program as soon as you find one. *Spend enough time defining the board is the key*.
* The *valid choices* in each level. As the algorithm proceeds to each level, there will
be a limited number of choices to proceed to the next level. Encapsulate inside the board
the logic to find the choices can help simplify the solution and making it easy to 
understand the implementation, and thus making it easier to implement and not getting
yourself lost.
* The *exit condition*. This can vary depends on what is asked for. For example for the 
KnightsTour problem it means that there are no more places on the board to place the 
next knight. *One important characteristics of this algorithm is that you populate the*
*solution board at the time of exit*. This is because you won't know whether you have 
found a solution until before you hit the exit condition. 
* The *set and unset of the board*. The solution board is like a scratch pad, you draft
on it the temporary state of the solution as you proceed through the solution. When you
hit the point where you have a solution, you take a snapshot of the board and store the
new solution. After that, you need to erase the temporary state so that you can try with
the next available choice. In the case where you cannot drill down half way (it means that
the current trial is not going to produce a solution), you also back track. *Depends on* 
*the problem, you might NOT need to do this erasure part if you have a mechanism to keep* 
*track of which part of the board is the scratch notes for the level*. For example, in the 
ParenthesesPairs problem, that mechanism is to keep track of the pointer.

List of problems using the backtracking method:

* [TopologicalSort](../graph/topologicalsort/TopologicalSort.java)
* [NQueen](NQueen2.java)
* [KnightsTour](KnightsTour.java)
* [ParencesPairs](ParencesPairs.java)
* [BinarySearchTreeCounter](BinarySearchTreeCounter.java)
* [RegExMatcher](RegExMatcher.java)
