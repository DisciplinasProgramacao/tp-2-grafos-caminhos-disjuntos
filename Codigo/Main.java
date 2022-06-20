import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import src.graph.Graph;

public class Main {

	//Numero de vertices em um dado grafo
	static int V;
	/**
	 * Cria um array de vertices visitados
	 * e marca todos os vertices que ainda não foram visitados
	 * @param rGraph matrix do grafo residual
	 * @param s vertice de origem
	 * @param t vertice de destino
	 * @param anterior array pai
	 * @return true ou false
	 */
	static boolean bfs(int rGraph[][], int s,
			int t, int anterior[]) {

		V = rGraph.length;
		boolean[] visitado = new boolean[V];
		Queue<Integer> q = new LinkedList<>();
		q.add(s);
		visitado[s] = true;
		anterior[s] = -1;

		
		while (!q.isEmpty()) {
			int u = q.peek();
			q.remove();

			for (int v = 0; v < V; v++) {
				if (visitado[v] == false &&
						rGraph[u][v] > 0) {
					q.add(v);
					anterior[v] = u;
					visitado[v] = true;
				}
			}
		}
		return (visitado[t] == true);
	}

	/**
	 * Funcção de Ford Fulkerson 
	 * @param graph
	 * @param s
	 * @param t
	 * @return fluxo máximo
	 */
	static int EncontrarCaminhosDisjuntos(int graph[][], int s, int t) {
		V = graph.length;
		int u, v;

		// Cria um grafo residual e preenche-o
		// com capacidades fornecidas pelo grafo 
		// original
		int[][] rGraph = new int[V][V];
		for (u = 0; u < V; u++)
			for (v = 0; v < V; v++)
				rGraph[u][v] = graph[u][v];


		int[] anterior = new int[V];

		int fluxo_maximo = 0; 

		while (bfs(rGraph, s, t, anterior)) {
			int fluxo_do_caminho = Integer.MAX_VALUE;

			for (v = t; v != s; v = anterior[v]) {
				u = anterior[v];
				fluxo_do_caminho = Math.min(fluxo_do_caminho, rGraph[u][v]);
			}
			for (v = t; v != s; v = anterior[v]) {
				u = anterior[v];
				rGraph[u][v] -= fluxo_do_caminho;
				rGraph[v][u] += fluxo_do_caminho;
			}
			fluxo_maximo += fluxo_do_caminho;
		}
		return fluxo_maximo;
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
				String peso = sc.next(); // Não utilizado
				graph.matrix[i - 1][j - 1] = 1;
			}

			// fechar scanner
			sc.close();
			

			System.out.println(String.format("| %-20s | %-10s | %-10s | %-20s |", arquivosIntancias[instance],
					origem+1, destino+1, EncontrarCaminhosDisjuntos(graph.matrix, origem, destino)));
					
		}
	}
}