package src.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Graph {
    public int[][] matrix;
    public int n_vertices;
    public int k_centers;
    public int maxDistance = 0;

    public Graph(int n_vertices) {
        this.matrix = new int[n_vertices][n_vertices];
        this.n_vertices = n_vertices;
        this.preencherComZeros();
    }

    private void preencherComZeros() {
        for (int i = 0; i < n_vertices; i++) {
            for (int j = 0; j < n_vertices; j++) {
                    this.matrix[i][j] = 0;
            }
        }
    }

}
