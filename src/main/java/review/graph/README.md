# Graph algorithms

## General traversal template.

```
void traverse(source):
	captured[source]=1;

	while there is a fringe edge
		pick one => (u,v) // how we pick determines the algorithm
		captured[v]=1;
		parent[v]=u;
```

|         Algorithm         |                  Policy               |     Search Tree   |
|---------------------------|---------------------------------------|-------------------|
| 1. Breadth-first			| Choose which was seen first			| BFS				|
| 2. Depth-first			| Choose which was seen last			| DFS				|
| 3. Dijkstra's				| Choose which with minimum RHS label	| Shortest path		|
| 4. Prim's					| Same as above							| Min Span (MST)    |
| 5. Best-first				| Same as above							| Best-first		|
| 6. A*						| Similar as above but adds two labels	| A* Tree			|

## BFS: Breadth-first-search

Complexity = O(m+n), where m is the number of edges and n nodes.

### Using loop

```
void bfs(s):
	captured[s]=1;
	visited[s]=1;

	q=new Queue();
	q.push(s);
	while not isEmpty(q): // capture the next vertex
		v = q.pop();
		captured[v]=1;
		for w in adjList[v]:
			if visited[w]==0 then
				visited[w]=1;
				parent[w]=v;
				q.push(w);
```

### Using loop

## DFS: Depth-first-search

Complexity = O(m+n), where m is the number of edges and n nodes.

```
void dfs(s):
	captured[s]=1;
	visited[s]=1;

	q=new Stack();
	q.push(s);
	while not isEmpty(q): // capture the next vertex
		v = q.pop();
		captured[v]=1;
		for w in adjList[v]:
			if visited[w]==0 then
				visited[w]=1;
				parent[w]=v;
				q.push(w);
```

### Using recursion

```
void dfs(s):
	visited[s]=1;
	for w in adjList[s]:
		if visited[w]==0 then
			parent[w]=s;
			dfs(w);
```

## MST: Minimum-spanning-tree

Contains algorithm for weighted-quick-find, the Kruskal’s and Prim's algorithm for MST.

Complexity = O(nlog(n)) and O(n) amortized if using path compression.

```
int wqf(edges, n):
	int comp=n;
	int[] parent = new int[n]  and init with parent[i]=i, i=0...n;
	int[] size = new int[n] and init with 1;
	for each edge in edges:
		u=find(edge.source);
		v=find(edges.dest);
		if(v==u) continue // already connected, no need to connect again
		// weight and connect
		if(size[u]>=size[v]) then
			parent[v]=u;
			size[u] += size[v];
			comp--;
		else
			parent[u]=v;
			size[v] += size[u];
			comp--;
	return comp;	
```

