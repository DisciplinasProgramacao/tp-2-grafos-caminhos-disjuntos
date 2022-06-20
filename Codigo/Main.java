
// Java program to find maximum number
// of edge disjoint paths
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import src.graph.Graph;

public class Main {

	// Number of vertices in given graph
	static int V;

	/*
	 * Returns true if there is a path from
	 * source 's' to sink 't' in residual graph.
	 * Also fills parent[] to store the path
	 */
	static boolean bfs(int rGraph[][], int s,
			int t, int parent[]) {
		// Create a visited array and
		// mark all vertices as not visited

		V = rGraph.length;
		boolean[] visited = new boolean[V];

		// Create a queue, enqueue source vertex and
		// mark source vertex as visited
		Queue<Integer> q = new LinkedList<>();
		q.add(s);
		visited[s] = true;
		parent[s] = -1;

		// Standard BFS Loop
		while (!q.isEmpty()) {
			int u = q.peek();
			q.remove();

			for (int v = 0; v < V; v++) {
				if (visited[v] == false &&
						rGraph[u][v] > 0) {
					q.add(v);
					parent[v] = u;
					visited[v] = true;
				}
			}
		}

		// If we reached sink in BFS
		// starting from source, then
		// return true, else false
		return (visited[t] == true);
	}

	// Returns the maximum number of edge-disjoint
	// paths from s to t. This function is copy of
	// forFulkerson() discussed at http://goo.gl/wtQ4Ks
	static int findDisjointPaths(int graph[][], int s, int t) {
		V = graph.length;
		int u, v;

		// Create a residual graph and fill the
		// residual graph with given capacities
		// in the original graph as residual capacities
		// in residual graph

		// Residual graph where rGraph[i][j] indicates
		// residual capacity of edge from i to j (if there
		// is an edge. If rGraph[i][j] is 0, then there is not)
		int[][] rGraph = new int[V][V];
		for (u = 0; u < V; u++)
			for (v = 0; v < V; v++)
				rGraph[u][v] = graph[u][v];

		// This array is filled by BFS and to store path
		int[] parent = new int[V];

		int max_flow = 0; // There is no flow initially

		// Augment the flow while there is path
		// from source to sink
		while (bfs(rGraph, s, t, parent)) {
			// Find minimum residual capacity of the edges
			// along the path filled by BFS. Or we can say
			// find the maximum flow through the path found.
			int path_flow = Integer.MAX_VALUE;

			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				path_flow = Math.min(path_flow, rGraph[u][v]);
			}

			// update residual capacities of the edges and
			// reverse edges along the path
			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				rGraph[u][v] -= path_flow;
				rGraph[v][u] += path_flow;
			}

			// Add path flow to overall flow
			max_flow += path_flow;
		}

		// Return the overall flow (max_flow is equal to
		// maximum number of edge-disjoint paths)
		return max_flow;
	}

	// Driver Code
	public static void main(String[] args) throws FileNotFoundException {
		int vertices = 0;
		int arestas = 0;
		int origem = 0;
		int destino = 0;
		Graph graph;
		int[] maxDistancesList = new int[1];

		for (int instance = 0; instance < maxDistancesList.length; instance++) {
			// File file = new File("./instancias/elist"+(instance+1)+".txt");
			File file = new File("./instancias/elist96d.rmf");
			Scanner sc = new Scanner(file);

			vertices = sc.nextInt();
			arestas = sc.nextInt();
			origem = sc.nextInt();
			destino = sc.nextInt();
			graph = new Graph(vertices);

			while (sc.hasNext()) {
				int i = sc.nextInt();
				int j = sc.nextInt();
				int weight = sc.nextInt();
				graph.matrix[i - 1][j - 1] = 1;
			}

			// close scanner
			sc.close();

			System.out.println("There can be maximum " +
					findDisjointPaths(graph.matrix, origem, destino) +
					" edge-disjoint paths from " +
					origem + " to " + destino);
		}

		// Let us create a graph shown in the above example
		/*
		 * int graph1[][] =
		 * { { 0, 1, 1, 1, 0, 0, 0, 0 },
		 * { 0, 0, 1, 0, 0, 0, 0, 0 },
		 * { 0, 0, 0, 1, 0, 0, 1, 0 },
		 * { 0, 0, 0, 0, 0, 0, 1, 0 },
		 * { 0, 0, 1, 0, 0, 0, 0, 1 },
		 * { 0, 1, 0, 0, 0, 0, 0, 1 },
		 * { 0, 0, 0, 0, 0, 1, 0, 1 },
		 * { 0, 0, 0, 0, 0, 0, 0, 0 } };
		 * 
		 * int graph2[][] = {
		 * { 0, 1, 0, 1, 0, 0 },
		 * { 0, 0, 0, 0, 1, 0 },
		 * { 0, 0, 0, 0, 1, 1 },
		 * { 0, 1, 0, 0, 1, 0 },
		 * { 0, 0, 0, 1, 0, 0 },
		 * { 0, 0, 0, 0, 0, 1 } };
		 * 
		 * int s = 0;
		 * int t = 7;
		 * 
		 * int s2 = 0;
		 * int t2 = 4;
		 */

		/*System.out.println("There can be maximum " +
				findDisjointPaths(graph1, s, t) +
				" edge-disjoint paths from " +
				s + " to " + t);

		System.out.println("There can be maximum " +
				findDisjointPaths(graph2, s2, t2) +
				" edge-disjoint paths from " +
				s2 + " to " + t2);*/
	}
}

// This code is contributed by PrinciRaj1992
