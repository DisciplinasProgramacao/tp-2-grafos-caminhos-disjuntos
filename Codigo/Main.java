
// Java program to find maximum number
// of edge disjoint paths
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import src.graph.Graph;

public class Main {

	// Number of vertices in given graph
	static int V;

	/*
	 * Returns true if there is a path from
	 * source 's' to sink 't' in residual graph.
	 * Also fills parent[] to store the path
	 */
	/**
	 * Cria um array de vertices visitados
	 * e marca todos os vertices que ainda não foram visitados
	 * @param rGraph matrix do grafo residual
	 * @param s vertice de origem
	 * @param t vertice de destino
	 * @param parent array pai
	 * @return true ou false
	 */
	static boolean bfs(int rGraph[][], int s,
			int t, int parent[]) {

		V = rGraph.length;
		boolean[] visited = new boolean[V];
		Queue<Integer> q = new LinkedList<>();
		q.add(s);
		visited[s] = true;
		parent[s] = -1;

		
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
		return (visited[t] == true);
	}

	/**
	 * Funcção de Ford Fulkerson 
	 * @param graph
	 * @param s
	 * @param t
	 * @return fluxo máximo
	 */
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

	public static void main(String[] args) throws FileNotFoundException {
		int vertices = 0;
		int arestas = 0;
		int origem = 0;
		int destino = 0;
		Graph graph;
		String[] arquivosIntancias = {
			"elist96d.rmf",
			"elist160d.rmf",
			"elist200d.rmf",
			"elist500d.rmf",
			"elist640d.rmf",
			"elist960d.rmf",
			"elist1440d.rmf"
		};
		System.out.println(String.format("| %-20s | %-10s | %-10s | %-20s |", "INSTÂNCIA",
		"ORIGEM", "DESTINO", "CAMINHOS_DISJUNTOS"));
		for (int instance = 0; instance < arquivosIntancias.length; instance++) {
			File file = new File("./src/instancias/"+arquivosIntancias[instance]);
			Scanner sc = new Scanner(file);

			vertices = sc.nextInt();
			arestas = sc.nextInt();
			origem = sc.nextInt() - 1;
			destino = sc.nextInt() - 1;
			graph = new Graph(vertices);
			
			while (sc.hasNext()) {
				int i = sc.nextInt();
				int j = sc.nextInt();
				String weight = sc.next();
				graph.matrix[i - 1][j - 1] = 1;
			}

			// fechar scanner
			sc.close();
			

			System.out.println(String.format("| %-20s | %-10s | %-10s | %-20s |", arquivosIntancias[instance],
					origem+1, destino+1, findDisjointPaths(graph.matrix, origem, destino)));
					
		}
	}
}